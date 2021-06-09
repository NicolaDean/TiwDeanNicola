package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerletThymeleafSQL;
import it.polimi.tiw.dao.AuctionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Close", value = "/Close")
public class CloseAuction extends BasicServerletThymeleafSQL {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try
        {
            int id = Integer.parseInt(request.getParameter("auction"));
            AuctionDao dao = new AuctionDao(this.getConnection());
            dao.setAuctionClosed(id);
            System.out.println("Auction + " + id + " closed");
        }catch (Exception e)
        {
            e.printStackTrace();
           //TODO REDIRECT TO ERROR (You cant close this auction)
        }



    }

}
