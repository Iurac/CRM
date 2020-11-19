package com.iurac.crm.workbench.service;

import com.iurac.crm.vo.PaginationVo;
import com.iurac.crm.workbench.domain.Tran;
import com.iurac.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.service
 * Author: IuRac
 * CreateTime: 2020-11-16 13:13
 * Description:
 */
public interface TranService {
    PaginationVo<Tran> transactionPageList(Map<String, Object> map);

    boolean save(Tran tran, String customerName);

    Tran detail(String id);

    List<TranHistory> getTranHistoryListByTid(String tid);

    boolean changeStage(Tran tran);
}
