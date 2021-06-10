package it.polimi.tiw.managment;

import javax.servlet.ServletContext;

public class CurrentContext {

    static ServletContext context;



    public static void setContext(ServletContext ctx)
    {
        context = ctx;
    }
}
