package com.iurac.crm.workbench.web.controller;

import com.iurac.crm.settings.domain.User;
import com.iurac.crm.settings.service.UserService;
import com.iurac.crm.settings.service.impl.UserServiceImpl;
import com.iurac.crm.utils.*;
import com.iurac.crm.vo.PaginationVo;
import com.iurac.crm.workbench.domain.Activity;
import com.iurac.crm.workbench.domain.ActivityRemark;
import com.iurac.crm.workbench.service.ActivityService;
import com.iurac.crm.workbench.service.impl.ActivityServiceImpl;
import com.mysql.cj.Session;
import org.omg.CORBA.ARG_IN;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.Detail;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.activation.ActivationID;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.web.controller
 * Author: IuRac
 * CreateTime: 2020-11-05 19:01
 * Description:
 */
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入了Activity控制器！");

        String path = request.getServletPath();

        if("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/activity/save.do".equals(path)) {
           save(request,response);
        }else if("/workbench/activity/activityPageList.do".equals(path)) {
           activityPageList(request,response);
        }else if("/workbench/activity/delete.do".equals(path)) {
            delete(request,response);
        }else if("/workbench/activity/getUserListAndActivity.do".equals(path)) {
            getUserListAndActivity(request,response);
        }else if("/workbench/activity/update.do".equals(path)) {
            update(request,response);
        }else if("/workbench/activity/detail.do".equals(path)) {
            detail(request,response);
        }else if("/workbench/activity/getRemarkListByAid.do".equals(path)) {
            getRemarkListByAid(request,response);
        }else if("/workbench/activity/deleteRemark.do".equals(path)) {
            deleteRemark(request,response);
        }else if("/workbench/activity/saveRemark.do".equals(path)) {
            saveRemark(request,response);
        }else if("/workbench/activity/updateRemark.do".equals(path)) {
            updateRemark(request,response);
        }
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");

        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditFlag("1");
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        activityRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());

        boolean flag = activityService.updateRemark(activityRemark);

        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("activityRemark",activityRemark);

        PrintJson.printJsonObj(response,map);

    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");

        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setNoteContent(noteContent);
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        activityRemark.setEditFlag("0");
        activityRemark.setActivityId(activityId);

        boolean flag = activityService.saveRemark(activityRemark);

        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("activityRemark",activityRemark);

        PrintJson.printJsonObj(response,map);

    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");

        boolean flag= activityService.deleteRemark(id);

        PrintJson.printJsonFlag(response,flag);

    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String aid = request.getParameter("aid");
        List<ActivityRemark> activityRemarkList = activityService.getRemarkListByAid(aid);

        PrintJson.printJsonObj(response,activityRemarkList);


    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");
        Activity activity = activityService.detail(id);

        request.setAttribute("activity",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity activity = new Activity();
        activity.setId(request.getParameter("id"));
        activity.setOwner(request.getParameter("owner"));
        activity.setName(request.getParameter("name"));
        activity.setStartDate(request.getParameter("startDate"));
        activity.setEndDate(request.getParameter("endDate"));
        activity.setCost(request.getParameter("cost"));
        activity.setDescription(request.getParameter("description"));
        activity.setEditTime(DateTimeUtil.getSysTime());
        activity.setEditBy(((User)request.getSession().getAttribute("user")).getName());

        boolean result = activityService.update(activity);

        PrintJson.printJsonFlag(response,result);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");

        Map<String , Object> map = activityService.getUserListAndActivity(id);

        PrintJson.printJsonObj(response,map);

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String[] ids = request.getParameterValues("id");

        boolean flag = activityService.delete(ids);

        PrintJson.printJsonFlag(response,flag);
    }

    private void activityPageList(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int pageNo = Integer.parseInt(pageNoStr);
        int pageSize = Integer.parseInt(pageSizeStr);
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        PaginationVo<Activity> activityPaginationVo = activityService.activityPageList(map);

        PrintJson.printJsonObj(response,activityPaginationVo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity activity = new Activity();
        activity.setId(UUIDUtil.getUUID());
        activity.setOwner(request.getParameter("owner"));
        activity.setName(request.getParameter("name"));
        activity.setStartDate(request.getParameter("startDate"));
        activity.setEndDate(request.getParameter("endDate"));
        activity.setCost(request.getParameter("cost"));
        activity.setDescription(request.getParameter("description"));
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());

        Boolean result = activityService.save(activity);

        PrintJson.printJsonFlag(response,result);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList = userService.getUserList();

        PrintJson.printJsonObj(response,userList);
    }
}
