package com.iurac.crm.workbench.dao;

import com.iurac.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {
    int unbund(String id);

    int bund(ClueActivityRelation clueActivityRelation);

    List<ClueActivityRelation> getListByCid(String cid);

    int delete(String id);
}
