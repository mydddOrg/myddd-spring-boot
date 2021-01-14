package org.myddd.domain.internal.criterion;

import org.myddd.domain.NamedParameters;
import org.myddd.domain.QueryCriterion;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * "空"条件，什么也不做。为了简化条件之间的运算
 *
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class EmptyCriterion extends AbstractCriterion {

    private static final EmptyCriterion INSTANCE = new EmptyCriterion();

    public static final EmptyCriterion singleton() {
        return INSTANCE;
    }

    private EmptyCriterion() {
    }

    @Override
    public QueryCriterion and(QueryCriterion criterion) {
        return criterion;
    }

    @Override
    public QueryCriterion or(QueryCriterion criterion) {
        return criterion;
    }

    @Override
    public QueryCriterion not() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toQueryString() {
        return "";
    }

    @Override
    public NamedParameters getParameters() {
        return NamedParameters.create();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EmptyCriterion)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).toHashCode();
    }

}
