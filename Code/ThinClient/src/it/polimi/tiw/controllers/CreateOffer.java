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

@WebServlet(name = "CreateOffer", value = "/NewOffer")
public class CreateOffer extends BasicServerletThymeleafSQL {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String in1 = request.getParameter("auctionid");
        String in2 = request.getParameter("offer");

        OfferDao offerDao = new OfferDao(this.getConnection());
        AuctionDao dao = new AuctionDao(this.getConnection());



        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentUser");
        System.out.println(in1);
        int auctionid = Integer.parseInt(in1);
        int offer =Integer.parseInt(in2);

        Auction auction = null;
        List<Offer> offerts = null;

        try {
            System.out.println("DOING OFFERT");
            //Retrive auction data
            auction   = dao.getAuctionById(auctionid);
            if(auction == null ) throw new CustomExeption("This acution does not exist");
            request.setAttribute("auction",auction);

            //Insert offer
            offerDao.insertOffer(u.getId(), auctionid, offer);

            System.out.println("OFFERT INSERTED");
            //Retrive updated offerts
            offerts   = offerDao.getOffertById(auctionid);
            request.setAttribute("offerts",offerts);

            //Send back user to auction detail
            this.templateRenderer(request,response, TemplatePaths.auctionDetails);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (CustomExeption customExeption) {
            //Generate message to send to the user
            System.out.println("CUSTOM EXEPTION -> " + customExeption.SendError());

            //Retrive updated offerts
            offerts   = offerDao.getOffertById(auctionid);
            request.setAttribute("offerts",offerts);

            this.setError(request,response,customExeption.SendError(),TemplatePaths.auctionDetails);
        }
    }
}
