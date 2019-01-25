package cn.songm.common.dao;

import cn.songm.common.entity.Tab01;

public interface Tab01Dao extends BaseDao<Tab01> {

	/**
	 * 自增
	 * 
	 * @param no
	 */
	public void incr(String no);
}
