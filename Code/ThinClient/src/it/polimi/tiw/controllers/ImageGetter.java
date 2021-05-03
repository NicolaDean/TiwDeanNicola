package it.polimi.tiw.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

/**
 * this class allow you to get an image without knowing its real path inside the server
 */
@WebServlet("/ImageGetter/*")
public class ImageGetter extends HttpServlet {

    private String imgFolder= "";


    @Override
    public void init() throws ServletException {
            imgFolder = getServletContext().getInitParameter("imgUpload");
    }

    /**
     * Get the image without knowing its real path
     * @param request http request
     * @param response http response
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = request.getPathInfo();
        
        if(url == null)
        {
            return;
        }

        String imgName = URLDecoder.decode(url.substring(1), "UTF-8");
        File file = new File(imgFolder, url);


        response.setHeader("Content-Type", getServletContext().getMimeType(imgName));
        response.setHeader("Content-Length", String.valueOf(file.length()));

        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

        Files.copy(file.toPath(), response.getOutputStream());

    }
}
