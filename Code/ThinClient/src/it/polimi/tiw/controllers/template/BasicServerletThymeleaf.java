package it.polimi.tiw.controllers.template;

import it.polimi.tiw.managment.TemplateEngineGenerator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Generate a thymeleaf template engine
 */
public class BasicServerletThymeleaf extends HttpServlet {

    private TemplateEngine templateEngine;

    /**
     * Initialize Thymeleaf template Engine and DB connection
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();

        //Generate Context
        ServletContext context = getServletContext();
        //Generate Thymeleaf template engine
        this.templateEngine = TemplateEngineGenerator.getTemplateEngine(context,".html");
    }


    /**
     * Call doPost by default (if not overrided)
     * @param request   http request
     * @param response  http response
     * @throws ServletException exception of serverlet
     * @throws IOException input output exception
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     *
     * @return the thymeleaf initialized template engine
     */
    public TemplateEngine getTemplateEngine()
    {
        return this.templateEngine;
    }

    /**
     * Generate an error on a "standard" error template $errorMsg
     * @param request
     * @param response
     * @param error
     */
    public void setError(HttpServletRequest request, HttpServletResponse response,String error,String path)
    {
        request.setAttribute("errorMsg",error);
        try {
            templateRenderer(request,response,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given the path of a template it generate it
     * @param request http request
     * @param response http response
     * @param path template path
     * @throws IOException template not exist
     */
    public void templateRenderer(HttpServletRequest request, HttpServletResponse response,String path) throws IOException {
        ServletContext context = getServletContext();

        WebContext ctx = new WebContext(request, response, context, request.getLocale());
        this.templateEngine.process(path,ctx,response.getWriter());
    }

}

