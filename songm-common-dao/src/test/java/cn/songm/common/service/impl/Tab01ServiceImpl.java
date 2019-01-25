package cn.songm.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.songm.common.dao.Tab01Dao;
import cn.songm.common.entity.Tab01;
import cn.songm.common.service.Tab01Service;

@Service("tab01Service")
public class Tab01ServiceImpl implements Tab01Service  {

    @Autowired
    private Tab01Dao tab01Dao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Tab01 add() {
        Tab01 t = new Tab01();
        t.setCount(0);
        if (tab01Dao.insert(t) != 1) {
            throw new RuntimeException("Add Tab01 fail.");
        }
        return t;
    }
    
    @Override
    synchronized public void updateCount(String on, int i) {
        _update(on, i);
    }
    
    @Transactional(rollbackFor = Exception.class)
    private void _update(String on, int i) {
        Tab01 t = getById(on);
        if (t.getCount() == null) {
            t.setCount(0);
        }
        t.setCount(t.getCount() + i);
        tab01Dao.update(t);
    }


    @Override
    public Tab01 getById(String no) {
        return tab01Dao.selectOneById(no);
    }


    @Override
    public void incr(String no) {
        tab01Dao.incr(no);
    }
    
}
