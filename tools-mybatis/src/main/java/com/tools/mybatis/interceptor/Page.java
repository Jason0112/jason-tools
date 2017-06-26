package com.tools.mybatis.interceptor;

import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :分页对象
 */
public class Page<E> extends ArrayList<E> {
    private static final long serialVersionUID = 1L;

    /**
     * 不进行count查询
     */
    private static final int NO_SQL_COUNT = -1;
    /**
     * 进行count查询
     */
    private static final int SQL_COUNT = 0;
    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 起始行
     */
    private int startRow;
    /**
     * 末行
     */
    private int endRow;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 分页合理化
     */
    private boolean reasonable;
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * 排序方式
     */
    private String orderBy;
    /**
     * 是否排序
     */
    private boolean isSort =false;

    public Page() {
        super();
    }


    public Page(RowBounds rowBounds, boolean count) {
        this(rowBounds, count ? Page.SQL_COUNT : Page.NO_SQL_COUNT);
    }

    public Page(RowBounds rowBounds, int total) {
        super(rowBounds.getLimit() > -1 ? rowBounds.getLimit() : 0);
        this.pageSize = rowBounds.getLimit();
        this.startRow = rowBounds.getOffset();
        //RowBounds方式默认不求count总数，如果想求count,可以修改这里为SQL_COUNT
        this.total = total;
        this.endRow = this.startRow + this.pageSize;
    }

    public Page(int pageNum, int pageSize, boolean count) {
        this(pageNum, pageSize, null, null, count ? Page.SQL_COUNT : Page.NO_SQL_COUNT, false);
    }

    public Page(int pageNum, int pageSize, String sortField, String orderBy, int total, boolean isSort) {
        super(pageSize > -1 ? pageSize : 0);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.orderBy = orderBy;
        this.total = total;
        this.isSort = isSort;
        calculateStartAndEndRow();
    }

    public List<E> getResult() {
        return this;
    }

    public int getPages() {
        return pages;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        //分页合理化，针对不合理的页码自动处理
        this.pageNum = (reasonable && pageNum <= 0) ? 1 : pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (pageSize > 0) {
            pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
        } else {
            pages = 0;
        }
        //分页合理化，针对不合理的页码自动处理
        if (reasonable && pageNum > pages) {
            pageNum = pages;
            calculateStartAndEndRow();
        }
    }

    public void setReasonable(boolean reasonable) {
        this.reasonable = reasonable;
        //分页合理化，针对不合理的页码自动处理
        if (this.reasonable && this.pageNum <= 0) {
            this.pageNum = 1;
            calculateStartAndEndRow();
        }
    }

    /**
     * 计算起止行号
     */
    private void calculateStartAndEndRow() {
        this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;
        this.endRow = this.startRow + this.pageSize * (this.pageNum > 0 ? 1 : 0);
    }

    public boolean isCount() {
        return this.total > NO_SQL_COUNT;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                ", reasonable=" + reasonable +
                ", sortField='" + sortField + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", isSort=" + isSort +
                '}';
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isSort() {
        return isSort;
    }

    public void setIsSort(boolean isSort) {
        this.isSort = isSort;
    }
}

