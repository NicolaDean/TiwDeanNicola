package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerletThymeleafSQL;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.managment.TemplatePaths;
import it.polimi.tiw.models.Auction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Auctions", value = "/Auctions")
public class ShowAuctions extends BasicServerletThymeleafSQL {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuctionDao auctionDao = new AuctionDao(this.getConnection());

        String filter = request.getParameter("filter");

        List<Auction> auctions = new ArrayList<>();
        try {
            auctions = auctionDao.getAuctionsFromNameOrDescription(filter);
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }

        if(!auctions.isEmpty())
        {
            request.setAttribute("auctions",auctions);
            this.templateRenderer(request,response,TemplatePaths.auctionList);
        }
        else
        {
            request.setAttribute("auctions",auctions);
            this.setError(request,response,"No result found",TemplatePaths.auctionList);
        }

    }
    }

