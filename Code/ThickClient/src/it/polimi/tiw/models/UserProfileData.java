package it.polimi.tiw.models;

import it.polimi.tiw.managment.JsonSerializable;

import java.sql.Date;
import java.util.List;

public class UserProfileData extends JsonSerializable {

    private List<Auction> open;
    private List<Auction> closed;
    public UserProfileData(List<Auction> open,List<Auction> closed)
    {
        this.open   = open;
        this.closed = closed;
    }

}
