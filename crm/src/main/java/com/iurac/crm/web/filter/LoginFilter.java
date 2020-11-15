package com.iurac.crm.web.filter;

import com.iurac.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.web.filter
 * Author: IuRac
 * CreateTime: 2020-11-05 14:11
 * Description:
 */
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String path = request.getServletPath();

        if("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            chain.doFilter(req,resp);
        }else {
            if(user != null){
                chain.doFilter(req,resp);
            }else {
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }
    }
}
