package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerlet;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.models.Auction;
import it.polimi.tiw.models.UserProfileData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetUserProfileData", value = "/UserProfile")
public class GetUserProfileData extends BasicServerlet {
    //TODO CHECK LOGIN BEFORE SEND DATA (per non inviare dati personali ad utenti sconosciuti)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int input = Integer.parseInt(request.getParameter("userid"));

        AuctionDao auctionDao = new AuctionDao(this.getConnection());


        List<Auction> open   = new ArrayList<>();
        List<Auction> closed = new ArrayList<>();
        List<Auction> winned = new ArrayList<>();
        try {
            open   = auctionDao.getOpenAuctions(input);
            closed = auctionDao.getClosedAuctions(input);
            winned = auctionDao.getWinnedAuctions(input);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        UserProfileData data = new UserProfileData(open,closed,winned);

        if(!(open.isEmpty() && closed.isEmpty()))
        {
            this.sendJson(data,response);
        }
        else
        {
            this.respondError("you dont own auctions click on create button to create your First :)", response);
        }

    }
}
