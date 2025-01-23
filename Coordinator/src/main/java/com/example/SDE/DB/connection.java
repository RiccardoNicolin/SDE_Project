package com.example.SDE.DB;

import java.sql.Connection;
import java.sql.DriverManager;

import com.example.SDE.config;

public class connection {
    //GRANT ALL ON table TO utente --> ogni volta che una tabell viene create da docker;
    // Function to connect to PostgreSQL database

    public static Connection connectToDB() {
        Connection connection = null;
        try {
            // Database credentials
            String url = config.DB_URL;//link to db
            String user = config.DB_USER; //user to access DB
            String password = config.DB_PASSWORD; //Db password

            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            //System.out.println("Connected to the PostgreSQL server successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

}
