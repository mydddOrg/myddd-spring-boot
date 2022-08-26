package org.myddd.extensions.organization.application;

import com.google.common.base.Strings;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.OrganizationNotExistsException;
import org.myddd.extensions.organisation.ParentOrganizationNotExists;
import org.myddd.extensions.organisation.domain.*;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.organization.application.assembler.EmployeeAssembler;
import org.myddd.extensions.organization.application.assembler.OrganizationAssembler;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.utils.Page;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Named
public class OrganizationApplicationSpring implements OrganizationApplication {

    public static final String ROOT_PARENT_ID = "rootParentId";
    private OrganizationService organizationService;

    private OrganizationService getOrganizationService(){
        if(Objects.isNull(organizationService)){
            organizationService = InstanceFactory.getInstance(OrganizationService.class);
        }
        return organizationService;
    }

    private QueryChannelService queryChannelService;

    private QueryChannelService getQueryChannelService(){
        if(Objects.isNull(queryChannelService)){
            queryChannelService = InstanceFactory.getInstance(QueryChannelService.class);
        }
        return queryChannelService;
    }

    @Override
    public OrganizationDto createTopOrganization(OrganizationDto request) {
        Company company = OrganizationAssembler.toCompany(request);
        Organization created = getOrganizationService().createTopOrganization(company);
        return OrganizationAssembler.toDTO(created);
    }

    public OrganizationDto createDepartment(OrganizationDto request) {
        Organization parent = Organization.queryOrganizationByOrgId(request.getParentId());
        if(Objects.isNull(parent)){
            throw new ParentOrganizationNotExists();
        }
        Department department = OrganizationAssembler.toDepartment(request);
        department.setParent(parent);

        return OrganizationAssembler.toDTO(department.createDepartment());
    }

    @Override
    public BoolValue joinOrganization(JoinOrLeaveOrganizationDto request) {
        getOrganizationService().joinOrganization(request.getUserId(),request.getOrgId());
        return BoolValue.of(true);
    }

    @Override
    public BoolValue leaveOrganization(JoinOrLeaveOrganizationDto request) {
        return null;
    }

    @Override
    public BoolValue deleteOrganization(Int64Value request) {
        Organization.deleteOrganization(request.getValue());
        return BoolValue.of(true);
    }

    @Override
    public ListOrganizationDto queryTopCompaniesByUserId(Int64Value request) {
        List<Organization> organizationList = Company.queryTopCompaniesByUserId(request.getValue());
        List<OrganizationDto> organizationDtoList = organizationList.stream().map(OrganizationAssembler::toDTO).collect(Collectors.toList());
        return ListOrganizationDto.newBuilder()
                .addAllOrganizations(organizationDtoList)
                .build();
    }

    @Override
    public ListOrganizationDto queryDepartmentsByEmployeeAndOrg(QueryDepartmentsDto request) {
        List<EmployeeOrganizationRelation> query = null;

        StringBuilder sql;
        Map<String, Object> params;
        sql = new StringBuilder("from EmployeeOrganizationRelation where employee.id = :employeeId");
        params = new HashMap<>(Map.of("employeeId", request.getEmployeeId()));
        if(!Strings.isNullOrEmpty(request.getSearch())){
            sql.append(" and organization.name like :name ");
            params.put("name","%"+request.getSearch()+"%");
        }

        sql.append(" and organization.rootParentId = :rootParentId");
        params.put(ROOT_PARENT_ID,request.getOrgId());

        query = getQueryChannelService()
                .createJpqlQuery(sql.toString(),EmployeeOrganizationRelation.class)
                .setParameters(params)
                .list();

        if(query.isEmpty())return ListOrganizationDto.newBuilder().build();

        if(!request.getIncludeSubOrg()){
            return ListOrganizationDto.newBuilder()
                    .addAllOrganizations(query.stream().map(it -> OrganizationAssembler.toDTO(it.getOrganization())).collect(Collectors.toList()))
                    .build();
        }
        else {
            var departmentSQL = new StringBuilder("from Organization where id in :ids ");

            var subDepartments = query.stream().map(it -> it.getOrganization().getId()).collect(Collectors.toList());
            departmentSQL.append(" or (");
            for (int i = 0; i < subDepartments.size(); i++) {
                if(i != 0) departmentSQL.append(" or ");
                var subDepartment = subDepartments.get(i);
                var fullPathSearch = "'%/" + subDepartment + "%'";
                departmentSQL.append(" fullPath like ").append(fullPathSearch);
            }
            departmentSQL.append(") ");

            var departmentQuery = getQueryChannelService().createJpqlQuery(departmentSQL.toString(),Organization.class)
                    .addParameter("ids",subDepartments)
                    .list();

            return ListOrganizationDto.newBuilder()
                    .addAllOrganizations(departmentQuery.stream().map(OrganizationAssembler::toDTO).collect(Collectors.toList()))
                    .build();
        }

    }

    @Override
    public ListOrganizationDto listCompanyTrees(QueryCompanyTreeDto request) {

        StringBuilder querySQL = new StringBuilder("from Organization where rootParentId = :rootParentId");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put(ROOT_PARENT_ID,request.getOrgId());

        if(!request.getLimitsList().isEmpty()){
            querySQL.append(" and ( id in :ids ");
            for (Long idLimit : request.getLimitsList()) {
                var fullPathSearch = "'%/" + idLimit + "%'";
                querySQL.append(" or fullPath like ").append(fullPathSearch);
            }
            querySQL.append(" )");
            parameters.put("ids",request.getLimitsList());
        }

        var organizationList = getQueryChannelService()
                .createJpqlQuery(querySQL.toString(),Organization.class)
                .setParameters(parameters)
                .list();

        var organizationMap = organizationList.stream().collect(Collectors.toMap(Organization::getId, Function.identity()));
        var parentOrgIds = getQueryChannelService()
                .createSqlQuery("select id,parent_ from organization_ where root_parent_id_ =:rootParentId ",Object.class)
                .addParameter(ROOT_PARENT_ID,request.getOrgId())
                .list();

        parentOrgIds.forEach(it -> {
            var value = (Object[])it;
            BigInteger key =(BigInteger)value[0];
            BigInteger parentId = (BigInteger)value[1];
            if(Objects.nonNull(parentId) && organizationMap.containsKey(key.longValue()) && organizationMap.containsKey(parentId.longValue())){
                organizationMap.get(key.longValue()).setParent(organizationMap.get(parentId.longValue()));
            }
        });

        List<OrganizationDto> organizationDtoList = organizationList.stream().map(OrganizationAssembler::toDTO).collect(Collectors.toList());
        return ListOrganizationDto.newBuilder()
                .addAllOrganizations(organizationDtoList)
                .build();
    }

    @Override
    public PageOrganizationDto pageSearchOrganizations(PageQueryDto request) {
        Page<Organization> organizationPage;

        StringBuilder querySQL = new StringBuilder("from Organization where rootParentId = :rootParentId");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put(ROOT_PARENT_ID,request.getOrgId());

        if(!Strings.isNullOrEmpty(request.getSearch())){
            querySQL.append(" and name like :search");
            parameters.put("search","%"+request.getSearch()+"%");
        }

        if(!request.getLimitsList().isEmpty()){
            querySQL.append(" and ( id in :ids ");
            for (Long idLimit : request.getLimitsList()) {
                var fullPathSearch = "'%/" + idLimit + "%'";
                querySQL.append(" or fullPath like ").append(fullPathSearch);
            }
            querySQL.append(" )");
            parameters.put("ids",request.getLimitsList());
        }

        organizationPage = getQueryChannelService()
                .createJpqlQuery(querySQL.toString(),Organization.class)
                .setParameters(parameters)
                .setPage(request.getPage(),request.getPageSize())
                .pagedList();

        var organizationList = organizationPage.getData();
        Organization.fetchOrganizationFullNamePath(organizationList);

        return PageOrganizationDto.newBuilder()
                .setPage(request.getPage())
                .setPageSize(request.getPageSize())
                .setTotal(organizationPage.getResultCount())
                .addAllOrganizations(organizationList.stream().map(OrganizationAssembler::toDTO).collect(Collectors.toList()))
                .build();
    }

    @Override
    public PageOrganizationDto pageQueryOrganizations(PageQueryDto request) {
        Page<Organization> organizationPage = getQueryChannelService()
                .createJpqlQuery("from Organization where parent.id = :orgId",Organization.class)
                .addParameter("orgId",request.getOrgId())
                .setPage(request.getPage(),request.getPageSize())
                .pagedList();

        return PageOrganizationDto.newBuilder()
                .setPage(request.getPage())
                .setPageSize(request.getPageSize())
                .setTotal(organizationPage.getResultCount())
                .addAllOrganizations(organizationPage.getData().stream().map(OrganizationAssembler::toDTO).collect(Collectors.toList()))
                .build();
    }

    @Override
    public OrganizationDto updateOrganization(OrganizationDto request) {
        Organization organization = Organization.queryOrganizationByOrgId(request.getId());
        if(Objects.isNull(organization)){
            throw new OrganizationNotExistsException(request.getId());
        }

        organization.setName(request.getName());
        return OrganizationAssembler.toDTO(organization.updateOrganization());
    }

    @Override
    @Transactional
    public EmployeeDto queryOpenApiEmployeeDto(Int64Value request) {
        return EmployeeAssembler.toDto(Employee.queryOrCreateEmployeeByType(request.getValue(),EmployeeType.EMPLOYEE_OPEN_API));
    }

    @Override
    public ListEmployeeDto queryOrganizationSystemManagerEmployees(Int64Value request) {
        var organization = Organization.queryOrganizationByOrgId(request.getValue());
        if(Objects.isNull(organization))throw new OrganizationNotExistsException(request.getValue());


        var query = getQueryChannelService().createJpqlQuery("from Employee where userId = :userId ",Employee.class)
                .addParameter("userId",organization.getCreateUserId())
                .list();
        return ListEmployeeDto.newBuilder()
                .addAllEmployees(query.stream().map(EmployeeAssembler::toDto).collect(Collectors.toList()))
                .build();
    }

}
