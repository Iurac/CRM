package com.iurac.crm.workbench.dao;

import com.iurac.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {
    
    int save(Clue clue);

    int getTotalByCondition(Map<String, Object> map);

    List<Clue> getClueByCondition(Map<String, Object> map);

    Clue getClueById(String id);

    int update(Clue clue);

    int deleteByIds(String[] ids);

    Clue getDetail(String id);

    int delete(String cid);
}
