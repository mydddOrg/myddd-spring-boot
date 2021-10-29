package org.myddd.querychannel;


import com.google.common.base.Preconditions;
import org.myddd.querychannel.basequery.BaseQuery;
import org.myddd.querychannel.basequery.QueryParameters;
import org.myddd.utils.Page;

import java.util.List;
import java.util.Map;

/**
 * 查询通道查询
 *
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public abstract class ChannelQuery<T> {

    protected QueryRepository repository;
    private BaseQuery query;
    private int pageIndex;

    public ChannelQuery(QueryRepository repository) {
        this.repository = repository;
    }

    public void setQuery(BaseQuery query) {
        this.query = query;
    }

    /**
     * 获取查询参数
     *
     * @return 查询参数
     */
    public QueryParameters getParameters() {
        return query.getParameters();
    }

    /**
     * 设置定位命名参数（数组方式）
     *
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    public ChannelQuery<T> setParameters(Object... parameters) {
        query.setParameters(parameters);
        return  this;
    }

    /**
     * 设置定位参数（列表方式）
     *
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    public ChannelQuery<T> setParameters(List<Object> parameters) {
        query.setParameters(parameters);
        return this;
    }

    /**
     * 设置命名参数（Map形式，Key是参数名称，Value是参数值）
     *
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    public ChannelQuery<T> setParameters(Map<String, Object> parameters) {
        query.setParameters(parameters);
        return this;
    }

    /**
     * 添加一个命名参数，Key是参数名称，Value是参数值。
     *
     * @param key 命名参数名称
     * @param value 参数值
     * @return 该对象本身
     */
    public ChannelQuery<T> addParameter(String key, Object value) {
        query.addParameter(key, value);
        return  this;
    }

    /**
     * 针对分页查询，获取firstResult。 firstResult代表从满足查询条件的记录的第firstResult + 1条开始获取数据子集。
     *
     * @return firstResult的设置值，
     */
    public int getFirstResult() {
        return query.getFirstResult();
    }

    /**
     * 针对分页查询，设置firstResult。 firstResult代表从满足查询条件的记录的第firstResult + 1条开始获取数据子集。
     *
     * @param firstResult 要设置的firstResult值。
     * @return 该对象本身
     */
    public ChannelQuery<T> setFirstResult(int firstResult) {
        Preconditions.checkArgument(firstResult >= 0, "First result must be greater than 0!");
        query.setFirstResult(firstResult);
        return  this;
    }

    /**
     * 获取当前页码（0为第一页）
     *
     * @return 当前页码
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 获取每页记录数
     *
     * @return 每页记录数
     */
    public int getPageSize() {
        return query.getMaxResults();
    }

    /**
     * 设置每页记录数
     *
     * @param pageSize 每页记录数
     * @return 该对象本身
     */
    public ChannelQuery<T> setPageSize(int pageSize) {
        Preconditions.checkArgument(pageSize > 0, "Page size must be greater than 0!");
        query.setMaxResults(pageSize);
        return this;
    }

    /**
     * 设置分页信息
     *
     * @param pageIndex 要设置的页码
     * @param pageSize 要设置的页大小
     * @return 该对象本身
     */
    public ChannelQuery<T> setPage(int pageIndex, int pageSize) {
        Preconditions.checkArgument(pageIndex >= 0, "Page index must be greater than or equals to 0!");
        Preconditions.checkArgument(pageSize > 0, "Page index must be greater than 0!");
        this.pageIndex = pageIndex;
        query.setMaxResults(pageSize);
        query.setFirstResult(Page.getStartOfPage(pageIndex, pageSize));
        return this;
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

    /**
     * 获取符合查询条件的记录总数
     *
     * @return 符合查询条件的记录总数
     */
    public long queryResultCount() {
        CountQueryStringBuilder builder = new CountQueryStringBuilder(getQueryString());
        if (builder.containsGroupByClause()) {
            List rows = createBaseQuery(builder.removeOrderByClause())
                    .setParameters(query.getParameters()).list();
            return rows == null ? 0 : rows.size();
        } else {
            Number result = (Number) createBaseQuery(builder.buildQueryStringOfCount())
                    .setParameters(query.getParameters()).singleResult();
            return result.longValue();
        }
    }

    /**
     * 获得当前查询对应的查询字符串
     * @return 当前查询对应的查询字符串
     */
    protected abstract String getQueryString();

    protected abstract BaseQuery createBaseQuery(String queryString);
}
