package cn.songm.common.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import cn.songm.common.beans.IEntity;
import cn.songm.common.beans.PageBean;
import cn.songm.common.beans.PageParam;

/**
 * 据访问层基础支撑类.
 * 
 * @author zhangsong
 */
public abstract class BaseDaoImpl<T extends IEntity> implements BaseDao<T> {

    protected static final Log LOG = LogFactory.getLog(BaseDaoImpl.class);

    public static final String SQL_SEQUENCE_NEXT = "sequenceNext";
    public static final String SQL_INSERT = "insert";
    public static final String SQL_BATCH_INSERT = "batchInsert";
    public static final String SQL_UPDATE_BY_ID = "updateByPrimaryKey";
    public static final String SQL_BATCH_UPDATE_BY_IDS = "batchUpdateByIds";
    // public static final String SQL_BATCH_UPDATE_BY_COLUMN = "batchUpdateByColumn";
    public static final String SQL_ONE_BY_ID = "selectByPrimaryKey";
    public static final String SQL_LIST_BY_COLUMN = "listByColumn";
    public static final String SQL_COUNT_BY_COLUMN = "countByColumn";
    public static final String SQL_DELETE_BY_ID = "deleteByPrimaryKey";
    public static final String SQL_BATCH_DELETE_BY_IDS = "batchDeleteByIds";
    public static final String SQL_BATCH_DELETE_BY_COLUMN = "batchDeleteByColumn";
    // public static final String SQL_LIST_BY = "listBy";
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

    @Override
    public long selectSequence() {
        return sessionTemplate.selectOne(getStatement(SQL_SEQUENCE_NEXT));
    }

    @Override
    public int insert(T entity) {
        entity.init();
        return sessionTemplate.insert(getStatement(SQL_INSERT), entity);
    }

    @Override
    public int insert(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        return sessionTemplate.insert(getStatement(SQL_BATCH_INSERT), list);
    }

    @Override
    public int update(T entity) {
        entity.setUpdated(new Date());
        return sessionTemplate.update(getStatement(SQL_UPDATE_BY_ID), entity);
    }

    @Override
    public int update(Map<String, Object> paramMap) {
        paramMap.put("updated", new Date());
        return sessionTemplate.update(getStatement(SQL_UPDATE_BY_ID), paramMap);
    }
    
    @Override
    public int update(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        return sessionTemplate.update(getStatement(SQL_BATCH_UPDATE_BY_IDS),
                list);
    }

    @Override
    public T selectOneById(Object id) {
        return sessionTemplate.selectOne(getStatement(SQL_ONE_BY_ID), id);
    }

    @Override
    public T selectOneByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_LIST_BY_COLUMN),
                paramMap);
    }

    @Override
    public List<T> selectListByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectList(getStatement(SQL_LIST_BY_COLUMN),
                paramMap);
    }

    @Override
    public Long selectCountByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_COLUMN),
                paramMap);
    }

    @Override
    public int delete(Object id) {
        return (int) sessionTemplate.delete(getStatement(SQL_DELETE_BY_ID), id);
    }

    @Override
    public int delete(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        } else {
            return (int) sessionTemplate
                    .delete(getStatement(SQL_BATCH_DELETE_BY_IDS), list);
        }
    }

    @Override
    public int delete(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return 0;
        } else {
            return (int) sessionTemplate
                    .delete(getStatement(SQL_BATCH_DELETE_BY_COLUMN), paramMap);
        }
    }

    @Override
    public PageBean<T> selectListPage(PageParam pageParam,
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
        int pageNum = PageBean.checkPageNum(totalCount.intValue(),
                pageParam.getPageSize(), pageParam.getPageNum());
        pageParam.setPageNum(pageNum); // 为当前页重新设值
        // 校验页面输入的每页记录数PageSize是否合法
        int pageSize = PageBean.checkPageSize(pageParam.getPageSize()); // 校验每页记录数
        pageParam.setPageSize(pageSize); // 重新设值

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst",
                (pageParam.getPageNum() - 1) * pageParam.getPageSize());
        paramMap.put("pageSize", pageParam.getPageSize());
        paramMap.put("startRowNum",
                (pageParam.getPageNum() - 1) * pageParam.getPageSize());
        paramMap.put("endRowNum",
                pageParam.getPageNum() * pageParam.getPageSize());

        // 获取分页数据集
        List<T> list = sessionTemplate.selectList(getStatement(SQL_LIST_PAGE),
                paramMap);

        PageBean<T> pageBean = null;
        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            Map<String, Object> countResultMap = sessionTemplate
                    .selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            pageBean = new PageBean<T>(pageParam.getPageNum(),
                    pageParam.getPageSize(), totalCount.intValue(), list,
                    countResultMap);
        } else {
            // 构造分页对象
            pageBean = new PageBean<T>(pageParam.getPageNum(),
                    pageParam.getPageSize(), totalCount.intValue(), list);
        }
        if (pageParam.getBefore() != null) {
            pageBean.setBefore(pageParam.getBefore());
        } else {
            pageBean.setBefore(new Date().getTime());
        }
        return pageBean;
    }

    @Override
    public List<T> selectListByColumn(PageParam pageParam, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }

        if (pageParam.getPageNum() > 1) {
            paramMap.put("pageBefore", pageParam.getBefore());
        }

        // 校验页面输入的每页记录数PageSize是否合法
        int pageSize = PageBean.checkPageSize(pageParam.getPageSize());
        pageParam.setPageSize(pageSize);

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst",
                (pageParam.getPageNum() - 1) * pageParam.getPageSize());
        paramMap.put("pageSize", pageParam.getPageSize());
        //paramMap.put("startRowNum",
        //        (pageParam.getPageNum() - 1) * pageParam.getPageSize());
        //paramMap.put("endRowNum",
        //        pageParam.getPageNum() * pageParam.getPageSize());

        // 获取分页数据集
        return sessionTemplate.selectList(getStatement(SQL_LIST_PAGE), paramMap);
    }
    
    @Override
    public Long selectCountByColumn(PageParam pageParam, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }

        if (pageParam.getPageNum() > 1) {
            paramMap.put("pageBefore", pageParam.getBefore());
        }
        // 统计总记录数
        return sessionTemplate.selectOne(getStatement(SQL_LIST_PAGE_COUNT), paramMap);
    }
    
    /**
     * 获取Mapper命名空间
     * 
     * @param sqlId
     * @return
     */
    public String getStatement(String sqlId) {
		return new StringBuilder(getClass().getName()).append(".").append(sqlId).toString();
    }

}
