package org.myddd.querychannel.basequery;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.querychannel.BaseQuery;

/**
 * 可以指定定位查询参数或命名查询参数，也可以针对查询结果取子集。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class JpqlQuery<T> extends BaseQuery<T> {

    private final String jpql;

    /**
     * 使用仓储和JPQL语句创建JPQL查询。
     * @param jpql JPQL查询语句
     */
    public JpqlQuery(String jpql) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(jpql));
        this.jpql = jpql;
    }

    /**
     * 获取JPQL查询语句
     * @return JPQL查询语句
     */
    public String getJpql() {
        return jpql;
    }

    @Override
    public String queryName() {
        return jpql;
    }

    @Override
    public QueryType queryType() {
        return QueryType.JPQL_QUERY;
    }
}
