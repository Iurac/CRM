package com.iurac.crm.workbench.dao;

import com.iurac.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    int getByCids(String[] ids);

    int deleteByCids(String[] ids);

    List<ClueRemark> getRemarkListByCid(String cid);

    int saveRemark(ClueRemark clueRemark);

    int updateRemark(ClueRemark clueRemark);

    int deleteRemark(String id);
}
