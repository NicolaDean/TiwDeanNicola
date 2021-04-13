package it.polimi.tiw.controllers;

import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.managment.TemplatePaths;
import it.polimi.tiw.models.Auction;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowAuctions extends BasicServerletThymeleafSQL{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuctionDao auctionDao = new AuctionDao(this.getConnection());
        List<Auction> auctions = new ArrayList<>();
        try {
            auctions = auctionDao.getAuctions();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("auctions", auctions);
        this.getTemplateEngine().process(TemplatePaths.auctionList, ctx, response.getWriter());
    }
    }

