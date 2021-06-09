package it.polimi.tiw.models;

import it.polimi.tiw.managment.JsonSerializable;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Offer extends JsonSerializable {

    String  userName;
    int     offer;
    String  date;

    public Offer(String userName, int offer, Timestamp date)
    {
        this.userName   = userName;
        this.offer      = offer;

        DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm");

        if(date != null)
            this.date       = formatter.format(date);
        else
            this.date = "No offert done";
    }

    public String getuserName() {
        return this.userName;
    }

    public String getDate() {
        return this.date;
    }

    public int getOffer() {
        return this.offer;
    }
}
