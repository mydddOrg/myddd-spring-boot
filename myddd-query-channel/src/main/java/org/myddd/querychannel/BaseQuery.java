package org.myddd.querychannel;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.domain.InstanceFactory;
import org.myddd.querychannel.basequery.QueryParameters;
import org.myddd.querychannel.basequery.QueryType;

import java.util.*;

/**
 * 查询基类，为NamedQuery、JpqlQuery和SqlQuery提供共同行为。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public abstract class BaseQuery<T> {
    private static QueryRepository repository;
    private QueryParameters parameters = PositionalParameters.create();
    private final NamedParameters mapParameters = NamedParameters.create();
    private int firstResult;
    private int maxResults;

    private static QueryRepository getQueryRepository(){
        if(Objects.isNull(repository)){
            repository = InstanceFactory.getInstance(QueryRepository.class);
        }
        return repository;
    }

    /**
     * 获取查询参数
     * @return 查询参数
     */
    public QueryParameters getParameters() {
        return parameters;
    }

    /**
     * 设置定位命名参数（数组方式）
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    public BaseQuery<T> setParameters(Object... parameters) {
        this.parameters = PositionalParameters.create(parameters);
        return this;
    }

    /**
     * 设置定位参数（列表方式）
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    public BaseQuery<T> setParameters(List<Object> parameters) {
        this.parameters = PositionalParameters.create(parameters);
        return this;
    }

    /**
     * 设置命名参数（Map形式，Key是参数名称，Value是参数值）
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    public BaseQuery<T> setParameters(Map<String, Object> parameters) {
        this.parameters = NamedParameters.create(parameters);
        return this;
    }

    /**
     * 添加一个命名参数，Key是参数名称，Value是参数值。
     * @param key 命名参数名称
     * @param value 参数值
     * @return 该对象本身
     */
    public BaseQuery<T> addParameter(String key, Object value) {
        mapParameters.add(key, value);
        this.parameters = mapParameters;
        return this;
    }

    /**
     * 设置命名参数（Map形式，Key是参数名称，Value是参数值）
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    public BaseQuery<T> setParameters(QueryParameters parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * 针对分页查询，获取firstResult。
     * firstResult代表从满足查询条件的记录的第firstResult + 1条开始获取数据子集。
     * @return firstResult的设置值，
     */
    public int getFirstResult() {
        return firstResult;
    }

    /**
     * 针对分页查询，设置firstResult。
     * firstResult代表从满足查询条件的记录的第firstResult + 1条开始获取数据子集。
     * @param firstResult 要设置的firstResult值。
     * @return 该对象本身
     */
    public BaseQuery<T> setFirstResult(int firstResult) {
        Preconditions.checkArgument(firstResult >= 0);
        this.firstResult = firstResult;
        return this;
    }

    /**
     * 针对分页查询，获取maxResults设置值。
     * maxResults代表从满足查询条件的结果中最多获取的数据记录的数量。
     * @return maxResults的设置值。
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * 针对分页查询，设置maxResults的值。
     * maxResults代表从满足查询条件的结果中最多获取的数据记录的数量。
     * @param maxResults 要设置的maxResults值
     * @return 该对象本身
     */
    public BaseQuery<T> setMaxResults(int maxResults) {
        Preconditions.checkArgument(maxResults > 0);
        this.maxResults = maxResults;
        return this;
    }

    public List<T> list() {
        return getRepository().find(this);
    }

    public T singleResult() {
        return getRepository().getSingleResult(this);
    }

    public abstract String queryName();

    public abstract QueryType queryType();

    /**
     * 获得仓储对象。
     * @return 仓储对象
     */
    protected QueryRepository getRepository() {
        return getQueryRepository();
    }


    public static class NamedParameters implements QueryParameters {

        private final Map<String, Object> params;

        public static NamedParameters create() {
            return new NamedParameters(new HashMap<>());
        }

        public static NamedParameters create(Map<String, Object> params) {
            return new NamedParameters(params);
        }

        private NamedParameters(Map<String, Object> params) {
            Preconditions.checkNotNull(params, "Parameters cannot be null");
            this.params = new HashMap<>(params);
        }

        public NamedParameters add(String key, Object value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkNotNull(value);
            params.put(key, value);
            return this;
        }

        public Map<String, Object> getParams() {
            return Collections.unmodifiableMap(params);
        }

    }

    public static class PositionalParameters implements QueryParameters {

        private Object[] params;

        public static PositionalParameters create() {
            return new PositionalParameters(new Object[]{});
        }

        public static PositionalParameters create(Object... params) {
            return new PositionalParameters(params);
        }

        public static PositionalParameters create(List<Object> params) {
            return new PositionalParameters(params.toArray());
        }

        private PositionalParameters(Object[] params) {
            Preconditions.checkNotNull(params,"params不能为空");
            this.params = Arrays.copyOf(params, params.length);
        }

        public Object[] getParams() {
            return Arrays.copyOf(params, params.length);
        }
    }
}
