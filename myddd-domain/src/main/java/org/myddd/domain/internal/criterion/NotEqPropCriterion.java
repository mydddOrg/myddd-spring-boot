package org.myddd.domain.internal.criterion;

/**
 * 代表一个属性不等于另一个属性的查询条件
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class NotEqPropCriterion extends PropertyCompareCriterion {

    /**
     * 创建查询条件
     * @param propName 属性名
     * @param otherPropName 另一个属性名
     */
    public NotEqPropCriterion(String propName, String otherPropName) {
        super(propName, otherPropName);
        setOperator(" != ");
    }
}
