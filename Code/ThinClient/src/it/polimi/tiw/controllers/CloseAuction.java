package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerletThymeleafSQL;
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
public class CloseAuction extends BasicServerletThymeleafSQL {

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

            request.setAttribute("auction",auction);
            request.setAttribute("offerts",offert);

            HttpSession session = request.getSession();
            User u = (User)session.getAttribute("currentUser");

            if(u.getId() != auction.getUserId())
            {
                throw new CustomExeption("You cant close others auction");
            }
            if(!auction.isClosable()) throw new CustomExeption("Non closable auction");

            System.out.println(u.getId() +"->" + auction.getUserId());
            System.out.println("lol");

            auctionDao.setAuctionClosed(id);

            auction.setClosed();
            System.out.println("Auction + " + id + " closed");
            this.templateRenderer(request,response, TemplatePaths.auctionDetails);
            return;
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            this.setError(request,response,"SQL error", TemplatePaths.auctionDetails);
        } catch (CustomExeption customExeption) {
            customExeption.printStackTrace();

            this.setError(request,response,customExeption.SendError(), TemplatePaths.auctionDetails);
            return;
        }



    }

}
