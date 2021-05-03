package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerletThymeleaf;
import it.polimi.tiw.managment.TemplatePaths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Create", value = "/Create")
public class GoToCreationPage extends BasicServerletThymeleaf {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.templateRenderer(request,response, TemplatePaths.auctionCreate);
        return;
    }
}
