package cn.songm.common.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页组件.
 * 
 * @author zhangsong
 */
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = -1081813527339854550L;

    // 查询时间戳之前的数据
    private Long before;

    // 指定的或是页面参数
    private int pageNum; // 当前页
    private int pageSize; // 每页显示多少条

    // 查询数据库
    private int totalCount; // 总记录数
    private List<T> recordList = new ArrayList<T>(0); // 本页的数据列表

    // 计算
    private int totalPage; // 总页数
    private int beginPageIndex; // 页码列表的开始索引（包含）
    private int endPageIndex; // 页码列表的结束索引（包含）

    private Map<String, Object> countResultMap; // 当前分页条件下的统计结果

    /**
     * 计算总页数 .
     * 
     * @param totalCount
     *            总记录数.
     * @param pageSize
     *            每页记录数.
     * @return totalPage 总页数.
     */
    public static int countTotalPage(int totalCount, int pageSize) {
        if (totalCount % pageSize == 0) {
            // 刚好整除
            return totalCount / pageSize;
        } else {
            // 不能整除则总页数为：商 + 1
            return totalCount / pageSize + 1;
        }
    }

    /**
     * 校验当前页数pageNum.<br/>
     * 1、先根据总记录数totalCount和每页记录数pageSize，计算出总页数totalPage.<br/>
     * 2、判断页面提交过来的当前页数pageNum是否大于总页数totalPage，大于则返回totalPage.<br/>
     * 3、判断pageNum是否小于1，小于则返回1.<br/>
     * 4、其它则直接返回pageNum .
     * 
     * @param totalCount
     *            要分页的总记录数 .
     * @param pageSize
     *            每页记录数大小 .
     * @param pageNum
     *            输入的当前页数 .
     * @return pageNum .
     */
    public static int checkPageNum(int totalCount, int pageSize, int pageNum) {
        int totalPage = PageBean.countTotalPage(totalCount, pageSize); // 最大页数
        if (pageNum > totalPage) {
            // 如果页面提交过来的页数大于总页数，则将当前页设为总页数
            // 此时要求totalPage要大于获等于1
            if (totalPage < 1) {
                return 1;
            }
            return totalPage;
        } else if (pageNum < 1) {
            return 1; // 当前页不能小于1（避免页面输入不正确值）
        } else {
            return pageNum;
        }
    }

    /**
     * 校验页面输入的每页记录数pageSize是否合法.<br/>
     * 1、当页面输入的每页记录数pageSize大于允许的最大每页记录数MAX_PAGE_SIZE时，返回MAX_PAGE_SIZE.<br/>
     * 2、如果pageSize小于1，则返回默认的每页记录数DEFAULT_PAGE_SIZE.
     * 
     * @param pageSize
     *            页面输入的每页记录数.
     * @return checkPageSize .
     */
    public static int checkPageSize(int pageSize) {
        if (pageSize > PageParam.MAX_PAGE_SIZE) {
            return PageParam.MAX_PAGE_SIZE;
        } else if (pageSize < 1) {
            return PageParam.DEFAULT_PAGE_SIZE;
        } else {
            return pageSize;
        }
    }

    public PageBean() {
    }
    
    /**
     * 只接受前4个必要的属性，会自动的计算出其他3个属生的值
     * 
     * @param pageNum
     * @param pageSize
     * @param totalCount
     * @param recordList
     */
    public PageBean(int pageNum, int pageSize, int totalCount,
            List<T> recordList) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.recordList = recordList;

        // 计算总页码
        totalPage = (totalCount + pageSize - 1) / pageSize;

        // 计算 beginPageIndex 和 endPageIndex
        if (totalPage <= 10) {
            // 如果总页数不多于10页，则全部显示
            beginPageIndex = 1;
            endPageIndex = totalPage;
        } else {
            // 如果总页数多于10页，则显示当前页附近的共10个页码
            // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
            beginPageIndex = pageNum - 4;
            endPageIndex = pageNum + 5;
            // 当前面的页码不足4个时，则显示前10个页码
            if (beginPageIndex < 1) {
                beginPageIndex = 1;
                endPageIndex = 10;
            }
            // 当后面的页码不足5个时，则显示后10个页码
            if (endPageIndex > totalPage) {
                endPageIndex = totalPage;
                beginPageIndex = totalPage - 10 + 1;
            }
        }
    }

    /**
     * 只接受前5个必要的属性，会自动的计算出其他3个属生的值
     * 
     * @param pageNum
     * @param pageSize
     * @param totalCount
     * @param recordList
     */
    public PageBean(int pageNum, int pageSize, int totalCount,
            List<T> recordList, Map<String, Object> countResultMap) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.recordList = recordList;
        this.countResultMap = countResultMap;

        // 计算总页码
        totalPage = (totalCount + pageSize - 1) / pageSize;

        // 计算 beginPageIndex 和 endPageIndex
        if (totalPage <= 10) {
            // 如果总页数不多于10页，则全部显示
            beginPageIndex = 1;
            endPageIndex = totalPage;
        } else {
            // 如果总页数多于10页，则显示当前页附近的共10个页码
            // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
            beginPageIndex = pageNum - 4;
            endPageIndex = pageNum + 5;
            // 当前面的页码不足4个时，则显示前10个页码
            if (beginPageIndex < 1) {
                beginPageIndex = 1;
                endPageIndex = 10;
            }
            // 当后面的页码不足5个时，则显示后10个页码
            if (endPageIndex > totalPage) {
                endPageIndex = totalPage;
                beginPageIndex = totalPage - 10 + 1;
            }
        }
    }

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getBeginPageIndex() {
        return beginPageIndex;
    }

    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }

    public int getEndPageIndex() {
        return endPageIndex;
    }

    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }

    public Map<String, Object> getCountResultMap() {
        return countResultMap;
    }

    public void setCountResultMap(Map<String, Object> countResultMap) {
        this.countResultMap = countResultMap;
    }

    public Long getBefore() {
        return before;
    }

    public void setBefore(Long before) {
        this.before = before;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("PageBean [");
        str.append("before=").append(before)
                .append(", pageNum=").append(pageNum)
                .append(", pageSize=").append(pageSize)
                .append(", totalCount=").append(totalCount)
                .append(", recordList=").append(recordList)
                .append(", totalPage=").append(totalPage)
                .append(", beginPageIndex=").append(beginPageIndex)
                .append(", endPageIndex=").append(endPageIndex)
                .append(", countResultMap=").append(countResultMap).append("]");
        return str.toString();
    }

}
