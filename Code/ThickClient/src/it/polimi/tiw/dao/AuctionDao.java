package it.polimi.tiw.dao;

import it.polimi.tiw.managment.JsonSerializable;
import it.polimi.tiw.models.Auction;
import it.polimi.tiw.models.Offer;
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

    public int createAution(int userid, String name, String description, String fileFormat, Date expiringDate, int initialOffer, int minimumOffer)
    {
        SalesItemDao salesItemDao = new SalesItemDao(this.connection);
        PreparedStatement queryParameters = null;

        int salesItemid = -1;
        salesItemid = salesItemDao.createSalesItem(name,description,fileFormat);


        String query ="insert into auctions " +
                      "(userid,salesItemid,initialPrize,minimumOffer,expiringDate)"+
                      "values"+
                      "(?,?,?,?,?)";
        System.out.println(expiringDate.toString());
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

    /**
     * Generic auction select query (recive a select query and execute it)
     * @param statement a select query
     * @return  a list of auctions
     * @throws SQLException if something goes wrong in the query
     */
    public List<Auction> executeAuctionSelect(PreparedStatement statement) throws SQLException {
        List<Auction> auctions=new ArrayList<Auction>();

        ResultSet queryResult       = null;

        try {

            queryResult = statement.executeQuery();

            while(queryResult.next())
            {
                SalesItem item =
                        new SalesItem(
                            queryResult.getInt("itemId"),
                            queryResult.getString("name"),
                            queryResult.getString("description"),
                            queryResult.getString("fileFormat")
                        );

                Offer offer =
                        new Offer(
                                queryResult.getString("userName"),
                                queryResult.getInt("maxOffer"),
                                queryResult.getDate("maxOfferDate")
                        );

                Auction auction   =
                        new Auction(
                            queryResult.getInt("auctionID"),
                            queryResult.getInt("userId"),
                            item,
                            queryResult.getInt("initialPrize"),
                            queryResult.getInt("minimumOffer"),
                            queryResult.getDate("expiringDate"),
                            offer
                        );

                auctions.add(auction);

            }
        }
        catch (SQLException e)
        {
            throw new SQLException();
        }


        return auctions;
    }



    public List<Auction> getOpenAuctions(int userid) throws SQLException {
        String query = "select * from auctionsData where (userid=?) AND (expiringDate > CURRENT_TIME()) order by expiringDate asc";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1,userid);
        return executeAuctionSelect(statement);
    }

    public List<Auction> getClosedAuctions(int userid) throws SQLException {
        String query = "select * from auctionsData where (userid=?) AND (expiringDate < CURRENT_TIME()) order by expiringDate asc";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1,userid);
        return executeAuctionSelect(statement);
    }

    public List<Auction> getAuctions() throws SQLException {
        String query = "select * from auctionsData where expiringDate > CURRENT_TIME() order by expiringDate asc";
        PreparedStatement statement = connection.prepareStatement(query);


        return executeAuctionSelect(statement);
    }

    public Auction getAuctionById(int id) throws SQLException {
        String query = "select * from auctionsData where auctionid=?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1,id);
        List<Auction> res = executeAuctionSelect(statement);

        if(res.isEmpty())
        {
            return null;
        }
        else
        {
            return (Auction)res.get(0);
        }

    }
}
