package cn.songm.common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class SeqTableDaoImpl implements SeqTableDao {

    @Autowired
    protected SqlSessionTemplate sessionTemplate;

    public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
        this.sessionTemplate = sessionTemplate;
    }

    @Override
    public String getSeqNextValue(SeqBuild seqBuild) {
        return sessionTemplate.selectOne(
                this.getClass().getName() + ".getSeqNextValue", seqBuild);
    }
}
