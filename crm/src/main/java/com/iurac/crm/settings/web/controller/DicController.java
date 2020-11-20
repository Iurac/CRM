package com.iurac.crm.settings.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: ${PACKAGE_NAME}
 * Author: IuRac
 * CreateTime: 2020-11-12 13:27
 * Description: ${Description}
 */

public class DicController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入了Dic控制器！");

        String path = request.getServletPath();

        if("/settings/dic/xx.do".equals(path)){

        }
    }
}
