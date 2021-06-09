package it.polimi.tiw.filters;

import it.polimi.tiw.controllers.template.BasicServerletThymeleaf;
import it.polimi.tiw.managment.TemplatePaths;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NotLoggedFilter extends BasicFilter {


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
                this.renderer(request,response,TemplatePaths.homePage);
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
