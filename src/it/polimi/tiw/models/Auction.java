package it.polimi.tiw.models;

import java.util.Date;

public class Auction {

    private int id;
    private int salesItemId;
    private int initialPrice;
    private int minimumOffer;
    private Date expiringDate;


    public Auction(int id,int salesItemId,int initialPrice,int minimumOffer,Date expiringDate)
    {
        this.id           = id;
        this.salesItemId  = salesItemId;
        this.initialPrice = initialPrice;
        this.minimumOffer = minimumOffer;
        this.expiringDate = expiringDate;
    }

    public int getId() {
        return this.id;
    }

    public int getSalesItemId() {
        return this.salesItemId;
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
}
