package it.polimi.tiw.models;

import it.polimi.tiw.managment.JsonSerializable;

import java.util.Date;

public class Auction extends JsonSerializable {

    private int id;
    private int userId;
    private SalesItem salesItem;
    private int initialPrice;
    private int minimumOffer;
    private Date expiringDate;
    private Offer maxOffer;


    public Auction(int id,int userId,SalesItem item,int initialPrice,int minimumOffer,java.sql.Date expiringDate,Offer maxOffer)
    {
        this.id           = id;
        this.userId       = userId;
        this.salesItem    = item;
        this.initialPrice = initialPrice;
        this.minimumOffer = minimumOffer;
        this.expiringDate = new java.util.Date(expiringDate.getTime());
        this.maxOffer     = maxOffer;
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

    public Date getExpiringDate() {
        return this.expiringDate;
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
}
