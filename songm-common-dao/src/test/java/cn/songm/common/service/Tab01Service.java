package cn.songm.common.service;

import cn.songm.common.entity.Tab01;

public interface Tab01Service {

	public Tab01 add();

	public void updateCount(String on, int i);

	public void incr(String no);

	public Tab01 getById(String no);
}
