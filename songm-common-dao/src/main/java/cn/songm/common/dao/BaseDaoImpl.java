package cn.songm.common.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import cn.songm.common.beans.Entity;
import cn.songm.common.beans.PageBean;
import cn.songm.common.beans.PageParam;

/**
 * 据访问层基础支撑类.
 * 
 * @author zhangsong
 */
public abstract class BaseDaoImpl<T extends Entity> extends SqlSessionDaoSupport
        implements BaseDao<T> {

    protected static final Log LOG = LogFactory.getLog(BaseDaoImpl.class);

    public static final String SQL_INSERT = "insert";
    public static final String SQL_BATCH_INSERT = "batchInsert";
    public static final String SQL_UPDATE_BY_ID = "updateByPrimaryKey";
    public static final String SQL_BATCH_UPDATE_BY_IDS = "batchUpdateByIds";
    public static final String SQL_BATCH_UPDATE_BY_COLUMN = "batchUpdateByColumn";
    public static final String SQL_SELECT_BY_ID = "selectByPrimaryKey";
    public static final String SQL_LIST_BY_COLUMN = "listByColumn";
    public static final String SQL_COUNT_BY_COLUMN = "countByColumn";
    public static final String SQL_DELETE_BY_ID = "deleteByPrimaryKey";
    public static final String SQL_BATCH_DELETE_BY_IDS = "batchDeleteByIds";
    public static final String SQL_BATCH_DELETE_BY_COLUMN = "batchDeleteByColumn";
    public static final String SQL_LIST_BY = "listBy";
    public static final String SQL_LIST_PAGE = "listPage";
    public static final String SQL_LIST_PAGE_COUNT = "listPageCount";
    // 根据当前分页参数进行统计
    public static final String SQL_COUNT_BY_PAGE_PARAM = "countByPageParam";

    /**
     * 注入SqlSessionTemplate实例 (要求Spring中进行SqlSessionTemplate的配置).
     * 可以调用sessionTemplate完成数据库操作.
     */
    @Autowired
    protected SqlSessionTemplate sessionTemplate;

    public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
        this.sessionTemplate = sessionTemplate;
    }

    public SqlSession getSqlSession() {
        return super.getSqlSession();
    }

    /**
     * 单条插入数据.
     */
    public int insert(T entity) {
        entity.init();
        return sessionTemplate.insert(getStatement(SQL_INSERT), entity);
    }

    /**
     * 批量插入数据.
     */
    public int insert(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        return sessionTemplate.insert(getStatement(SQL_BATCH_INSERT), list);
    }

    /**
     * 根据id单条更新数据.
     */
    public int update(T entity) {
        entity.setUpdated(new Date());
        return sessionTemplate.update(getStatement(SQL_UPDATE_BY_ID), entity);
    }

    /**
     * 根据id批量更新数据.
     */
    public int update(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        return sessionTemplate.update(getStatement(SQL_BATCH_UPDATE_BY_IDS),
                list);
    }

    /**
     * 根据column批量更新数据.
     */
    public int update(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return 0;
        }
        return sessionTemplate.update(getStatement(SQL_BATCH_UPDATE_BY_COLUMN),
                paramMap);
    }

    /**
     * 根据主键查询数据.
     */
    public T getById(Object id) {
        return sessionTemplate.selectOne(getStatement(SQL_SELECT_BY_ID), id);
    }

    /**
     * 根据column查询数据.
     */
    public T getByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_LIST_BY_COLUMN),
                paramMap);
    }

    /**
     * 根据条件查询 getBy: selectOne <br/>
     * 
     * @param paramMap
     * @return
     */
    public T getBy(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_LIST_BY), paramMap);
    }

    /**
     * 根据条件查询列表数据.
     */
    public List<T> listBy(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectList(getStatement(SQL_LIST_BY), paramMap);
    }

    /**
     * 根据column查询列表数据.
     */
    public List<T> listByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectList(getStatement(SQL_LIST_BY_COLUMN),
                paramMap);
    }

    /**
     * 根据column查询记录数
     * 
     * @param paramMap
     * @return
     */
    public Long getCountByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_COLUMN),
                paramMap);
    }

    /**
     * 根据id删除数据
     * 
     * @param id
     * @return
     */
    public int delete(String id) {
        return (int) sessionTemplate.delete(getStatement(SQL_DELETE_BY_ID), id);
    }

    /**
     * 根据id批量删除数据
     * 
     * @param list
     * @return
     */
    public int delete(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        } else {
            return (int) sessionTemplate
                    .delete(getStatement(SQL_BATCH_DELETE_BY_IDS), list);
        }
    }

    /**
     * 根据column批量删除数据
     * 
     * @param paramMap
     * @return
     */
    public int delete(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return 0;
        } else {
            return (int) sessionTemplate
                    .delete(getStatement(SQL_BATCH_DELETE_BY_COLUMN), paramMap);
        }
    }

    /**
     * 分页查询数据
     * 
     * @param pageParam
     * @param paramMap
     * @return
     */
    public PageBean<T> listPage(PageParam pageParam,
            Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }

        if (pageParam.getPageNum() > 1) {
            paramMap.put("pageBefore", pageParam.getBefore());
        }
        // 统计总记录数
        Long totalCount = sessionTemplate
                .selectOne(getStatement(SQL_LIST_PAGE_COUNT), paramMap);

        // 校验当前页数
        int currentPage = PageBean.checkCurrentPage(totalCount.intValue(),
                pageParam.getNumPerPage(), pageParam.getPageNum());
        pageParam.setPageNum(currentPage); // 为当前页重新设值
        // 校验页面输入的每页记录数numPerPage是否合法
        int numPerPage = PageBean.checkNumPerPage(pageParam.getNumPerPage()); // 校验每页记录数
        pageParam.setNumPerPage(numPerPage); // 重新设值

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst",
                (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("pageSize", pageParam.getNumPerPage());
        paramMap.put("startRowNum",
                (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("endRowNum",
                pageParam.getPageNum() * pageParam.getNumPerPage());

        // 获取分页数据集
        List<T> list = sessionTemplate.selectList(getStatement(SQL_LIST_PAGE),
                paramMap);

        PageBean<T> pageBean = null;
        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            Map<String, Object> countResultMap = sessionTemplate
                    .selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            pageBean = new PageBean<T>(pageParam.getPageNum(),
                    pageParam.getNumPerPage(), totalCount.intValue(), list,
                    countResultMap);
        } else {
            // 构造分页对象
            pageBean = new PageBean<T>(pageParam.getPageNum(),
                    pageParam.getNumPerPage(), totalCount.intValue(), list);
        }
        if (pageParam.getBefore() != null) {
            pageBean.setBefore(pageParam.getBefore());
        } else {
            pageBean.setBefore(new Date().getTime());
        }
        return pageBean;
    }

    /**
     * 获取Mapper命名空间
     * 
     * @param sqlId
     * @return
     */
    public String getStatement(String sqlId) {
        String name = this.getClass().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(".").append(sqlId);
        return sb.toString();
    }

}
