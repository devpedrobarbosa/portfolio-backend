package dev.pedrao.portfolio_api.service;

import dev.pedrao.portfolio_api.model.User;
import dev.pedrao.portfolio_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
    * Save a new user
    *
    * @param user the user to save
    * @return the saved user
    */
    public User create(User user) {
        return userRepository.save(user);
    }

    /**
    * Find all users
    *
    * @return list of all users
    */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
    * Find a user by its ID
    *
    * @param id the ID to search for
    * @return the user if found, empty Optional otherwise
    */
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
    * Update an existing user
    *
    * @param id the ID of the user to update
    * @param updatedUser the updated user information
    * @return the updated user if found, null otherwise
    */
    public User update(String id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        
        if (existingUser.isPresent()) {
            updatedUser.setId(id);
            return userRepository.save(updatedUser);
        }
        
        return null;
    }

    /**
    * Delete a user by its ID
    *
    * @param id the ID of the user to delete
    */
    public void delete(String id) {
        userRepository.deleteById(id);
    }
}