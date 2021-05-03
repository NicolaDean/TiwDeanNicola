package it.polimi.tiw.controllers.template;

import it.polimi.tiw.managment.dbConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Generate a template engine and a SQL connection
 */
public class BasicServerletThymeleafSQL extends BasicServerletThymeleaf {

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

}
