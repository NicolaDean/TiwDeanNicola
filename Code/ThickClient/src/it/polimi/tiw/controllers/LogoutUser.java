package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Logout", value = "/Logout")
public class LogoutUser extends BasicServerlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        System.out.println("Logout");
        response.sendRedirect(getServletContext().getContextPath() + "/home.html");
        return;
    }
}
