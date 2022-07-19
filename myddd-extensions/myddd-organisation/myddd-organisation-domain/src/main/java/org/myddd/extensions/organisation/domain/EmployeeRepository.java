package org.myddd.extensions.organisation.domain;

import org.myddd.domain.AbstractRepository;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository extends AbstractRepository {

    /**
     * 查询指定组织下的指定编号ID是否已经存在
     * @param orgId 组织ID
     * @param employeeId 雇员ID
     * @return 是否存在
     */
    boolean isEmployeeIdExists(Long orgId,String employeeId);

    /**
     * 根据用户ID以及组织ID查询对应的雇员信息
     * @param userId 用户ID
     * @param orgId 组织ID
     * @return 返回雇员信息，没有为空
     */
    Employee queryEmployeeByUserIdAndOrgId(Long userId,long orgId);

    /**
     * 返回一个雇员的所有部门信息
     * @param employeeId 雇员ID
     * @return 返回部门信息集合
     */
    List<EmployeeOrganizationRelation> queryEmployeeOrganizations(Long employeeId);

    /**
     * 查询雇员ID在指定组织ID下的关系
     * @param employeeId 雇员ID
     * @param organizationId 组织ID
     * @return 雇员与组织的关系，雇员不在组织中则返回空
     */
    EmployeeOrganizationRelation queryEmployeeInOrganization(Long employeeId,Long organizationId);

    /**
     * 查询第三方数据源ID同步过来的所有雇员信息
     * @param thirdSourceId 第三方源ID
     * @return 雇员集合
     */
    List<Employee> queryExistsEmployeeByThirdSourceId(Long thirdSourceId);

    /**
     * 查询第三方数据源同步过来的雇员与部门的对应关系
     * @param thirdSourceId 第三方源ID
     * @return 雇员组织集合
     */
    List<EmployeeOrganizationRelation> queryExistsEmployeeOrganizationRelationByThirdSourceId(Long thirdSourceId);

    /**
     * 批量注销用户，这个方法在同步第三方数据源时使用
     */
    void batchDisableEmployees(List<Employee> employees);

    /**
     * 批量更新用户，给第三方同步数据时使用
     * @param employees 要更新的雇员信息集合
     */
    void batchUpdateEmployees(List<Employee> employees);

    /**
     * 返回指定组织下的指定雇员TYPE的雇员信息
     * @param orgId 组织ID
     * @param employeeType 雇员TYPE
     * @return 雇员，不存在则为空
     */
    Employee queryEmployeeByType(Long orgId,EmployeeType employeeType);


    /**
     * 批量查询雇员的角色信息
     * @param employeeIds 需要批量查询的雇员ID
     * @return 返回雇员-角色集
     */
    Map<Long,List<OrgRole>> batchQueryEmployeeRoles(List<Long> employeeIds);

    /**
     * 批量查询雇员的组织信息
     * @param employeeIds 需要批量查询的雇员ID
     * @return 返回雇员-组织集
     */
    Map<Long,List<Organization>> batchQueryEmployeeOrganizations(List<Long> employeeIds);


    /**
     * 查询一个用户在指定组织下的雇员信息，如果没有则返回空
     * @param orgId 组织ID
     * @param userId 用户ID
     * @return 雇员信息
     */
    Employee queryOrgEmployeeByUserId(Long orgId, Long userId);

    /**
     * 查询一个组织下指定邮箱的雇员是否存在
     * @param orgId 组织ID
     * @param email 邮箱
     * @return 雇员信息
     */
    Employee queryOrgEmployeeByEmail(Long orgId, String email);

    /**
     * 查询一个组织下指定邮箱的手机号是否存在
     * @param orgId 组织ID
     * @param phone 手机号
     * @return 雇员信息
     */
    Employee queryOrgEmployeeByPhone(Long orgId, String phone);


    /**
     * 批量清理一批雇员的组织关系
     * @param idList 雇员集合
     */
    void batchClearEmployeeOrganizations(List<Long> idList);

}