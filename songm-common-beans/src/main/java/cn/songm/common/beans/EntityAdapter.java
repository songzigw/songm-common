package cn.songm.common.beans;

import cn.songm.common.redis.annotations.HashField;
import cn.songm.common.utils.StringUtils;

/**
 * 实体默认适配类
 * 
 * @author zhangsong
 *
 */
public class EntityAdapter extends Entity {

	private static final long serialVersionUID = -3689797880160694822L;

	/** 系统编号(一般情况)，在没有业务ID编号的情况下，这个键可以充当业务ID，即主键id */
    @HashField("no")
	private String no;
	
	public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
    
    @Override
    public Entity init() {
        if (this.no == null) {
            this.no = StringUtils.get32UUID();
        }
        return super.init();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((no == null) ? 0 : no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityAdapter other = (EntityAdapter) obj;
		if (no == null) {
			if (other.no != null)
				return false;
		} else if (!no.equals(other.no))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
                "EntityAdapter [no=%s, version=%s, created=%tc, updated=%tc, remark=%s]",
                no, getVersion(), getCreated(), getUpdated(), getRemark());
	}
}
