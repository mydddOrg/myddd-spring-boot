package org.myddd.querychannel.query;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.querychannel.BaseQuery;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.querychannel.QueryRepository;
import org.myddd.querychannel.basequery.JpqlQuery;

/**
 * 通道查询的JPQL实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class ChannelJpqlQuery<T> extends ChannelQuery<T> {
    
    private final JpqlQuery<T> query;

    public ChannelJpqlQuery(QueryRepository repository, String jpql) {
        super(repository);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(jpql),"JPQL must be set!");
        query = new JpqlQuery<>(jpql);
        setQuery(query);
    }

    @Override
    protected String getQueryString() {
        return query.getJpql();
    }

    @Override
    protected <Q> BaseQuery<Q> createBaseQuery(String queryString,Class<Q> qClass) {
        return new JpqlQuery<>(queryString);
    }
}
