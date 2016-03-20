/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import beans.LoginBean;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
 * @author John
 */
@WebFilter(filterName = "teacherRoleFilter")
public class TeacherRoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest re = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = re.getSession(false);
        LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");
        if (loginBean.getRole().equals("student")) {
            res.sendRedirect(re.getContextPath() + "/errorViews/navigationError.xhtml");
        } else {
            chain.doFilter(re, res);
        }
    }

    @Override
    public void destroy() {
    }

}
