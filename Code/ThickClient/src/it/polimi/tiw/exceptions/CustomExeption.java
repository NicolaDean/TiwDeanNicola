package it.polimi.tiw.exceptions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

public class CustomExeption extends Exception{

    public String message;

    public CustomExeption(String msg)
    {
        super(msg);
        this.message = msg;
    }

    public void SendError(HttpServletResponse response)
    {
        try {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(this.message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
