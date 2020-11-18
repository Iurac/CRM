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
import com.iurac.crm.workbench.domain.Contacts;
import com.iurac.crm.workbench.domain.Tran;
import com.iurac.crm.workbench.service.ActivityService;
import com.iurac.crm.workbench.service.ContactsService;
import com.iurac.crm.workbench.service.CustomerService;
import com.iurac.crm.workbench.service.TranService;
import com.iurac.crm.workbench.service.impl.ActivityServiceImpl;
import com.iurac.crm.workbench.service.impl.ContactsServiceImpl;
import com.iurac.crm.workbench.service.impl.CustomerServiceImpl;
import com.iurac.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.web.controller
 * Author: IuRac
 * CreateTime: 2020-11-16 13:14
 * Description:
 */
public class TranController  extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入了Tran控制器");

        String path = request.getServletPath();

        if("/workbench/transaction/transactionPageList.do".equals(path)){
            transactionPageList(request,response);
        }else if("/workbench/transaction/getActivityListByName.do".equals(path)){
            getActivityListByName(request,response);
        }else if("/workbench/transaction/getContactsListByName.do".equals(path)){
            getContactsListByName(request,response);
        }else if("/workbench/transaction/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(request,response);
        }else if("/workbench/transaction/save.do".equals(path)){
            save(request,response);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        String customerName = request.getParameter("create-customerName");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("create-owner");
        String money  = request.getParameter("create-money");
        String name = request.getParameter("create-name");
        String expectedDate = request.getParameter("create-expectedDate");
        String stage = request.getParameter("create-stage");
        String type = request.getParameter("create-type");
        String source = request.getParameter("create-source");
        String activityId = request.getParameter("create-activityId");
        String contactsId = request.getParameter("create-contactsId");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("create-description");
        String contactSummary = request.getParameter("create-contactSummary");
        String nextContactTime = request.getParameter("create-nextContactTime");
        Tran tran = new Tran();
        tran.setId(id);
        tran.setMoney(money);
        tran.setName(name);
        tran.setStage(stage);
        tran.setSource(source);
        tran.setExpectedDate(expectedDate);
        tran.setCreateTime(createTime);
        tran.setCreateBy(createBy);
        tran.setActivityId(activityId);
        tran.setContactsId(contactsId);
        tran.setContactSummary(contactSummary);
        tran.setDescription(description);
        tran.setType(type);
        tran.setOwner(owner);
        tran.setNextContactTime(nextContactTime);

        boolean flag = tranService.save(tran,customerName);

        if(flag) {
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }
    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        String name = request.getParameter("name");

        List<String> customerList = customerService.getCustomerName(name);

        PrintJson.printJsonObj(response,customerList);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList = userService.getUserList();

        PrintJson.printJsonObj(response,userList);
    }

    private void getContactsListByName(HttpServletRequest request, HttpServletResponse response) {
        ContactsService contactsService = (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());

        String cname = request.getParameter("cname");

        List<Contacts> contactsList = contactsService.getContactsListByName(cname);

        PrintJson.printJsonObj(response,contactsList);
    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String aname = request.getParameter("aname");

        List<Activity> activityList = activityService.getActivityListByName(aname);

        PrintJson.printJsonObj(response,activityList);
    }

    private void transactionPageList(HttpServletRequest request, HttpServletResponse response) {
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String stage = request.getParameter("stage");
        String source = request.getParameter("source");
        String type = request.getParameter("type");
        String contactsName = request.getParameter("contactsName");
        String customerName = request.getParameter("customerName");
        int pageNo = Integer.parseInt(pageNoStr);
        int pageSize = Integer.parseInt(pageSizeStr);
        int skipCount = (pageNo-1)*pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("stage",stage);
        map.put("source",source);
        map.put("type",type);
        map.put("contactsName",contactsName);
        map.put("customerName",customerName);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        PaginationVo<Tran> tranPaginationVo = tranService.transactionPageList(map);

        PrintJson.printJsonObj(response,tranPaginationVo);
    }
}
