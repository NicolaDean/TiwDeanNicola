package it.polimi.tiw.controllers;

import it.polimi.tiw.dao.AuctionDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "CreateAuction", value = "/CreateAuction")
public class CreateAuction  extends BasicServerletThymeleadSQL {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AuctionDao auctionDao = new AuctionDao(this.getConnection());

        String rawData = request.getParameter("startDate");
        SimpleDateFormat formattedData = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ns");
        Date expiringData = null;


        int initialOffer  = Integer.parseInt(request.getParameter("initialOffer"));
        int minimumOffer  = Integer.parseInt(request.getParameter("minimumOffer"));

//surround below line with try catch block as below code throws checked exception
        try {
            expiringData = (Date) formattedData.parse(rawData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        auctionDao.createAution(request.getParameter("name"),
                                request.getParameter("description"),
                                request.getParameter("imgPath"),
                                expiringData,
                                initialOffer,
                                minimumOffer
        );



    }
}
