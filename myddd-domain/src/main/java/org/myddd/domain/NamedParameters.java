package org.myddd.domain;


import org.myddd.utils.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询语言或命名查询的命名参数集。JPA、Hibernate和SQL等都支持定位
 * 参数(如"... where e.name = ?")和命名参数(如"... where name = :name")两种形式。<br>
 * 尽可能采用命名参数的形式，定位参数是落后的形式。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class NamedParameters implements QueryParameters {
    private Map<String, Object> params = new HashMap<>();
    
    /**
     * 创建一个空查询参数集
     * @return 一个基于Map的查询参数集
     */
    public static NamedParameters create() {
        return new NamedParameters(new HashMap<>());
    }
    
    /**
     * 创建一个查询参数集
     * @param params 要设置的查询参数的map，Key为参数名，Value为参数值
     * @return 一个基于Map的查询参数集
     */
    public static NamedParameters create(Map<String, Object> params) {
        return new NamedParameters(params);
    }

    private NamedParameters(Map<String, Object> params) {
        Assert.notNull(params, "Parameters cannot be null");
        this.params = new HashMap<>(params);
    }
    
    /**
     * 添加一个命名参数
     * @param key 参数名称
     * @param value 参数值
     * @return 当前对象本身
     */
    public NamedParameters add(String key, Object value) {
        Assert.notBlank(key);
        Assert.notNull(value);
        params.put(key, value);
        return this;
    }

    /**
     * 将另一个NamedParameters合并进来。
     * @param other 要合并的参数集
     * @return 该对象本身。其参数集是原有的参数集与另一个参数集合并后的结果
     */
    public NamedParameters add(NamedParameters other) {
        Assert.notNull(other);
        params.putAll(other.getParams());
        return this;
    }

    /**
     * 获得参数Map
     * @return 参数Map
     */
    public Map<String, Object> getParams() {
        return Collections.unmodifiableMap(params);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NamedParameters)) {
            return false;
        }

        NamedParameters that = (NamedParameters) other;

        return this.getParams().equals(that.getParams());
    }

    @Override
    public int hashCode() {
        return getParams().hashCode();
    }

    /**
     * 获得参数集的字符串表示形式
     * @return 当前对象的字符串表示形式
     */
    @Override
    public String toString() {
        return params.toString();
    }
    
}
