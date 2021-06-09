package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerlet;
import it.polimi.tiw.dao.OfferDao;
import it.polimi.tiw.models.User;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@MultipartConfig
@WebServlet(name = "CreateOffer", value = "/NewOffer")
public class CreateOffer extends BasicServerlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String in1         = StringEscapeUtils.escapeJava(request.getParameter("auctionid"));
        String in2         = StringEscapeUtils.escapeJava(request.getParameter("offer"));

        OfferDao offerDao = new OfferDao(this.getConnection());
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentUser");
        System.out.println(in1);
        int auctionid = Integer.parseInt(in1);
        int offer =Integer.parseInt(in2);

        try {
            offerDao.insertOffer(u.getId(),auctionid,offer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
