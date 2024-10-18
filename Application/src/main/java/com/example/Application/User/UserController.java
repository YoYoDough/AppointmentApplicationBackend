package com.example.Application.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
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



    @GetMapping("/email")
    public ResponseEntity<String> getUserByEmail(@RequestParam String email, User user) {
        Optional<String> existingEmail = userService.findEmailByEmail(user.getEmail());
        if (existingEmail.isPresent()) {
            return ResponseEntity.ok(existingEmail.get()); // Return the email if found
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!"); // Return 404 if not found
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
