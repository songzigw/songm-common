package cn.songm.common.beans;

import java.io.Serializable;
import java.util.Date;

public interface IEntity extends Serializable {

	public IEntity init();
	
	public void setUpdated(Date updated);
}
