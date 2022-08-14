package org.myddd.extensions.organisation.organization;

import java.util.List;

public class WithLimitsQueryVO {

    private List<Long> limits = List.of();

    public List<Long> getLimits() {
        return limits;
    }

    public void setLimits(List<Long> limits) {
        this.limits = limits;
    }
}
