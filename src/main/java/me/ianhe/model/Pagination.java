package me.ianhe.model;

import java.util.Collection;

/**
 * 分页信息
 *
 * @author iHelin
 * @since 2017/11/13 17:30
 */
public class Pagination<T> {

    /**
     * 总个数
     */
    private long totalCount;
    /**
     * 总页数
     */
    private int totalPageNum;
    /**
     * 最后一页
     */
    private int endPage;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 第一页
     */
    private int startPage;
    /**
     * 状态
     */
    private String status;
    /**
     * 数据
     */
    private Collection data;

    private static final int PRE_AFT_PAGE_NUM = 3;

    public Pagination(Collection<? extends T> data, long totalCount, int currentPage, int pageLength) {
        if (totalCount <= 0) {
            totalCount = 0;
        }
        if (currentPage <= 0) {
            currentPage = 1;
        }

        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.data = data;
        this.status = "success";

        if (pageLength <= 0) {
            pageLength = 1;
        }
        this.totalPageNum = (int) Math.ceil((double) totalCount / pageLength);
        startPage = currentPage - PRE_AFT_PAGE_NUM;
        if (startPage <= 0) {
            startPage = Math.min(1, totalPageNum);
        }
        endPage = currentPage + PRE_AFT_PAGE_NUM;
        if (endPage > totalPageNum) {
            endPage = totalPageNum;
        }
    }

    public long getTotalCount() {
        return totalCount;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public Collection getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

}
