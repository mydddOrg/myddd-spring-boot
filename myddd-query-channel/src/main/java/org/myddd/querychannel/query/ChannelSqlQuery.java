package org.myddd.querychannel.query;


import org.myddd.domain.BaseQuery;
import org.myddd.domain.Entity;
import org.myddd.domain.EntityRepository;
import org.myddd.domain.SqlQuery;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.utils.Assert;
import org.myddd.utils.Page;

import java.util.List;

/**
 * 通道查询的SQL实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class ChannelSqlQuery extends ChannelQuery<ChannelSqlQuery> {
    
    private final SqlQuery query;

    public ChannelSqlQuery(EntityRepository repository, String sql) {
        super(repository);
        Assert.notBlank(sql, "SQL must be set!");
        query = new SqlQuery(repository, sql);
        setQuery(query);
    }

    /**
     * 设置查询的结果实体类型。注意setResultEntityClass()和addScalar()是互斥的，
     * 分别适用于查询结果是实体和标量两种情形
     * @param resultEntityClass 要设置的查询结果类型
     * @return 该对象本身
     */
    public ChannelSqlQuery setResultEntityClass(Class<? extends Entity> resultEntityClass) {
        query.setResultEntityClass(resultEntityClass);
        return this;
    }

    @Override
    public <T> List<T> list() {
        return query.list();
    }

    @Override
    public <T> Page<T> pagedList() {
        return new Page<T>(query.getFirstResult(), queryResultCount(), 
                query.getMaxResults(), (List<T>) query.list());
    }

    @Override
    public <T> T singleResult() {
        return (T) query.singleResult();
    }

    @Override
    protected String getQueryString() {
        return query.getSql();
    }

    @Override
    protected BaseQuery createBaseQuery(String queryString) {
        return repository.createSqlQuery(queryString);
    }


}
