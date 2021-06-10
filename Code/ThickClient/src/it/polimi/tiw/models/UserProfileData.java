package it.polimi.tiw.models;

import it.polimi.tiw.managment.JsonSerializable;

import java.sql.Date;
import java.util.List;

public class UserProfileData extends JsonSerializable {

    private List<Auction> open;
    private List<Auction> closed;
    private List<Auction> winned;
    public UserProfileData(List<Auction> open,List<Auction> closed,List<Auction> winned)
    {
        this.open   = open;
        this.closed = closed;
        this.winned = winned;
    }

}
