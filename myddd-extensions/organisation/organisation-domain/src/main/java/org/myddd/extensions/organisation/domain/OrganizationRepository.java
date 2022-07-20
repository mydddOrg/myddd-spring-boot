package org.myddd.extensions.organisation.domain;

import org.myddd.domain.AbstractRepository;

import java.util.List;
import java.util.Set;

public interface OrganizationRepository extends AbstractRepository {

    /**
     * 根据OrgId查询组织信息
     * @param orgId 组织ID
     * @return
     */
    Organization queryOrganizationByOrgId(Long orgId);

    /**
     * 查询一个用户所有的公司
     * @param userId 用户ID
     * @return
     */
    List<Organization> queryTopCompaniesByUserId(Long userId);

    /**
     * 更新一个组织
     * @param organization 组织
     * @return
     */
    Organization updateOrganization(Organization organization);

    /**
     * 批量查询一批组织信息
     * @param orgIds 组织ID集
     * @return
     */
    Set<Organization> batchQueryOrganization(Set<Long> orgIds);

    /**
     * 查询已经导入过的第三方组织ID
     * @param thirdSourceId 第三方同步ID
     * @return
     */
    List<Organization> queryExistsOrganizationsByThirdSourceId(Long thirdSourceId);

    /**
     * 批量注销组织
     * @param organizations 需要注销的组织列表
     */
    void batchDisableOrganizations(List<Organization> organizations);

    /**
     * 批量更新组织
     * @param organizations 需要更新的组织列表
     */
    void batchUpdateOrganizations(List<Organization> organizations);


    /**
     * 是否一个部门下雇员为空
     * @param id 部门ID
     * @return 是否为空
     */
    boolean isOrganizationEmpty(Long id);

    /**
     * 根据父ID及组织名称，查询一个部门信息
     * @param parentId 父ID
     * @param name 组织名称
     * @return
     */
    Organization queryOrganizationByParentIdAndName(Long parentId,String name);
}
