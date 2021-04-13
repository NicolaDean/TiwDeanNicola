package it.polimi.tiw.dao;

import it.polimi.tiw.models.SalesItem;
import it.polimi.tiw.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesItemDao {

    private Connection connection;

    public SalesItemDao(Connection connection)
    {
        this.connection = connection;
    }

    public List<SalesItem> getItemById() throws SQLException {
        List<SalesItem> salesItem = new ArrayList<>();

        String query = "select id,name,description,imagepath from salesItems";
        PreparedStatement statement = null;
        ResultSet queryResult       = null;

        try {
            statement = connection.prepareStatement(query);
            queryResult = statement.executeQuery();

            while(queryResult.next())
            {
                salesItem.add(new SalesItem(queryResult.getInt("id"),
                                            queryResult.getString("name"),
                                            queryResult.getString("description"),
                                            "png"));

            }
        }
        catch (SQLException e)
        {
            throw new SQLException();
        }


        return salesItem;
    }

    public int createSalesItem(String name,String description,String fileFormat) {
        PreparedStatement statement = null;

        //SALES ITEM INSERTION
        String query = "insert into salesItems (name,description,fileFormat) values (?,?,?)";
        try {
            statement = this.connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,description);
            statement.setString(3,fileFormat);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //GET SALES ITEM CODE
        statement = null;
        ResultSet queryResult       = null;

        query = "select max(id) as id from salesItems";

        int out = -1;
        try
        {
            statement = this.connection.prepareStatement(query);
            queryResult = statement.executeQuery();

            while(queryResult.next()) {
                out = queryResult.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return out;
    }
}
