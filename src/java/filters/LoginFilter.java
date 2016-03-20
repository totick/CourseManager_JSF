package filters;

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

import beans.LoginBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@WebFilter(filterName = "loginFilter")
public class LoginFilter implements Filter {

    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest re = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = re.getSession(false);
        
        if(session == null){
            res.sendRedirect(re.getContextPath() + "/login.xhtml");
            return;
        }
        
        LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");

        if (loginBean != null && loginBean.getLoggedInPerson() != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(re.getContextPath() + "/login.xhtml");
        }

    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
