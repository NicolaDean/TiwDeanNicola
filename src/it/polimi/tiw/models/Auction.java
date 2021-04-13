package it.polimi.tiw.models;

import java.sql.Date;

public class Auction {

    private int id;
    private int userId;
    private SalesItem salesItem;
    private int initialPrice;
    private int minimumOffer;
    private Date expiringDate;


    public Auction(int id,int userId,SalesItem item,int initialPrice,int minimumOffer,Date expiringDate)
    {
        this.id           = id;
        this.userId       = userId;
        this.salesItem    = item;
        this.initialPrice = initialPrice;
        this.minimumOffer = minimumOffer;
        this.expiringDate = new java.sql.Date(expiringDate.getTime());
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


    /**
     * Generate the path for this auction image
     * @param fileExtension extension of the file uploaded
     * @return
     */
    public String getImagePath(String fileExtension)
    {
        //example : userid/auctionid/itemId.png
        return "/" + this.userId +"/" + this.salesItem.getCode() + "." + fileExtension;
    }
}
