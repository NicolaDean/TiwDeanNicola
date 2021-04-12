package it.polimi.tiw.dao;

import it.polimi.tiw.models.SalesItem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuctionDao {

    private Connection connection;

    public AuctionDao(Connection connection)
    {
        this.connection = connection;
    }

    public void createAution(String name, String description, String imgPath, Date expiringDate,int initialOffer,int minimumOffer)
    {
        SalesItemDao salesItemDao = new SalesItemDao(this.connection);
        PreparedStatement queryParameters = null;

        int salesItemid = salesItemDao.createSalesItem(name,description,imgPath);
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
            queryParameters.setDate(5,expiringDate);

            queryParameters.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
