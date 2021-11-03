package org.myddd.querychannel.query;



import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.querychannel.QueryRepository;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.querychannel.BaseQuery;
import org.myddd.querychannel.basequery.JpqlQuery;
import org.myddd.querychannel.basequery.NamedQuery;

/**
 * 通道查询的SQL实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class ChannelNamedQuery<T> extends ChannelQuery<T> {
    
    private NamedQuery<T> query;

    public ChannelNamedQuery(QueryRepository repository, String queryName) {
        super(repository);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(queryName),"Query name must be set!");
        query = new NamedQuery<>(queryName);
        setQuery(query);
    }

    @Override
    protected String getQueryString() {
        return repository.getQueryStringOfNamedQuery(query.getQueryName());
    }

    @Override
    protected BaseQuery<T> createBaseQuery(String queryString) {
        return new JpqlQuery<>(queryString);
    }

}
