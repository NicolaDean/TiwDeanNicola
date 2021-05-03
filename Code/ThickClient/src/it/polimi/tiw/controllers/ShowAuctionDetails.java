package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerletThymeleafSQL;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.dao.OfferDao;
import it.polimi.tiw.managment.TemplatePaths;
import it.polimi.tiw.models.Auction;
import it.polimi.tiw.models.Offer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowAuctionDetails", value = "/Details")
public class ShowAuctionDetails extends BasicServerletThymeleafSQL {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String input = request.getParameter("auction");

        AuctionDao auctionDao = new AuctionDao(this.getConnection());
        OfferDao   offerDao   = new OfferDao(this.getConnection());



        try {
            if(input == null ) throw new Exception("No auction specified");

            int auctionId = Integer.parseInt(input);
            Auction auction     = auctionDao.getAuctionById(auctionId);
            List<Offer> offerts = offerDao.getOffertById(auctionId);

            if(auction == null ) throw new Exception("This acution does not exist");

            request.setAttribute("auction",auction);
            request.setAttribute("offerts",offerts);

            this.templateRenderer(request,response, TemplatePaths.auctionDetails);

        } catch (SQLException throwables) {
            throwables.printStackTrace();

            this.setError(request,response,"SQL ERROR",TemplatePaths.auctionDetails);
        } catch (Exception e) {
            e.printStackTrace();
            this.setError(request,response,e.getMessage(),TemplatePaths.auctionDetails);
        }

    }

}
