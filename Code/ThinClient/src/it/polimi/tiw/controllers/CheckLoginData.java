package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerletThymeleafSQL;
import it.polimi.tiw.dao.UserDao;
import it.polimi.tiw.managment.TemplatePaths;
import it.polimi.tiw.models.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Login", value = "/Login")
public class CheckLoginData extends BasicServerletThymeleafSQL {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String psw   = request.getParameter("password");


        System.out.println("Input: " + email + " , "+ psw);



        UserDao dao = new UserDao(this.getConnection());
        User usr = null;
        try {
            usr = dao.checkUserLogin(email,psw);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(usr == null)
        {
            this.setError(request,response,"email or password are wrong, try again :)", TemplatePaths.loginPage);
            System.out.println("Errore");
            return;
        }
        else
            {

                HttpSession session = request.getSession();
                session.setAttribute("currentUser", usr);

                response.sendRedirect(getServletContext().getContextPath() + "/home");
            }
    }
}
