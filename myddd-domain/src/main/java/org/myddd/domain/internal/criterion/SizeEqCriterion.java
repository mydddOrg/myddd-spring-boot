package org.myddd.domain.internal.criterion;

/**
 * 判断某个集合属性的记录数等于指定值的查询条件
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class SizeEqCriterion extends SizeCompareCriterion {

    /**
     * 创建查询条件
     * @param propName 属性名
     * @param value 属性值
     */
    public SizeEqCriterion(String propName, int value) {
        super(propName, value);
        setOperator(" = ");
     }
}
