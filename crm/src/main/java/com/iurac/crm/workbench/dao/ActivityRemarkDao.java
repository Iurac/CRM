package com.iurac.crm.workbench.dao;

import com.iurac.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.dao
 * Author: IuRac
 * CreateTime: 2020-11-09 09:38
 * Description:
 */
public interface ActivityRemarkDao {
    int getByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String aid);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
