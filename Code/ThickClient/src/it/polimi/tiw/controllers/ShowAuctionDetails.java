package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerlet;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.dao.OfferDao;
import it.polimi.tiw.models.Auction;
import it.polimi.tiw.models.UserProfileData;
import it.polimi.tiw.models.Offer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowAuctionDetails", value = "/Details")
public class ShowAuctionDetails extends BasicServerlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String input = request.getParameter("auction");

        OfferDao   offerDao   = new OfferDao(this.getConnection());

        try {
            if(input == null ) throw new Exception("No auction specified");

            int auctionId = Integer.parseInt(input);
            List<Offer> offerts = offerDao.getOffertById(auctionId);

            this.sendJson(offerts,response);

        } catch (SQLException throwables) {
            throwables.printStackTrace();

            this.respondError("SQL error", response);
        } catch (Exception e) {
            e.printStackTrace();
            this.respondError(e.getMessage(), response);
        }

    }

}
