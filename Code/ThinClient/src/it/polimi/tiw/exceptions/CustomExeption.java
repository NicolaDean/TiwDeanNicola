package it.polimi.tiw.exceptions;

import it.polimi.tiw.controllers.template.BasicServerletThymeleaf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomExeption extends Exception{

    public String message;

    public CustomExeption(String msg)
    {
        super(msg);
        this.message = msg;
    }

    public String SendError()
    {
        return this.message;
    }


}
