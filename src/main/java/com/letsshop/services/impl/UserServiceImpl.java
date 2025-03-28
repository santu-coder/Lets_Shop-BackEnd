package com.letsshop.services.impl;

import com.letsshop.dto.LoginDTO;
import com.letsshop.dto.UserDTO;
import com.letsshop.entities.User;
import com.letsshop.repository.UserRepository;
import com.letsshop.services.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void registerUser(UserDTO userDTO) {
        // Validation checks
        // Check if the username already exists in the database
//        if (userRepository.existsByUsername(userDTO.getUsername())) {
//            throw new IllegalArgumentException("Username already taken.");
//        }

        // Check if the email already exists in the database
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already registered.");
        }

        // Validate that passwords match
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        // Map DTO to Entity
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));  // Hash the password before saving
        user.setEmail(userDTO.getEmail());

        // Save to the repository
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Handle any DB constraint violations
            throw new IllegalArgumentException("An error occurred while saving the user.");
        }
    }

    @Override
    public boolean loginUser(LoginDTO loginDTO) {
        // Check if the username or email exists in the database
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByEmail(loginDTO.getEmail());
        }

        if (userOptional.isEmpty()) {
            return false;  // Username or email not found
        }

        // Get the user from the optional
        User user = userOptional.get();

        // Compare the entered password with the hashed password in the database
        return passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
    }
}
