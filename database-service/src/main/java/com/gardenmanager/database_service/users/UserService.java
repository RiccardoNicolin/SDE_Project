package com.gardenmanager.database_service.users;

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
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new DuplicateUsernameException("Username '" + user.getUsername() + "' is already taken.");
        }
        // Hash password before saving
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        return userRepository.save(user);
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
