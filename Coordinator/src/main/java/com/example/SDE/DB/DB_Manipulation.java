package com.example.SDE.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.SDE.DB.connection.connectToDB;
import com.example.SDE.Data_structure.User;

@CrossOrigin(origins = "*")
//NOTA BENE:
//Aggiungere cross origin solo per react e il DB, altrimenti rischio attacco
//Se si aggiunge una tabella garantire i permessi all'utente tramite GRANT

@RestController
public class DB_Manipulation {

    @GetMapping("/getUser")
    public User getUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("Username: " + username + " Password: " + password);
        Connection connection = connectToDB();
        if (connection != null) {
            try {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                // Create a statement
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);

                // Execute a query
                ResultSet rs = stmt.executeQuery();

                int id = 0;
                String user = "";
                String pswd = "";
                // Process the result set
                while (rs.next()) {
                    id = rs.getInt(1);
                    user = rs.getString(2);
                    pswd = rs.getString(3);
                }

                // Close the connection
                connection.close();
                System.out.println("Connection closed.");
                return new User(id, user, pswd);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

}
