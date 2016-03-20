/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import beans.LoginBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebFilter(filterName = "adminRoleFilter")
public class AdminRoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest re = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = re.getSession(false);
        
        //I think I get null pointer exception otherwise if the session has timed out.
        if(session == null){
            res.sendRedirect(re.getContextPath() + "/login.xhtml");
            return;
        }
        
        LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");
        if(loginBean.getRole().equals("teacher") || loginBean.getRole().equals("student")){
            res.sendRedirect(re.getContextPath() + "/errorViews/navigationError.xhtml");
        }
        else{
            chain.doFilter(re, res);
        }
    }

    @Override
    public void destroy() {
    }
    
}
