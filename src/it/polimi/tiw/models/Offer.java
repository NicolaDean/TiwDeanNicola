package it.polimi.tiw.models;

import java.util.Date;

public class Offer {

    String userName;
    int offer;
    Date date;

    public Offer(String userName,int offer,Date date)
    {
        this.userName   = userName;
        this.offer      = offer;
        this.date       = date;
    }

    public String getuserName() {
        return this.userName;
    }

    public Date getDate() {
        return this.date;
    }

    public int getOffer() {
        return this.offer;
    }
}
