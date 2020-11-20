package com.iurac.crm.workbench.service;

import com.iurac.crm.vo.PaginationVo;
import com.iurac.crm.workbench.domain.Activity;
import com.iurac.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.service
 * Author: IuRac
 * CreateTime: 2020-11-05 19:00
 * Description:
 */
public interface ActivityService {
    Boolean save(Activity activity);

    PaginationVo<Activity> activityPageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String aid);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);

    List<Activity> getActivityListByCid(String cid);

    List<Activity> getUnrelatedActivityListByName(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}
