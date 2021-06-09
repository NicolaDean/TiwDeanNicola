package it.polimi.tiw.models;


import java.util.List;

public class UserProfileData {

    private List<Auction> open;
    private List<Auction> closed;
    public UserProfileData(List<Auction> open, List<Auction> closed)
    {
        this.open   = open;
        this.closed = closed;
    }

    public List<Auction> getOpen()
    {
        return open;
    }
    public List<Auction> getClosed()
    {
        return closed;
    }
}
