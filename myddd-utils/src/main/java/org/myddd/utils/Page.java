package org.myddd.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: 分页对象. 包含当前页数据及分页信息如总记录数.
 *
 * @author yshzhong
 *
 */
public class Page<T> {

    public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

    private long start; // 当前页第一条数据在List中的位置,从0开始

    private List<T> data = new ArrayList<>(); // 当前页中存放的记录,类型一般为List

    private long resultCount; // 总记录数


    private Page(){

    }

    public Page(long start, long totalSize, List<T> data) {
        Preconditions.checkArgument(start >= 0, "Start must not be negative!");
        Preconditions.checkArgument(totalSize >= 0, "Total size must not be negative!");
        this.start = start;
        this.resultCount = totalSize;
        this.data = data;
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
    }


    public Page(long start, long totalSize, int pageSize, List<T> data) {
        this(start, totalSize, data);
        Preconditions.checkArgument(pageSize > 0, "Page size must be greater than 0!");
        this.pageSize = pageSize;
    }


    public static <T> Page<T> builder(Class<T> tClass){
        Preconditions.checkNotNull(tClass);
        return new Page<>();
    }

    public Page<T> stat(long start){
        this.start = start;
        return this;
    }

    public Page<T> pageSize(int pageSize){
        this.pageSize = pageSize;
        return this;
    }

    public Page<T> data(List<T> data){
        this.data = data;
        return this;
    }

    public Page<T> totalSize(long totalSize){
        this.resultCount = totalSize;
        return this;
    }



    /**
     * 获得第一条记录的截取位置
     * @return 第一条记录的截取位置
     */
    public long getStart() {
        return start;
    }

    /**
     * 取总记录数.
     * @return 符合查询条件的记录总数
     */
    public long getResultCount() {
        return this.resultCount;
    }

    /**
     * 取总页数.
     * @return 符合查询条件的记录总页数
     */
    public long getPageCount() {
        if (resultCount % pageSize == 0) {
            return resultCount / pageSize;
        } else {
            return resultCount / pageSize + 1;
        }
    }

    /**
     * 取每页数据容量.
     * @return 每页可容纳的记录数量
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取当前页中的记录.
     * @return 当前数据页
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 取该页当前页码，0为第一页
     * @return 当前页的页码
     */
    public long getPageIndex() {
        return start / pageSize;
    }

    /**
     * 该页是否有下一页.
     * @return 如果当前页是最后一页，返回false，否则返回true
     */
    public boolean hasNextPage() {
        return this.getPageIndex() < this.getPageCount() - 1;
    }

    /**
     * 该页是否有上一页.
     * @return 如果当前页码为0，返回true，否则返回false
     */
    public boolean hasPreviousPage() {
        return this.getPageIndex() > 0;
    }

    /**
     * 获取任一页第一条数据在数据集的位置.
     *
     * @param pageIndex 从0开始的页号
     * @param pageSize 每页的容量
     * @return 该页第一条数据在符合条件的查询结果中的位置。
     */
    public static int getStartOfPage(int pageIndex, int pageSize) {
        return pageIndex * pageSize;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
