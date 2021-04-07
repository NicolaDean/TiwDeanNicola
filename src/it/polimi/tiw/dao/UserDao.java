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
}
