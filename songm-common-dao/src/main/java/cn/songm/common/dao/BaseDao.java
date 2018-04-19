/*
 * Copyright [2016] [zhangsong <songm.cn>].
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package cn.songm.common.dao;

import java.util.List;
import java.util.Map;

import cn.songm.common.beans.PageBean;
import cn.songm.common.beans.PageParam;

/**
 * 抽象DAO
 * 
 * @author zhangsong
 *
 * @param <T>
 */
public interface BaseDao<T> {

    /**
     * 获取序列值
     * 
     * @return
     */
    public long selectSequence();

    /**
     * 单条插入数据
     * 
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 批量插入数据
     * 
     * @param list
     * @return
     */
    int insert(List<T> list);

    /**
     * 根据id单条更新数据
     * 
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 根据id单条更新数据
     * 
     * @param paramMap
     * @return
     */
    int update(Map<String, Object> paramMap);

    /**
     * 根据id批量更新数据
     * 
     * @param list
     * @return
     */
    int update(List<T> list);

    /**
     * 根据主键查询数据
     * 
     * @param id
     * @return
     */
    T selectOneById(Object id);

    /**
     * 根据column查询数据
     * 
     * @param paramMap
     * @return
     */
    public T selectOneByColumn(Map<String, Object> paramMap);

    /**
     * 根据column查询列表数据
     * 
     * @param paramMap
     * @return
     */
    public List<T> selectListByColumn(Map<String, Object> paramMap);

    /**
     * 根据column查询记录数
     * 
     * @param paramMap
     * @return
     */
    Long selectCountByColumn(Map<String, Object> paramMap);

    /**
     * 根据id删除数据
     * 
     * @param id
     * @return
     */
    int delete(Object id);

    /**
     * 根据id批量删除数据
     * 
     * @param list
     * @return
     */
    int delete(List<T> list);

    /**
     * 根据column批量删除数据
     * 
     * @param paramMap
     * @return
     */
    int delete(Map<String, Object> paramMap);

    /**
     * 分页查询数据
     * 
     * @param pageParam
     * @param paramMap
     * @return
     */
    public PageBean<T> selectListPage(PageParam pageParam,
            Map<String, Object> paramMap);

    public List<T> selectListByColumn(PageParam pageParam,
            Map<String, Object> paramMap);

    public Long selectCountByColumn(PageParam pageParam,
            Map<String, Object> paramMap);

}
