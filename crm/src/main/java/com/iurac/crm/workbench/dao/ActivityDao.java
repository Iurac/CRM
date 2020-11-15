package com.iurac.crm.workbench.dao;

import com.iurac.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.dao
 * Author: IuRac
 * CreateTime: 2020-11-05 18:59
 * Description:
 */
public interface ActivityDao {

    int save(Activity activity);

    List<Activity> getActivityByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity getDetail(String id);

    List<Activity> getActivityListByCid(String cid);

    List<Activity> getUnrelatedActivityListByName(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}
