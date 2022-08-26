package org.myddd.extensions.organisation.organization;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myddd.domain.IDGenerate;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organization.api.EmployeeDto;
import org.myddd.extensions.organization.api.OrgRoleGroupDto;
import org.myddd.extensions.organization.api.OrganizationDto;
import org.myddd.extensions.security.api.UserDto;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerTest {

    @Value("${server.servlet.context-path:/}")
    public String contextPath;

    @Value("${local.server.port}")
    public int port;

    @Autowired
    public TestRestTemplate restTemplate;

    @Inject
    public ApplicationContext applicationContext;

    @Inject
    private IDGenerate idGenerate;

    @BeforeEach
    public void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    public String baseUrl(){
        return "http://localhost:"+port+contextPath;
    }

    protected HttpEntity<String> createHttpEntityFromString(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        return entity;
    }

    protected HttpEntity createEmptyHttpEntity(){
        return new HttpEntity<>(new HttpHeaders());
    }

    protected String randomId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    protected Long randomLong(){
        return idGenerate.nextId();
    }

    protected UserDto randomUserDto(){
        return randomUserDto(randomId());
    }

    protected UserDto randomUserDto(String userId){
        return UserDto.newBuilder()
                .setId(randomLong())
                .setUserId(userId)
                .setPassword(randomId())
                .setName(randomId())
                .build();
    }

    protected OrganizationDto randomOrganizationWithUserId(Long userId){
        return OrganizationDto.newBuilder()
                .setName(randomId())
                .setDescription(randomId())
                .setCreateUserId(userId)
                .build();
    }

    protected OrganizationDto randomOrganization(){
        return OrganizationDto.newBuilder()
                .setName(randomId())
                .setDescription(randomId())
                .setCreateUserId(randomLong())
                .build();
    }

    protected OrganizationDto randomSubOrganization(Long parentId){
        return OrganizationDto.newBuilder()
                .setName(randomId())
                .setParentId(parentId)
                .setDescription(randomId())
                .setCreateUserId(randomLong())
                .build();
    }

    protected OrgRoleGroupDto randomOrgRoleGroup(Long orgId){
        return OrgRoleGroupDto.newBuilder()
                .setOrganization(OrganizationDto.newBuilder().setId(orgId).build())
                .setName(randomId())
                .build();
    }

    protected EmployeeDto randomEmployee(){
        return EmployeeDto.newBuilder()
                .setUserId(randomLong())
                .setEmail(randomId())
                .setPhone(randomId())
                .setName(randomId()).build();
    }

    protected EmployeeDto randomEmployee(Long orgId){
        return EmployeeDto.newBuilder()
                .setUserId(randomLong())
                .setEmail(randomId())
                .setPhone(randomId())
                .setOrgId(orgId)
                .setName(randomId()).build();
    }

    protected String pageQueryWithLimits(List<Long> limits){
        var jsonObject = new JsonObject();
        var limitsArray = new JsonArray();
        limits.forEach(limitsArray::add);

        jsonObject.add("limits",limitsArray);
        jsonObject.addProperty("page",0);
        jsonObject.addProperty("pageSize",100);

        return jsonObject.toString();
    }

    protected String employeePageQueryWithLimits(List<Long> limits){
        var jsonObject = new JsonObject();
        var limitsArray = new JsonArray();
        limits.forEach(limitsArray::add);

        jsonObject.add("employees",limitsArray);
        jsonObject.addProperty("page",0);
        jsonObject.addProperty("pageSize",100);

        return jsonObject.toString();
    }

    protected String queryWithLimits(List<Long> limits){
        var jsonObject = new JsonObject();
        var limitsArray = new JsonArray();
        limits.forEach(limitsArray::add);

        jsonObject.add("limits",limitsArray);

        return jsonObject.toString();
    }

}


