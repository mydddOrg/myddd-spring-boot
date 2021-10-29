package org.myddd.querychannel.basequery;


import org.myddd.utils.Assert;
import java.util.List;

/**
 * 可以指定定位查询参数或命名查询参数，也可以针对查询结果取子集。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class JpqlQuery extends BaseQuery<JpqlQuery> {

    private final String jpql;

    /**
     * 使用仓储和JPQL语句创建JPQL查询。
     * @param jpql JPQL查询语句
     */
    public JpqlQuery(String jpql) {
        Assert.notBlank(jpql);
        this.jpql = jpql;
    }

    /**
     * 获取JPQL查询语句
     * @return JPQL查询语句
     */
    public String getJpql() {
        return jpql;
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
}
