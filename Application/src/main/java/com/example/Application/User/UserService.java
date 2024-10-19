package com.example.Application.User;

import com.example.Application.User.User;
import com.example.Application.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    public Optional<String> findEmailByEmail(String email) {
        return userRepository.findEmailByEmail(email);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User addNewUser(User user) {
        // Save user directly
        return userRepository.save(user); // Return the saved user
    }
}
