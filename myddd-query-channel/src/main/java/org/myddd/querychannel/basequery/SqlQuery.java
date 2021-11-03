package org.myddd.querychannel.basequery;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.domain.Entity;
import org.myddd.querychannel.BaseQuery;

/**
 * 可以指定定位查询参数或命名查询参数，也可以针对查询结果取子集。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class SqlQuery<T> extends BaseQuery<T> {

    private final String sql;

    /**
     * 使用仓储和SQL语句创建SQL查询。
     * @param sql SQL查询语句
     */
    public SqlQuery(String sql) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(sql));
        this.sql = sql;
    }

    /**
     * 获取SQL查询语句
     * @return SQL查询语句
     */
    public String getSql() {
        return sql;
    }

    @Override
    public String queryName() {
        return sql;
    }

    @Override
    public QueryType queryType() {
        return QueryType.SQL_QUERY;
    }
}
