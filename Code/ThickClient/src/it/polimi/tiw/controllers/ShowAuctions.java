package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerlet;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.managment.JsonSerializable;
import it.polimi.tiw.managment.TemplatePaths;
import it.polimi.tiw.models.Auction;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Auctions", value = "/Auctions")
@MultipartConfig
public class ShowAuctions extends BasicServerlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuctionDao auctionDao = new AuctionDao(this.getConnection());

        List<Auction> auctions = new ArrayList<>();

        try {
            auctions = auctionDao.getAuctions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        if(!auctions.isEmpty())
        {
            System.out.println("Return Auctions");

            this.sendJson(auctions,response);
        }
        else
        {
            this.respondError("No result found", response);
        }

    }
    }

