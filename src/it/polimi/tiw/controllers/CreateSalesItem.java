package it.polimi.tiw.controllers;

import it.polimi.tiw.dao.SalesItemDao;
import it.polimi.tiw.managment.dbConnection;
import it.polimi.tiw.models.SalesItem;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@WebServlet(name = "CreateSalesItem", value = "/CreateSalesItem")
public class CreateSalesItem  extends HttpServlet {

    Connection connection;
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();

        try {
            this.connection = dbConnection.getConnection(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(context);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();

        String filesPath = context.getContextPath() +"/UploadFolder";
        SalesItemDao dao = new SalesItemDao(this.connection);

        List<SalesItem> list =new ArrayList<>();
        try {
             list = dao.getItemById();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        // Redirect to the Home page and add missions to the parameters
        String path = "/WEB-INF/TemplateHTML/auctions";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("item", list);
        templateEngine.process(path, ctx, response.getWriter());

        /*response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<HTML><BODY>");
        out.println("<HEAD><TITLE>Show image</TITLE></HEAD>");

        for(SalesItem item:list)
        {
            out.println("<img src=\""+ filesPath + item.getPathImage() +" \"/>");
        }
        out.println("<P>" + "Image uploaded correctly!" + "</P>");

        out.println("<img src=\"" + filesPath + "nicola/deathnote.png " + " \"/>");

        out.println("</HTML></BODY>");
        out.close();*/
    }
}

