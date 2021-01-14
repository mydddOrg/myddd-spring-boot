package org.myddd.domain.internal.criterion;


import org.myddd.domain.QueryCriterion;
import org.myddd.utils.Assert;

/**
 * 基本查询条件，指除AND/OR/NOT查询条件以外的大多数查询条件，基本上都是判断某个属性值是否符合 某种条件
 *
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public abstract class BasicCriterion extends AbstractCriterion {

    private final String propName;

    public BasicCriterion(String propName) {
        Assert.notBlank(propName, "Property name is null or blank!");
        this.propName = propName;
    }

    /**
     * 获取属性名
     *
     * @return 属性名
     */
    public String getPropName() {
        return propName;
    }

    /**
     * 获取带别名前缀的属性名
     *
     * @return 带别名前缀的属性名
     */
    protected String getPropNameWithAlias() {
        return QueryCriterion.ROOT_ALIAS + "." + propName;
    }

    /**
     * 获得参数名
     *
     * @return 参数名
     */
    protected String getParamName() {
        String result = QueryCriterion.ROOT_ALIAS + "_" + propName + hashCode();
        result = result.replace(".", "_");
        result = result.replace("-", "_");
        return result;
    }

    /**
     * 获得带冒号前缀的参数名
     *
     * @return 带冒号前缀的参数名
     */
    protected String getParamNameWithColon() {
        return ":" + getParamName();
    }
}
