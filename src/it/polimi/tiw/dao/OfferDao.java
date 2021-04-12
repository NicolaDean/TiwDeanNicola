package it.polimi.tiw.dao;

import java.sql.Connection;

public class OfferDao {

    private Connection connection;

    public OfferDao(Connection connection)
    {
        this.connection = connection;
    }


}
