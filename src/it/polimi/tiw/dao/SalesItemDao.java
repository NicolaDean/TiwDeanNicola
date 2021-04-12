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
                                            queryResult.getString("imagePath")));

            }
        }
        catch (SQLException e)
        {
            throw new SQLException();
        }


        return salesItem;
    }

    public int createSalesItem(String name,String description,String imgPath)
    {
        return -1;
    }
}
