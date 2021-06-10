package it.polimi.tiw.dao;

import it.polimi.tiw.exceptions.CustomExeption;
import it.polimi.tiw.models.Offer;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfferDao {

    private Connection connection;

    public OfferDao(Connection connection)
    {
        this.connection = connection;
    }

    public void offertInsertion(int userId, int auctionId, int offer, Date date)
    {

    }

    public  List<Offer> offertSelect(int auctionId,String query)
    {
        List<Offer> offerts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,auctionId);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                Offer o = new Offer(
                        resultSet.getString("name"),
                        resultSet.getInt("offer"),
                        resultSet.getTimestamp("offerDate")
                );
                offerts.add(o);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return offerts;
    }
    public List<Offer> getOffertById(int auctionId)
    {
        String query = "select * from offertsData where auctionsid = ? order by offerDate desc ";

        return offertSelect(auctionId,query);
    }

    public Offer getMaxOffert(int auctionId) {
        String query = "select * from maxOfferData where auctionsid = ?";

        List<Offer> out = offertSelect(auctionId, query);
        if(out.isEmpty()) return null;
        return out.get(0);
    }

    public void insertOffer(int userId,int auctionid,int offer) throws SQLException, CustomExeption {
        String query = "insert into offerts (userid,auctionsid,offer,offerDate) values (?,?,?,?)";

        Timestamp timestamp = new java.sql.Timestamp((new Date()).getTime());

        Offer maxOffer = getMaxOffert(auctionid);
        //Controll valid offer
        AuctionDao auctionDao = new AuctionDao(this.connection);

        int minOffer  = auctionDao.getAuctionById(auctionid).getMinimumOffer();

        //Controll valid offer
        if(maxOffer != null &&  maxOffer.getOffer()  > offer)
            throw  new CustomExeption("Offer lower then maxOffer : " + maxOffer.getOffer() + " $");
        else if(maxOffer != null &&  maxOffer.getOffer()+ minOffer  > offer)
            throw  new CustomExeption("You need to offer at least" + minOffer + "$ more than "+ maxOffer.getOffer() + " $");


        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.setInt(1,userId);
        statement.setInt(2,auctionid);
        statement.setInt(3,offer);
        statement.setTimestamp(4,timestamp);

        statement.executeUpdate();

    }
}



