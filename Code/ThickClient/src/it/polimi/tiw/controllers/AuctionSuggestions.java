package it.polimi.tiw.controllers;

import com.google.gson.Gson;
import it.polimi.tiw.controllers.template.BasicServerlet;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.models.Auction;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "Suggestions", value = "/Suggestions")
public class AuctionSuggestions extends BasicServerlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("COOKIE READING");
        Gson gson = new Gson();

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }



        Integer[] ids = gson.fromJson(jb.toString(),Integer[].class);
        for(int i:ids)
        {
            System.out.print("- " + i+ " - ");
        }
        List<Integer> idsList = null;

        try {
            idsList = Arrays.stream(ids).distinct().collect(Collectors.toList());
        }catch (Exception e)
        {
            this.respondError("Error occured, No auctions suggested",response);
            return;
        }

        AuctionDao auctionDao = new AuctionDao(this.getConnection());

        List<Auction> auctions = new ArrayList<>();
        Date today = new Date();
        for(Integer id: idsList)
        {
            try {
                Auction a = auctionDao.getAuctionById(id);
                if(!a.isClosed() && !a.isClosed())
                {
                    auctions.add(a);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(auctions.isEmpty()){
            this.respondError("No suggestions available",response);
        }

        //TODO SORT AUCTIONS BY DATE
        this.sendJson(auctions,response);

        /*Cookie[] cookie = request.getCookies();

        System.out.println("COOKIE READING");
        for(Cookie c : cookie)
        {
            System.out.println(c.getValue());
            if(c.getName().equals("suggestions"))
            {
                Gson gson = new Gson();

                int [] ids = gson.fromJson(c.getValue(),int[].class);

            }
        }*/
    }
}
