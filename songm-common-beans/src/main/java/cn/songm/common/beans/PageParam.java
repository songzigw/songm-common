package cn.songm.common.beans;

import java.io.Serializable;

/**
 * 分页参数传递工具类
 * 
 * @author zhangsong
 */
public class PageParam implements Serializable {

    private static final long serialVersionUID = -952137614374314986L;

    /** 时间戳 */
    private Long before;

    /**
     * 默认为第一页.
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认每页记录数(15).
     */
    public static final int DEFAULT_NUM_PER_PAGE = 15;

    /**
     * 最大每页记录数(100).
     */
    public static final int MAX_PAGE_SIZE = 100;

    // 当前页数
    private int pageNum = DEFAULT_PAGE_NUM;

    // 每页记录数
    private int numPerPage;

    public Long getBefore() {
        return before;
    }

    public void setBefore(Long before) {
        this.before = before;
    }

    /**
     * 默认构造函数
     */
    public PageParam() {
    }

    /**
     * 带参数的构造函数
     * 
     * @param pageNum
     * @param numPerPage
     */
    public PageParam(Integer pageNum, Integer numPerPage) {
        if (pageNum == null) pageNum = 0;
        if (numPerPage == null) numPerPage = 0;
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
    }

    /** 当前页数 */
    public int getPageNum() {
        return pageNum > 0 ? pageNum : DEFAULT_PAGE_NUM;
    }

    /** 当前页数 */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /** 每页记录数 */
    public int getNumPerPage() {
        return numPerPage > 0 ? numPerPage : DEFAULT_NUM_PER_PAGE;
    }

    /** 每页记录数 */
    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

}
