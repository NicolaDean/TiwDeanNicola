package it.polimi.tiw.controllers;

import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.models.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@MultipartConfig
@WebServlet(name = "CreateAuction", value = "/CreateAuction")
public class CreateAuction  extends BasicServerletThymeleafSQL {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("currentUser");

        //Check if session is valid
        if(user == null)
        {
            request.setAttribute("errorMsg","Sorry you are not logged so what you are wainting for :)");
            response.sendRedirect(getServletContext().getContextPath() + "/Login");
            return;
        };


        AuctionDao auctionDao = new AuctionDao(this.getConnection());



        int initialOffer  = Integer.parseInt(request.getParameter("initialOffer"));
        int minimumOffer  = Integer.parseInt(request.getParameter("minimumOffer"));


        //TRY TO PARS DATA
        Date expiringData = null;

        String rawData = request.getParameter("expiringDate");
        DateFormat formattedData = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        try {
            expiringData =  formattedData.parse(rawData);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Part filePart = request.getPart("file");


        String fileName = filePart.getName();
        String imgPath = user.getId()+"/" +"a.png";
                //Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        int itemId = 0;
        itemId = auctionDao.createAution(1,
                                            request.getParameter("name"),
                                            request.getParameter("description"),
                                            "png",
                                            expiringData,
                                            initialOffer,
                                            minimumOffer
        );



        File uploads = new File(getServletContext().getInitParameter("imgUpload")+"/"+user.getId()+"/");

        File file = new File(uploads, itemId+".png");

        if (!uploads.exists()) {
            uploads.mkdirs();
        }

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }

    }
}
