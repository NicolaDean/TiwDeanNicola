package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerletThymeleafSQL;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.managment.TemplatePaths;
import it.polimi.tiw.models.User;
import it.polimi.tiw.models.UserProfileData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SellerPage", value = "/SellerPage")
public class GoToSellerPage extends BasicServerletThymeleafSQL {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AuctionDao dao = new AuctionDao(this.getConnection());

        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("currentUser");

        try {
            UserProfileData data = dao.getUserProfileData(user.getId());

            request.setAttribute("auctions",data);
            this.templateRenderer(request,response, TemplatePaths.sellerPage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return;
    }
}
