package it.polimi.tiw.dao;

import it.polimi.tiw.models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {


    private Connection connection;

    public UserDao(Connection connection)
    {
        this.connection = connection;
    }

    public User checkUserLogin(String email,String password) throws SQLException {
        User usr = null;

        String query                = "select id,name from users where email = ? and password = ?";
        PreparedStatement statement = null;
        ResultSet queryResult       = null;



        try
        {
            statement = connection.prepareStatement(query);
            statement.setString(1,email);
            statement.setString(2,password);

            queryResult = statement.executeQuery();

            while(queryResult.next())
            {
                usr = new User(queryResult.getInt("id"),email,queryResult.getString("name"));
                System.out.println(usr.getName() + usr.getName()+usr.getEmail());
            }

        }catch (SQLException e)
        {
            throw new SQLException();
        }

        return usr;
    }
}
