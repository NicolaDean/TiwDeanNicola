package it.polimi.tiw.models;

public class SalesItem {

    private int code;
    private String name;
    private String description;
    private String fileFormat;

    public SalesItem(int code,String name,String description,String fileFormat)
    {
        this.code           = code;
        this.name           = name;
        this.description    = description;
        this.fileFormat     = fileFormat;
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

    public String getFileFormat() {return  this.fileFormat;}


}
