package com.iurac.crm.settings.web.controller;

import com.iurac.crm.settings.domain.User;
import com.iurac.crm.settings.service.UserService;
import com.iurac.crm.settings.service.impl.UserServiceImpl;
import com.iurac.crm.utils.MD5Util;
import com.iurac.crm.utils.PrintJson;
import com.iurac.crm.utils.ServiceFactory;
import com.iurac.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.settings.web.controller
 * Author: IuRac
 * CreateTime: 2020-11-04 22:00
 * Description:
 */
public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入了User控制器！");

        String path = request.getServletPath();

        if("/settings/user/login.do".equals(path)){
            login(request,response);
        }else if("/settings/user/updatePwd.do".equals(path)) {
            updatePwd(request,response);
        }
    }

    private void updatePwd(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        boolean flag = false;

        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        newPwd = MD5Util.getMD5(newPwd);
        String id = ((User)request.getSession().getAttribute("user")).getId();
        String loginPwd = ((User)request.getSession().getAttribute("user")).getLoginPwd();

        if(loginPwd.equals(MD5Util.getMD5(oldPwd))){
            flag = userService.updatePwd(id,newPwd);
        }

        PrintJson.printJsonFlag(response,flag);

    }

    private void login(HttpServletRequest request, HttpServletResponse response){

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            User user = userService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user", user);

            PrintJson.printJsonFlag(response,true);

        } catch (Exception e) {

            e.printStackTrace();
            String msg = e.getMessage();

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);

            PrintJson.printJsonObj(response,map);

        }

    }
}
