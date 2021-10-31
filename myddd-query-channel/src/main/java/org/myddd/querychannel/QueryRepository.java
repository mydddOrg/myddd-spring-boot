package org.myddd.querychannel;

import java.util.List;

public interface QueryRepository {

    /**
     * 执行JPQL查询，返回符合条件的实体列表
     *
     * @param baseQuery 要执行的JPQL查询
     * @param <T> 返回结果元素类型
     * @return 符合查询条件的结果列表
     */
    <T> List<T> find(BaseQuery<T> baseQuery);

    /**
     * 执行JPQL查询，返回符合条件的单个实体
     *
     * @param baseQuery 要执行的JPQL查询
     * @param <T> 返回结果类型
     * @return 符合查询条件的单个结果
     */
    <T> T getSingleResult(BaseQuery<T> baseQuery);


    /**
     * 获取命名查询的查询字符串
     * @param queryName 命名查询的名字
     * @return 命名查询对应的JPQL字符串
     */
    String getQueryStringOfNamedQuery(String queryName);
}
