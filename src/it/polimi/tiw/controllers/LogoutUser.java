package it.polimi.tiw.controllers;

import it.polimi.tiw.managment.TemplatePaths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Logout", value = "/Logout")
public class LogoutUser extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("currentUser");

        response.sendRedirect(getServletContext().getContextPath() + "/");
        return;
    }
}