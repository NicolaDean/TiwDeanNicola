package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerlet;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.dao.OfferDao;
import it.polimi.tiw.exceptions.CustomExeption;
import it.polimi.tiw.managment.TemplatePaths;
import it.polimi.tiw.models.Auction;
import it.polimi.tiw.models.Offer;
import it.polimi.tiw.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Close", value = "/Close")
public class CloseAuction extends BasicServerlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        AuctionDao auctionDao = new AuctionDao(this.getConnection());
        OfferDao offerDao     = new OfferDao(this.getConnection());
        Auction        auction = null;
        List<Offer>    offert  = null;

        try
        {
            int id = Integer.parseInt(request.getParameter("auction"));
            auction = auctionDao.getAuctionById(id);
            offert  = offerDao.getOffertById(id);

            auction.setOfferts(offert);

            HttpSession session = request.getSession();
            User u = (User)session.getAttribute("currentUser");

            if(u.getId() != auction.getUserId()) throw new CustomExeption("You cant close others auction");
            if(!auction.isClosable()) throw new CustomExeption("Non closable auction");

            auctionDao.setAuctionClosed(id);
            System.out.println("Auction + " + id + " closed");
            this.sendJson(auction,response);
            return;
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            this.respondError("Sql Error",response);
        } catch (CustomExeption customExeption) {
            customExeption.printStackTrace();

            customExeption.SendError(response);
            return;
        }
    }

}
