package org.myddd.querychannel.basequery;

import org.myddd.querychannel.BaseQuery;
import org.myddd.utils.Assert;

/**
 * 可以指定定位查询参数或命名查询参数，也可以针对查询结果取子集。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class NamedQuery<T> extends BaseQuery<T> {

    private final String queryName;

    /**
     * 使用仓储和命名查询名字创建命名查询
     * @param queryName 命名查询的名称
     */
    public NamedQuery(String queryName) {
        Assert.notBlank(queryName);
        this.queryName = queryName;
    }

    /**
     * 获取命名查询的名称
     * @return 命名查询名称
     */
    public String getQueryName() {
        return queryName;
    }

    @Override
    public String queryName() {
        return queryName;
    }

    @Override
    public QueryType queryType() {
        return QueryType.NAMED_QUERY;
    }

}
