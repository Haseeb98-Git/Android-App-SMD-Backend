package com.springproject.connect_me_server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use.");
        }

        User user = new User();
        user.setUserId(request.getUserId() != null ? request.getUserId() : UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setFullName(request.getFullName());
        user.setProfilePicture(request.getProfilePicture());
        user.setPassword(request.getPassword());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
        return ResponseEntity.ok(user); // Or return a DTO/token
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String query) {
        return userRepository.findByUserNameContainingIgnoreCase(query);
    }

}


