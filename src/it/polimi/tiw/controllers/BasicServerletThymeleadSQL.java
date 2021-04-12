package it.polimi.tiw.controllers;

import it.polimi.tiw.managment.TemplateEngineGenerator;
import it.polimi.tiw.managment.dbConnection;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Class that extend httpServerlet to avoid recoding same init() code in all controllers
 */
public class BasicServerletThymeleadSQL extends HttpServlet {

    private Connection connection;
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
        TemplateEngineGenerator.getTemplateEngine(context,".html");

        //Try to connect to database and in case of error forward error Page
        try {
            this.connection = dbConnection.getConnection(context);
        } catch (Exception e) {
            e.printStackTrace();
        }


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
     * @return Getter to get db connection
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     *
     * @return the thymeleaf initialized template engine
     */
    public TemplateEngine getTemplateEngine()
    {
        return this.templateEngine;
    }
}
