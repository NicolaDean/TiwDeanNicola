package it.polimi.tiw.models;

public class User {
    private int id;
    private String email;
    private String name;

    public User(String email,String name)
    {
        this.email  = email;
        this.name   = name;
    }

    public User(int id,String email,String name)
    {
        this.id     = id;
        this.email  = email;
        this.name   = name;
    }
    public String getEmail()
    {
        return this.email;
    }

    public String getName()
    {
        return this.name;
    }

    public int getId()
    {
        return this.id;
    }

}
