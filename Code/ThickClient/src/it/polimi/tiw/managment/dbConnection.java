package it.polimi.tiw.managment;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {


    public static Connection getConnection(ServletContext context) throws Exception {
        Connection connection = null;
        try
        {
            String driver = context.getInitParameter("dbDriver");
            String url = context.getInitParameter("dbURL");
            String user = context.getInitParameter("dbUser");
            String password = context.getInitParameter("dbPassword");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException  e)
        {
            System.out.println("Errore sql");
            throw  new Exception(e.getMessage());
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Errore Driver");
        }

        return connection;
    }


}
