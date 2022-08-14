package org.myddd.extensions.organisation.organization;


import java.util.List;

public class WithLimitsPageQueryVO {

    private int page = 0;

    private int pageSize = 100;

    private List<Long> limits = List.of();

    private String search = "";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public List<Long> getLimits() {
        return limits;
    }

    public void setLimits(List<Long> limits) {
        this.limits = limits;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }
}
