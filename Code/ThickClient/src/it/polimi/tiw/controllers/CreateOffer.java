package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerlet;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.dao.OfferDao;
import it.polimi.tiw.exceptions.CustomExeption;
import it.polimi.tiw.models.Auction;
import it.polimi.tiw.models.User;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@MultipartConfig
@WebServlet(name = "CreateOffer", value = "/NewOffer")
public class CreateOffer extends BasicServerlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String in1         = StringEscapeUtils.escapeJava(request.getParameter("auctionid"));
        String in2         = StringEscapeUtils.escapeJava(request.getParameter("offer"));

        OfferDao   offerDao   = new OfferDao(this.getConnection());
        AuctionDao auctionDao = new AuctionDao(this.getConnection());

        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentUser");
        System.out.println(in1);
        int auctionid = Integer.parseInt(in1);
        int offer =Integer.parseInt(in2);

        try {
            offerDao.insertOffer(u.getId(), auctionid, offer);

            Auction auction = auctionDao.getAuctionById(auctionid);
            auction.setOfferts(offerDao.getOffertById(auctionid));
            this.sendJson(auction,response);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            this.respondError("SQL error",response);
        } catch (CustomExeption customExeption) {
            //Generate message to send to the user
            customExeption.printStackTrace();
            customExeption.SendError(response);
        }
    }
}
