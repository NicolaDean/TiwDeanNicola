package it.polimi.tiw.filters;

import it.polimi.tiw.controllers.template.BasicServerletThymeleaf;
import it.polimi.tiw.managment.TemplatePaths;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoggedFilter extends BasicFilter {

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
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }

        //Redirect to login page with error message
        request.setAttribute("errorMsg","You cant stay here, please login first");
        this.renderer(request,response,TemplatePaths.loginPage);
    }

    @Override
    public void destroy() {

    }
}
