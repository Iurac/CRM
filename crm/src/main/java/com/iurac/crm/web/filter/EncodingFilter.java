package com.iurac.crm.web.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.settings.web.filter
 * Author: IuRac
 * CreateTime: 2020-11-05 11:59
 * Description:
 */
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        chain.doFilter(request,response);
    }
}
