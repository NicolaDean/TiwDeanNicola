package it.polimi.tiw.controllers;

import it.polimi.tiw.dao.UserDao;
import it.polimi.tiw.managment.dbConnection;
import it.polimi.tiw.models.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends BasicServerletThymeleadSQL {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String psw   = request.getParameter("password");
        PrintWriter out = response.getWriter();

        System.out.println("Input: " + email + " , "+ psw);

        out.println("<title>Nicomane Aste</title>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">\n" +
                "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>\n" +
                "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\"></script>\n" +
                "</head>");

        UserDao dao = new UserDao(this.getConnection());
        User usr = null;
        try {
            usr = dao.checkUserLogin(email,psw);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(usr == null)
        {
            out.println("<div class=\"alert alert-warning\">\n" +
                        "  <strong>Login Fallito, Password o userneme scorretto.\n" +
                        "</div>");System.out.println("Login fallito");
        }
        else
            {
                out.println("<div class=\"alert alert-success\">\n" +
                        "  <strong> Welcome! "+ usr.getName()+ "</strong> \n"+
                        "</div>");
                System.out.println("Login fallito");
            }


        out.close();


    }
}
