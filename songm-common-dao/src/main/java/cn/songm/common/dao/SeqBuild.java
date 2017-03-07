package cn.songm.common.dao;

import java.io.Serializable;

/**
 * 此实体没有关联的表，只作用于序列查找时传参使用
 * 
 * @author zhangsong
 */
public class SeqBuild implements Serializable {
    private static final long serialVersionUID = -7749296843635154045L;

    /** 序列名称 **/
    private String seqName;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

}