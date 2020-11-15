package com.iurac.crm.workbench.service;

import com.iurac.crm.vo.PaginationVo;
import com.iurac.crm.workbench.domain.Activity;
import com.iurac.crm.workbench.domain.Clue;
import com.iurac.crm.workbench.domain.ClueRemark;
import com.iurac.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.service
 * Author: IuRac
 * CreateTime: 2020-11-12 13:31
 * Description:
 */
public interface ClueService {
    boolean save(Clue clue);

    PaginationVo<Clue> cluePageList(Map<String, Object> map);

    Map<String, Object> getUserListAndClue(String id);

    boolean update(Clue clue);

    boolean delete(String[] ids);

    Clue detail(String id);

    List<ClueRemark> getRemarkListByCid(String cid);

    boolean saveRemark(ClueRemark clueRemark);

    boolean updateRemark(ClueRemark clueRemark);

    boolean deleteRemark(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String cid, Tran tran, String createBy);
}
