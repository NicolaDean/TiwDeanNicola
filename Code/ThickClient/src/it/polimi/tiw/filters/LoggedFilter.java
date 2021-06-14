package it.polimi.tiw.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoggedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest)  servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession         session  = request.getSession(false);

        if(session != null)
        {
            Object user = session.getAttribute("currentUser");

            //If session contain a user mean user is authorized
            if(user!=null)
            {
                System.out.println("Logged user action");
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }
        System.out.println("Redirect not logged user to login page");
        //filterChain.doFilter(servletRequest,servletResponse);
        response.sendRedirect(request.getServletContext().getContextPath() + "/index.html");
    }

    @Override
    public void destroy() {

    }
}
