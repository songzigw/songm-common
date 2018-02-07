package cn.songm.common.beans;

import java.io.Serializable;

/**
 * 分页参数传递工具类
 * 
 * @author zhangsong
 */
public class PageParam implements Serializable {

    private static final long serialVersionUID = -952137614374314986L;

    /** 查询时间戳之前的数据 */
    private Long before;

    /**
     * 默认为第一页.
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认每页记录数(15).
     */
    public static final int DEFAULT_PAGE_SIZE = 15;

    /**
     * 最大每页记录数(100).
     */
    public static final int MAX_PAGE_SIZE = 100;

    // 当前页数
    private int pageNum = DEFAULT_PAGE_NUM;

    // 每页记录数
    private int pageSize = DEFAULT_PAGE_SIZE;

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
        this.pageNum = DEFAULT_PAGE_NUM;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * 带参数的构造函数
     * 
     * @param pageNum
     * @param pageSize
     */
    public PageParam(Integer pageNum, Integer pageSize) {
        if (null == pageNum || pageNum <= 0) {
            this.pageNum = DEFAULT_PAGE_NUM;
        } else {
            this.pageNum = pageNum;
        }
        if (null == pageSize || pageSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    /** 当前页数 */
    public int getPageNum() {
        return this.pageNum;
    }

    /** 当前页数 */
    public void setPageNum(int pageNum) {
        if (pageNum <= 0) {
            this.pageNum = DEFAULT_PAGE_NUM;
        } else {
            this.pageNum = pageNum;
        }
    }

    /** 每页记录数 */
    public int getPageSize() {
        return this.pageSize;
    }

    /** 每页记录数 */
    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

}
