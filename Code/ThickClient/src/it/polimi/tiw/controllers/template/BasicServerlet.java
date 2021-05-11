package it.polimi.tiw.controllers.template;

import it.polimi.tiw.managment.JsonSerializable;
import it.polimi.tiw.managment.dbConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Generate a template engine and a SQL connection
 */
@MultipartConfig
public class BasicServerlet extends HttpServlet {

    private Connection connection;

    /**
     * Initialize Thymeleaf template Engine and DB connection
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();

        //Generate Context
        ServletContext context = getServletContext();
        //Try to connect to database and in case of error forward error Page
        try {
            this.connection = dbConnection.getConnection(context);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    public void respondError(String error, HttpServletResponse response)
    {
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(error);
        } catch (IOException e) {
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

    /**
     *
     * @return Getter to get db connection
     */
    public Connection getConnection() {
        return this.connection;
    }

    public PrintWriter setJsonResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        return response.getWriter();
    }

    public<T> void sendJson(List<T> list,HttpServletResponse response)
    {
        try {
            PrintWriter printer = this.setJsonResponse(response);
            String out = JsonSerializable.listSerializartion((list));
            printer.println(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendJson(JsonSerializable obj,HttpServletResponse response)
    {
        try {
            PrintWriter printer = this.setJsonResponse(response);
            String out = obj.toJson();
            printer.println(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
