package com.iurac.crm.workbench.dao;

import com.iurac.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran tran);

    List<Tran> getTransactionByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);
}
