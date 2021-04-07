package it.polimi.tiw.models;

public class Offer {

    int idUser;
    int idAuction;
    int offer;

    public Offer(int idUser,int idAuction,int offer)
    {
        this.idUser     = idUser;
        this.idAuction  = idAuction;
        this.offer      = offer;
    }

    public int getIdUser() {
        return this.idUser;
    }

    public int getIdAuction() {
        return this.idAuction;
    }

    public int getOffer() {
        return this.offer;
    }
}
