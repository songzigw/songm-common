package cn.songm.common.beans;

import java.util.Date;

import cn.songm.common.redis.annotations.HashField;

/**
 * 实体基类
 * 
 * @author zhangsong
 *
 */
public abstract class Entity implements IEntity {

    private static final long serialVersionUID = 5568936941569759043L;

    /** 数据版本号 */
    @HashField("version")
    private Integer version;
    /** 创建时间 */
    @HashField("created")
    private Date created;
    /** 修改时间 */
    @HashField("updated")
    private Date updated;
    /** 描述 */
    @HashField("remark")
    private String remark;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return String.format(
                "Entity [version=%s, created=%tc, updated=%tc, remark=%s]",
                version, created, updated, remark);
    }

    @Override
    public Entity init() {
        if (this.version == null) {
            this.version = 0;
        }
        if (this.created == null) {
            this.created = new Date();
        }
        return this;
    }
}
