package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerletThymeleaf;
import it.polimi.tiw.controllers.template.BasicServerletThymeleafSQL;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.managment.TemplatePaths;
import it.polimi.tiw.models.Auction;
import it.polimi.tiw.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Home", value = "/home")
public class GoToHomePage extends BasicServerletThymeleafSQL {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AuctionDao dao = new AuctionDao(this.getConnection());

        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("currentUser");

        try {
            List<Auction> winnedAuction = dao.getWinnedAuctions(user.getId());
            request.setAttribute("winned",winnedAuction);

            this.templateRenderer(request,response, TemplatePaths.homePage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return;
    }

}
