package org.myddd.domain;


import org.myddd.utils.Assert;

import java.util.List;

/**
 * 基于原生SQL的查询。DDDLib支持的四种查询形式之一。
 * 可以指定定位查询参数或命名查询参数，也可以针对查询结果取子集。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class SqlQuery extends BaseQuery<SqlQuery> {

    private final String sql;
    private Class<? extends Entity> resultEntityClass;

    /**
     * 使用仓储和SQL语句创建SQL查询。
     * @param repository 仓储
     * @param sql SQL查询语句
     */
    public SqlQuery(EntityRepository repository, String sql) {
        super(repository);
        Assert.notBlank(sql);
        this.sql = sql;
    }

    /**
     * 获取SQL查询语句
     * @return SQL查询语句
     */
    public String getSql() {
        return sql;
    }

    /**
     * 返回查询结果实体类型。适用于返回结果是实体或实体列表的情形。
     * @return 查询结果的实体类型（如果结果是个集合，就是集合元素的类型）
     */
    public Class<? extends Entity> getResultEntityClass() {
        return resultEntityClass;
    }

    /**
     * 设置查询的结果实体类型。注意setResultEntityClass()和addScalar()是互斥的，
     * 分别适用于查询结果是实体和标量两种情形
     * @param resultEntityClass 要设置的查询结果类型
     * @return 该对象本身
     */
    public SqlQuery setResultEntityClass(Class<? extends Entity> resultEntityClass) {
        this.resultEntityClass = resultEntityClass;
        return this;
    }

    /**
     * 返回查询结果列表。
     * @param <T> 查询结果的列表元素类型
     * @return 查询结果。
     */
    @Override
    public <T> List<T> list() {
        return getRepository().find(this);
    }

    /**
     * 返回单条查询结果。
     * @param <T> 查询结果的类型
     * @return 查询结果。
     */
    @Override
    public <T> T singleResult() {
        return getRepository().getSingleResult(this);
    }

    /**
     * 执行更新仓储的操作。
     * @return 被更新或删除的实体的数量
     */
    @Override
    public int executeUpdate() {
        return getRepository().executeUpdate(this);
    }
}
