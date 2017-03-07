package cn.songm.common.beans;

import java.util.Date;

import cn.songm.common.utils.StringUtils;

/**
 * 实体基类
 * 
 * @author zhangsong
 *
 */
public abstract class Entity {

    /** 系统编号(一般情况)，在没有业务ID编号的情况下，这个键可以充当业务ID，即主键id */
    private String no;
    /** 版本号 */
    private String version;
    /** 创建时间 */
    private Date created;
    /** 修改时间 */
    private Date updated;
    /** 描述 */
    private String remark;
    /** 数据激活状态 */
    private Boolean status;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "Entity [no=%s, version=%s, created=%tc, updated=%tc, status=%b, remark=%s]",
                no, version, created, updated, status, remark);
    }

    public void init() {
        if (this.no == null) {
            this.no = StringUtils.get32UUID();
        }
        if (this.created == null) {
            this.created = new Date();
        }
        if (this.status == null) {
            this.status = true;
        }
    }
}
