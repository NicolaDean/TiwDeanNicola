package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerlet;
import it.polimi.tiw.dao.UserDao;
import it.polimi.tiw.models.User;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "Login", value = "/Login")
@MultipartConfig
public class CheckLoginData extends BasicServerlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = StringEscapeUtils.escapeJava(request.getParameter("email"));
        String psw   = StringEscapeUtils.escapeJava(request.getParameter("password"));


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
            this.respondError("Username or password are wrong, try again",response);
            System.out.println("Errore");
        }
        else
        {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", usr);
            this.sendJson(usr,response);
        }
    }
}
