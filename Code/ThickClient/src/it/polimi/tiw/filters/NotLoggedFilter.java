package it.polimi.tiw.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NotLoggedFilter implements Filter {


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
                System.out.println("Redirect to home");
                response.sendRedirect(request.getServletContext().getContextPath() + "/home.html");
                return;
            }
        }

        //Redirect to login page with error message
        filterChain.doFilter(servletRequest,servletResponse);


    }

    @Override
    public void destroy() {

    }
}
