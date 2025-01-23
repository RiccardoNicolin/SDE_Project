package com.example.SDE.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.SDE.DB.connection.connectToDB;
import com.example.SDE.Data_structure.AuthResponse;
import com.example.SDE.login.JwtProvider;

@CrossOrigin(origins = "*")

@RestController
public class controller {

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody String arg) {
        System.out.println("Signin request received");
        JSONObject json = new JSONObject(arg);
        String username = json.getString("username");
        String password = json.getString("password");

        try {
            Authentication authentication = authenticate(username, password);
            if (authentication == null) {
                throw new BadCredentialsException("Invalid username and password");
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Token generated");
            String token = JwtProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse();

            authResponse.setMessage("Login success");
            authResponse.setJwt(token);
            authResponse.setStatus(true);

            return new ResponseEntity<>(authResponse, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Login failed");
            authResponse.setStatus(false);
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    private Authentication authenticate(String username, String password) {
        Connection connection = connectToDB();
        int id = 0;
        if (connection != null) {
            System.out.println("Connection successful");

            try {

                // Create a statement
                Statement stmt = connection.createStatement();

                String query = "SELECT * FROM users WHERE username = ?";

                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, username);

                // Execute a query
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    //System.out.println("User found");
                    String checkPass = rs.getString("password");
                    id = rs.getInt("id");
                    if (password.equals(checkPass)) {
                        System.out.println("Password match");

                    } else {
                        throw new BadCredentialsException("Invalid password");
                    }
                } else {
                    System.out.println("User not found");
                    throw new BadCredentialsException("Invalid username and password");

                }
            } catch (Exception e) {
                System.out.println("Error in connection: " + e.getMessage());
                return null;
            }

        } else {
            System.out.println("Connection failed");
            return null;
        }
        Authentication token = new UsernamePasswordAuthenticationToken(username, Integer.toString(id), null);
        return token;
    }
}
