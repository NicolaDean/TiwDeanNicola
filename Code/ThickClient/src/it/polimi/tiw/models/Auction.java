package it.polimi.tiw.models;

import it.polimi.tiw.managment.JsonSerializable;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Auction extends JsonSerializable {

    private int         id;
    private int         userId;
    private SalesItem   salesItem;
    private int         initialPrice;
    private int         minimumOffer;
    private Date        expiringDate;
    private Offer       maxOffer;
    private Address     address;
    private Boolean     closed;
    private String      imgPath;
    private String      remainingTime;
    private String      date;
    private List<Offer> offerts = null;

    public Auction(int id, int userId, SalesItem item, int initialPrice, int minimumOffer, Timestamp expiringDate, Offer maxOffer, Address address, boolean closed)
    {
        this.id           = id;
        this.userId       = userId;
        this.salesItem    = item;
        this.initialPrice = initialPrice;
        this.minimumOffer = minimumOffer;
        this.expiringDate = expiringDate;
        this.maxOffer     = maxOffer;
        this.address      = address;
        this.closed       = closed;
        this.imgPath        = this.getImagePath(this.salesItem.getFileFormat());
        this.remainingTime  = this.calculateExpiringTime();

        DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm");

        if(expiringDate != null)
            this.date       = formatter.format(expiringDate);
        else
            this.date = "No offert done";

    }

    public void setOfferts(List<Offer> offerts)
    {
        this.offerts = offerts;
    }
    public int getId() {
        return this.id;
    }

    public SalesItem getSalesItem() {
        return this.salesItem;
    }

    public int getInitialPrice() {
        return this.initialPrice;
    }

    public int getMinimumOffer() {
        return this.minimumOffer;
    }

    public String getExpiringDate() {
        DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
        return formatter.format(this.expiringDate);
    }

    //TODO aggiongere una funzione per stampare in stringa una data formattata per bene

    public String calculateExpiringTime()
    {
        Date now = new Date();

        long difference = this.expiringDate.getTime() - now.getTime();
        boolean expired = difference <= 0;

        if (expired) difference = difference * -1;

        int days    =   (int)(((difference/1000)/60)/60)/24;
        int hours   =   (int)(((difference/1000)/60)/60)%24;
        int minutes =   (int)(((difference/1000)/60)%60);
        int seconds =   (int)((difference/1000)%60);

        if(!expired)
        {
            return (days + " days " + hours+ " hours " + minutes + " min left");
        }
        else
        {
            return  ( "Expired " + days + " days ago");
        }

    }
    public Offer getMaxOffer() {
        return maxOffer;
    }

    /**
     * Generate the path for this auction image
     * @param fileExtension extension of the file uploaded
     * @return
     */
    public String getImagePath(String fileExtension)
    {
        //example : userid/auctionid/itemId.png
        return this.userId +"/" + this.salesItem.getCode() + fileExtension;
    }

    public Address getAddress()
    {
        return this.address;
    }

    public boolean isClosed()
    {
        return this.closed;
    }
    public boolean isOpen()
    {
        return !this.closed;
    }
    public boolean isClosable()
    {
        return expiringDate.getTime() < (new Date()).getTime() && !this.closed;
    }
}

