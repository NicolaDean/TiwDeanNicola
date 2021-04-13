package it.polimi.tiw.controllers;

import it.polimi.tiw.dao.SalesItemDao;
import it.polimi.tiw.models.SalesItem;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.context.WebContext;

@WebServlet(name = "CreateSalesItem", value = "/CreateSalesItem")
public class CreateSalesItem  extends BasicServerletThymeleafSQL {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();

        String filesPath = context.getContextPath() +"/UploadFolder";
        SalesItemDao dao = new SalesItemDao(this.getConnection());

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
        this.getTemplateEngine().process(path, ctx, response.getWriter());

    }
}

