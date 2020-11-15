package com.iurac.crm.workbench.web.controller;

import com.iurac.crm.settings.domain.User;
import com.iurac.crm.settings.service.UserService;
import com.iurac.crm.settings.service.impl.UserServiceImpl;
import com.iurac.crm.utils.DateTimeUtil;
import com.iurac.crm.utils.PrintJson;
import com.iurac.crm.utils.ServiceFactory;
import com.iurac.crm.utils.UUIDUtil;
import com.iurac.crm.vo.PaginationVo;
import com.iurac.crm.workbench.domain.Activity;
import com.iurac.crm.workbench.domain.Clue;
import com.iurac.crm.workbench.domain.ClueRemark;
import com.iurac.crm.workbench.domain.Tran;
import com.iurac.crm.workbench.service.ActivityService;
import com.iurac.crm.workbench.service.ClueService;
import com.iurac.crm.workbench.service.impl.ActivityServiceImpl;
import com.iurac.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.web.controller
 * Author: IuRac
 * CreateTime: 2020-11-12 13:33
 * Description:
 */
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入了Clue控制器");

        String path = request.getServletPath();

        if("/workbench/clue/getUserList.do".equals(path)){
            getUserList(request,response);

        }else if("/workbench/clue/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/clue/cluePageList.do".equals(path)){
            cluePageList(request,response);
        }else if("/workbench/clue/getUserListAndClue.do".equals(path)){
            getUserListAndClue(request,response);
        }else if("/workbench/clue/update.do".equals(path)){
            update(request,response);
        }else if("/workbench/clue/delete.do".equals(path)){
            delete(request,response);
        }else if("/workbench/clue/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/clue/getRemarkListByCid.do".equals(path)){
            getRemarkListByCid(request,response);
        }else if("/workbench/clue/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if("/workbench/clue/updateRemark.do".equals(path)){
            updateRemark(request,response);
        }else if("/workbench/clue/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if("/workbench/clue/getActivityListByCid.do".equals(path)){
            getActivityListByCid(request,response);
        }else if("/workbench/clue/unbund.do".equals(path)){
            unbund(request,response);
        }else if("/workbench/clue/bund.do".equals(path)){
            bund(request,response);
        }else if("/workbench/clue/getUnrelatedActivityListByName.do".equals(path)){
            getUnrelatedActivityListByName(request,response);
        }else if("/workbench/clue/getActivityListByName.do".equals(path)){
            getActivityListByName(request,response);
        }else if("/workbench/clue/convert.do".equals(path)){
            convert(request,response);
        }
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String cid = request.getParameter("cid");
        String isCreateTran = request.getParameter("flag");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Tran tran = null;
        if("t".equals(isCreateTran)){
            tran = new Tran();

            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String aid = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            tran.setId(id);
            tran.setActivityId(aid);
            tran.setCreateBy(createBy);
            tran.setCreateTime(createTime);
            tran.setStage(stage);
            tran.setName(name);
            tran.setMoney(money);
            tran.setExpectedDate(expectedDate);
        }

        boolean flag = clueService.convert(cid,tran,createBy);

        if(flag){
            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
        }
    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String aname = request.getParameter("aname");


        List<Activity> activityList = activityService.getActivityListByName(aname);

        PrintJson.printJsonObj(response,activityList);
    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");

        boolean flag = clueService.bund(cid,aids);

        PrintJson.printJsonFlag(response,flag);
    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");

        boolean flag = clueService.unbund(id);

        PrintJson.printJsonFlag(response,flag);
    }

    private void getUnrelatedActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String aname = request.getParameter("aname");
        String cid = request.getParameter("cid");

        Map<String,String> map = new HashMap<>();
        map.put("aname",aname);
        map.put("cid",cid);

        List<Activity> activityList = activityService.getUnrelatedActivityListByName(map);

        PrintJson.printJsonObj(response,activityList);
    }

    private void getActivityListByCid(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String cid = request.getParameter("cid");
        List<Activity> activityList = activityService.getActivityListByCid(cid);

        PrintJson.printJsonObj(response,activityList);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");

        boolean flag = clueService.deleteRemark(id);

        PrintJson.printJsonFlag(response,flag);
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        String editFlag = "1";
        ClueRemark clueRemark = new ClueRemark();
        clueRemark.setId(id);
        clueRemark.setNoteContent(noteContent);
        clueRemark.setEditBy(editBy);
        clueRemark.setEditFlag(editFlag);
        clueRemark.setEditTime(editTime);

        boolean flag = clueService.updateRemark(clueRemark);

        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("clueRemark",clueRemark);

        PrintJson.printJsonObj(response,map);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = UUIDUtil.getUUID();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String editFlag = "0";
        String cid = request.getParameter("cid");
        String noteContent = request.getParameter("noteContent");
        ClueRemark clueRemark = new ClueRemark();
        clueRemark.setId(id);
        clueRemark.setCreateBy(createBy);
        clueRemark.setCreateTime(createTime);
        clueRemark.setNoteContent(noteContent);
        clueRemark.setClueId(cid);
        clueRemark.setEditFlag(editFlag);

        boolean flag = clueService.saveRemark(clueRemark);

        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("clueRemark",clueRemark);

        PrintJson.printJsonObj(response,map);
    }

    private void getRemarkListByCid(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String cid = request.getParameter("cid");
        List<ClueRemark> clueRemarkList = clueService.getRemarkListByCid(cid);

        PrintJson.printJsonObj(response,clueRemarkList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");
        Clue clue = clueService.detail(id);

        request.setAttribute("clue",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String[] ids = request.getParameterValues("id");

        boolean flag = clueService.delete(ids);

        PrintJson.printJsonFlag(response,flag);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setEditBy(editBy);
        clue.setEditTime(editTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);

        boolean flag = clueService.update(clue);

        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndClue(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");

        Map<String, Object> map = clueService.getUserListAndClue(id);

        PrintJson.printJsonObj(response,map);
    }

    private void cluePageList(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String fullname = request.getParameter("fullname");
        String company = request.getParameter("company");
        String phone = request.getParameter("phone");
        String resource = request.getParameter("resource");
        String owner = request.getParameter("owner");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        int pageNo = Integer.parseInt(pageNoStr);
        int pageSize = Integer.parseInt(pageSizeStr);
        int skipCount = (pageNo-1)*pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone);
        map.put("resource",resource);
        map.put("owner",owner);
        map.put("mphone",mphone);
        map.put("state",state);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        PaginationVo<Clue> cluePaginationVo = clueService.cluePageList(map);

        PrintJson.printJsonObj(response,cluePaginationVo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);

        boolean flag = clueService.save(clue);

        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList = userService.getUserList();

        PrintJson.printJsonObj(response,userList);
    }
}
