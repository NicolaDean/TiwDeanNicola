package it.polimi.tiw.models;

import it.polimi.tiw.managment.JsonSerializable;

import java.sql.Date;
import java.util.List;

public class AuctionDetails extends JsonSerializable {

    private Auction auction;
    private List<Offer> offerList;
    public AuctionDetails(Auction auction,List<Offer> offerList)
    {
        this.auction   = auction;
        this.offerList = offerList;
    }

}
