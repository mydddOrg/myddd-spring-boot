package org.myddd.querychannel;

import org.myddd.querychannel.basequery.JpqlQuery;
import org.myddd.querychannel.basequery.NamedQuery;
import org.myddd.querychannel.basequery.SqlQuery;

import java.util.List;

public interface QueryRepository {

    /**
     * 执行JPQL查询，返回符合条件的实体列表
     *
     * @param jpqlQuery 要执行的JPQL查询
     * @param <T> 返回结果元素类型
     * @return 符合查询条件的结果列表
     */
    <T> List<T> find(JpqlQuery jpqlQuery);

    /**
     * 执行JPQL查询，返回符合条件的单个实体
     *
     * @param jpqlQuery 要执行的JPQL查询
     * @param <T> 返回结果类型
     * @return 符合查询条件的单个结果
     */
    <T> T getSingleResult(JpqlQuery jpqlQuery);

    /**
     * 执行命名查询，返回符合条件的实体列表
     *
     * @param namedQuery 要执行的命名查询
     * @param <T> 返回结果元素类型
     * @return 符合查询条件的结果列表
     */
    <T> List<T> find(NamedQuery namedQuery);

    /**
     * 执行命名查询，返回符合条件的单个实体
     *
     * @param namedQuery 要执行的命名查询
     * @param <T> 返回结果类型
     * @return 符合查询条件的单个结果
     */
    <T> T getSingleResult(NamedQuery namedQuery);

    /**
     * 执行SQL查询，返回符合条件的实体列表
     *
     * @param sqlQuery 要执行的SQL查询。
     * @param <T> 返回结果元素类型
     * @return 符合查询条件的结果列表
     */
    <T> List<T> find(SqlQuery sqlQuery);

    /**
     * 执行SQL查询，返回符合条件的单个实体
     *
     * @param sqlQuery 要执行的SQL查询。
     * @param <T> 返回结果类型
     * @return 符合查询条件的单个结果
     */
    <T> T getSingleResult(SqlQuery sqlQuery);


    /**
     * 获取命名查询的查询字符串
     * @param queryName 命名查询的名字
     * @return 命名查询对应的JPQL字符串
     */
    String getQueryStringOfNamedQuery(String queryName);
}
