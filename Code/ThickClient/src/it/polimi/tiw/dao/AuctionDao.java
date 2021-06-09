package it.polimi.tiw.dao;

import it.polimi.tiw.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuctionDao {

    private Connection connection;

    public AuctionDao(Connection connection)
    {
        this.connection = connection;
    }

    public int createAution(int userid, String name, String description, String fileFormat, Timestamp expiringDate, int initialOffer, int minimumOffer)
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

            System.out.println("Create -> " + expiringDate);
            queryParameters.setTimestamp(5,expiringDate);

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
                                queryResult.getInt(   "itemId"),
                                queryResult.getString("name"),
                                queryResult.getString("description"),
                                queryResult.getString("fileFormat")
                        );

                Offer offer =
                        new Offer(
                                queryResult.getString("userName"),
                                queryResult.getInt(   "maxOffer"),
                                queryResult.getTimestamp(  "maxOfferDate")
                        );
                Address address =
                        new Address(
                                queryResult.getString("address"),
                                queryResult.getString("city"),
                                queryResult.getInt(   "cap"),
                                queryResult.getString("country")
                        );

                Auction auction   =
                        new Auction(
                                queryResult.getInt("auctionID"),
                                queryResult.getInt("userId"),
                                item,
                                queryResult.getInt("initialPrize"),
                                queryResult.getInt("minimumOffer"),
                                queryResult.getTimestamp("expiringDate"),
                                offer,
                                address,
                                queryResult.getBoolean("closed")
                        );

                auctions.add(auction);

            }
        }
        catch (SQLException e)
        {
            throw new SQLException();
        }

        for(Auction a:auctions){
            System.out.println("NOME:--> " + a.getSalesItem().getName());

            System.out.println(a.getExpiringDate() + " -> " +a.calculateExpiringTime());
        }

        return auctions;
    }

    public void setAuctionClosed(int id) throws SQLException {
        if(getAuctionById(id).isClosable())
        {
            String query = "update auctions set closed = true  where (id=?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);

            statement.executeUpdate();
        }

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

    /**
     * Extract all opened and closed auctions of a specific user id
     * @param userid
     * @return
     * @throws SQLException
     */
    public UserProfileData getUserProfileData(int userid) throws SQLException {

        return new UserProfileData(getOpenAuctions(userid),getClosedAuctions(userid));
    }

    public List<Auction> getWinnedAuctions(int userid) throws SQLException {
        String query = "select * from auctionsData where expiringDate < CURRENT_TIME() and winnerId =?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1,userid);
        return executeAuctionSelect(statement);
    }
    /**
     *
     * @param Filter Filter for the auction search
     * @return a list of auctions with name and description keyword
     * @throws SQLException if something goes wrong
     */
    public List<Auction> getAuctionsFromNameOrDescription(String Filter) throws SQLException {

        if(Filter == null)
        {
            return getAuctions();

        }

        String query = "select * from auctionsData where (expiringDate > CURRENT_TIME()) and (name LIKE '%"+Filter+"%' or description LIKE '%"+Filter+"%') order by expiringDate ASC";

        System.out.println(query);
        PreparedStatement statement = connection.prepareStatement(query);
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
            return res.get(0);
        }

    }
}
