package com.iurac.crm.workbench.service.impl;

import com.iurac.crm.settings.dao.UserDao;
import com.iurac.crm.settings.domain.User;
import com.iurac.crm.utils.SqlSessionUtil;
import com.iurac.crm.vo.PaginationVo;
import com.iurac.crm.workbench.dao.ActivityDao;
import com.iurac.crm.workbench.dao.ActivityRemarkDao;
import com.iurac.crm.workbench.domain.Activity;
import com.iurac.crm.workbench.domain.ActivityRemark;
import com.iurac.crm.workbench.service.ActivityService;
import jdk.nashorn.internal.ir.Flags;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.service.impl
 * Author: IuRac
 * CreateTime: 2020-11-05 19:01
 * Description:
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public Boolean save(Activity activity) {
        int result = activityDao.save(activity);

        if(result == 1){
            return true;
        }

        return false;
    }

    @Override
    public PaginationVo<Activity> activityPageList(Map<String, Object> map) {
        PaginationVo<Activity> activityPaginationVo = new PaginationVo<>();

        int total = activityDao.getTotalByCondition(map);

        List<Activity> activityList = activityDao.getActivityByCondition(map);

        activityPaginationVo.setTotal(total);
        activityPaginationVo.setDataList(activityList);

        return activityPaginationVo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;

        int remarkCount = activityRemarkDao.getByAids(ids);
        int deleteRemarkCount = activityRemarkDao.deleteByAids(ids);
        if(remarkCount != deleteRemarkCount){
            flag = false;
        }

        int deleteCount = activityDao.delete(ids);
        if(deleteCount != ids.length){
            flag = false;
        }

        return flag;

    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> uList = userDao.getUserList();
        Activity a = activityDao.getById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);

        return map;
    }

    @Override
    public boolean update(Activity activity) {

        int result = activityDao.update(activity);

        if(result == 1){
            return true;
        }
        return false;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.getDetail(id);

        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String aid) {
        List<ActivityRemark> activityRemarkList = activityRemarkDao.getRemarkListByAid(aid);

        return activityRemarkList;
    }

    @Override
    public boolean deleteRemark(String id) {
        int result = activityRemarkDao.deleteRemark(id);

        if(result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        int result = activityRemarkDao.saveRemark(activityRemark);

        if(result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        int result = activityRemarkDao.updateRemark(activityRemark);

        if(result == 1){
            return true;
        }
        return false;
    }

    @Override
    public List<Activity> getActivityListByCid(String cid) {
        List<Activity> activityList = activityDao.getActivityListByCid(cid);

        return activityList;
    }

    @Override
    public List<Activity> getUnrelatedActivityListByName(Map<String, String> map) {
        List<Activity> activityList = activityDao.getUnrelatedActivityListByName(map);

        return activityList;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> activityList = activityDao.getActivityListByName(aname);

        return activityList;    }
}
