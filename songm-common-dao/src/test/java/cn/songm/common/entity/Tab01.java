package cn.songm.common.entity;

import cn.songm.common.beans.EntityAdapter;

public class Tab01 extends EntityAdapter implements java.io.Serializable {

    private static final long serialVersionUID = -95087851354202707L;

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}