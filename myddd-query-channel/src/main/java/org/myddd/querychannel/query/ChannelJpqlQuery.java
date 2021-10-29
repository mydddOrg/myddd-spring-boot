package org.myddd.querychannel.query;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.domain.BaseQuery;
import org.myddd.domain.EntityRepository;
import org.myddd.domain.JpqlQuery;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.utils.Page;

import java.util.List;

/**
 * 通道查询的JPQL实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class ChannelJpqlQuery<T> extends ChannelQuery<T> {
    
    private final JpqlQuery query;

    public ChannelJpqlQuery(EntityRepository repository, String jpql) {
        super(repository);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(jpql),"JPQL must be set!");
        query = new JpqlQuery(repository, jpql);
        setQuery(query);
    }

    @Override
    protected String getQueryString() {
        return query.getJpql();
    }

    @Override
    protected BaseQuery createBaseQuery(String queryString) {
        return repository.createJpqlQuery(queryString);
    }

    /**
     * 返回查询结果数据页。
     *
     * @return 查询结果。
     */
    public List<T> list() {
        return query.list();
    }

    /**
     * 返回查询结果数据页。
     *
     * @return 查询结果。
     */
    public Page<T> pagedList() {
        return new Page<T>(query.getFirstResult(), queryResultCount(),
                query.getMaxResults(), query.list());
    }

    /**
     * 返回单条查询结果。
     *
     * @return 查询结果。
     */
    public T singleResult() {
        return (T) query.singleResult();
    }
}
