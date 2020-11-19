package com.iurac.crm.workbench.dao;

import com.iurac.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {
    int save(TranHistory tranHistory);

    List<TranHistory> getTranHistoryListByTid(String tid);
}
