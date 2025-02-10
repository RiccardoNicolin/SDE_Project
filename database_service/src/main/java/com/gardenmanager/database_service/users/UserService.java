package com.gardenmanager.database_service.users;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gardenmanager.database_service.exceptions.DuplicateUsernameException;
import com.gardenmanager.database_service.exceptions.UserNotFoundException;
import com.gardenmanager.database_service.utils.JwtUtil;
import com.gardenmanager.database_service.utils.PasswordUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        Random rand = new Random();
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new DuplicateUsernameException("Username '" + user.getUsername() + "' is already taken.");
        }

        boolean loop = true;
        while (loop) {
            try {
                getUserById(user.getId());
                System.out.println("User with ID " + user.getId() + " already exists, calculating new ID...");
                user.setId(rand.nextInt(1000)); //re-run until unique ID is found

            } catch (Exception e) {
                loop = false;
            }

        }
        // Hash password before saving

        String pass = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(pass);
        userRepository.InsertUser(user.getId(), user.getUsername(), pass);
        userRepository.flush();
        return user;
        //return userRepository.saveAndFlush(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User '" + username + "' not found.");
        }
        return user;
    }

    public String authenticateUser(String username, String password) {
        //System.out.println("authenticating user: " + username + " " + password);
        User user = userRepository.findByUsername(username);
        if (user == null || !PasswordUtil.verifyPassword(password, user.getPassword())) {
            throw new UserNotFoundException("Invalid username or password.");
        }
        return JwtUtil.generateToken(username);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
