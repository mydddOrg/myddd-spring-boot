package org.myddd.querychannel.query;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.querychannel.QueryRepository;
import org.myddd.querychannel.BaseQuery;
import org.myddd.querychannel.basequery.SqlQuery;

/**
 * 通道查询的SQL实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class ChannelSqlQuery<T> extends ChannelQuery<T> {
    
    private final SqlQuery<T> query;

    public ChannelSqlQuery(QueryRepository repository, String sql) {
        super(repository);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(sql),"SQL must be set!");
        query = new SqlQuery<>(sql);
        setQuery(query);
    }

    @Override
    protected String getQueryString() {
        return query.getSql();
    }

    @Override
    protected BaseQuery<T> createBaseQuery(String queryString) {
        return new SqlQuery<>(queryString);
    }


}
