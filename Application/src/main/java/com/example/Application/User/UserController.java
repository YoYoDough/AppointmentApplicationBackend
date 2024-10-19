package com.example.Application.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:3000")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }



    @GetMapping("/exists")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        Optional<User> existingUser = userService.findUserByEmail(email);

        // If the user is found, return the user object
        if (existingUser.isPresent()) {
            return ResponseEntity.ok(existingUser.get());
        }
        // If not found, return a 404 Not Found status
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
    }

    @PostMapping
    public ResponseEntity<String> registerNewUser(@RequestBody User user){
        // Check if the user already exists
        Optional<User> existingUser = userService.findUserByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            // User already exists, do not register and return a message
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists!"); // Return 409 Conflict
        } else {
            // User does not exist, register them
            userService.addNewUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully."); // Return 201 Created
        }
    }
}
