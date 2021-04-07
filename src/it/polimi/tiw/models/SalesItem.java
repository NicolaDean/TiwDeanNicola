package it.polimi.tiw.models;

public class SalesItem {

    private int code;
    private String name;
    private String description;
    private String pathImage;

    public SalesItem(int code,String name,String description,String pathImage)
    {
        this.code           = code;
        this.name           = name;
        this.description    = description;
        this.pathImage      = pathImage;
    }

    public int getCode()
    {
        return this.code;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getPathImage()
    {
        return this.pathImage;
    }



}
