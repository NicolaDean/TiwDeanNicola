package it.polimi.tiw.dao;

import it.polimi.tiw.models.Auction;
import it.polimi.tiw.models.SalesItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuctionDao {

    private Connection connection;

    public AuctionDao(Connection connection)
    {
        this.connection = connection;
    }

    public int createAution(int id, String name, String description, String fileFormat, Date expiringDate, int initialOffer, int minimumOffer)
    {
        SalesItemDao salesItemDao = new SalesItemDao(this.connection);
        PreparedStatement queryParameters = null;

        int salesItemid = -1;
        salesItemid = salesItemDao.createSalesItem(name,description,fileFormat);

        int userid = 1;

        String query  = "INSERT INTO AUCTIONS " +
                        "(userid,salesItemid,initialPrize,minimumOffer,espiringDate)" +
                        "VALUES" +
                        "(?,?,?,?,?)";

        try {
            queryParameters = this.connection.prepareStatement(query);
            queryParameters.setInt(1,userid);
            queryParameters.setInt(2,salesItemid);
            queryParameters.setInt(3,initialOffer);
            queryParameters.setInt(4,minimumOffer);
            queryParameters.setDate(5,new java.sql.Date(expiringDate.getTime()));

            queryParameters.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return salesItemid;

    }

    public List<Auction> getAuctions() throws SQLException {
        String query = "select * from auctionsData";
        List<Auction> auctions=new ArrayList<Auction>();

        PreparedStatement statement = null;
        ResultSet queryResult       = null;

        try {
            statement = connection.prepareStatement(query);
            queryResult = statement.executeQuery();

            while(queryResult.next())
            {
                SalesItem it = new SalesItem(queryResult.getInt("itemId"),
                                             queryResult.getString("name"),
                                             queryResult.getString("description"),
                                             queryResult.getString("fileFormat"));

                Auction au   = new Auction   (queryResult.getInt("auctionID"),
                        queryResult.getInt("userId"),
                        it,
                        queryResult.getInt("initialPrize"),
                        queryResult.getInt("minimumOffer"),
                        queryResult.getDate("expiringDate"));

                auctions.add(au);

            }
        }
        catch (SQLException e)
        {
            throw new SQLException();
        }

        for(Auction a:auctions)
            System.out.println("NOME:--> " + a.getSalesItem().getName());
        return auctions;

    }
}
