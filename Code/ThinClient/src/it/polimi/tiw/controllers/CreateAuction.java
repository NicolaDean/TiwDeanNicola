package it.polimi.tiw.controllers;

import it.polimi.tiw.controllers.template.BasicServerletThymeleafSQL;
import it.polimi.tiw.dao.AuctionDao;
import it.polimi.tiw.exceptions.CustomExeption;
import it.polimi.tiw.managment.TemplatePaths;
import it.polimi.tiw.models.User;

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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        System.out.println("USER: ->" + user.getId());


        AuctionDao auctionDao = new AuctionDao(this.getConnection());



        int     initialOffer  =-1;
        int     minimumOffer  =-1;

        String  name          = request.getParameter("name");
        String  description   = request.getParameter("description");

        Part filePart = request.getPart("file");
        //TRY TO PARS DATA
        Date today = new Date();
        Date expiringData = null;
        Timestamp timestamp = null;
        String rawData = request.getParameter("expiringDate");
        DateFormat formattedData = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        try {
            expiringData =  formattedData.parse(rawData);
            long time = expiringData.getTime();
            timestamp = new java.sql.Timestamp(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String fileName = filePart.getName();
        Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String imgFormat =filePart.getContentType();
        imgFormat="."+imgFormat.substring(imgFormat.lastIndexOf("/")+1);
        imgFormat = imgFormat.toLowerCase(Locale.ROOT);
        try {
            //INPUT VALIDATION:

            if(name == null || name.equals(""))
                throw new CustomExeption("Item Name empty");
            if(timestamp == null)
                throw new CustomExeption("Expiring date empty");
            if(description == null || description.equals(""))
                throw new CustomExeption("Item description empty");

            try
            {
                initialOffer = Integer.parseInt(request.getParameter("initialOffer"));
                minimumOffer = Integer.parseInt(request.getParameter("minimumOffer"));
            } catch (Exception e)
            {
                throw new CustomExeption("Invalid integer for initial and minimum offert");
            }



            if(initialOffer < minimumOffer)
                throw new CustomExeption("The Minimum Offer shouldnt be higher then initial price");

            if(timestamp.getTime() < today.getTime())
                throw new CustomExeption("The expiring date shouldnt be in the Past");

            if(filePart == null)
                throw new CustomExeption("You have to insert at least one image to show the objecte you want to sell");

            if(!imgFormat.equals(".png") && !imgFormat.equals(".jpg") && !imgFormat.equals(".jpeg")&& !imgFormat.equals(".webp"))
                throw new CustomExeption("Image file format dosnt supported, only png,jpg,jpeg");

        } catch (CustomExeption customExeption) {
            System.out.println(customExeption.SendError());
            this.setError(request,response,customExeption.SendError(), TemplatePaths.auctionCreate);
            return;
        }




        int itemId = 0;
        try {
            itemId = auctionDao.createAution(   user.getId(),
                                                name,
                                                description,
                                                imgFormat,
                                                timestamp,
                                                initialOffer,
                                                minimumOffer
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            this.setError(request,response,"Sql error occur", TemplatePaths.auctionCreate);
            return;
        } catch (CustomExeption customExeption) {
            customExeption.printStackTrace();
            System.out.println(customExeption.SendError());
            this.setError(request,response,customExeption.SendError(), TemplatePaths.auctionCreate);
            return;
        }

        File uploads = new File(getServletContext().getInitParameter("imgUpload")+"/"+user.getId()+"/");

        File file = new File(uploads, itemId+imgFormat);

        if (!uploads.exists()) {
            uploads.mkdirs();
        }

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }


        response.sendRedirect(getServletContext().getContextPath() + "/home");

    }
}
