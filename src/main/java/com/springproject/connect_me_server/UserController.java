package com.springproject.connect_me_server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository userRepository;
    private final FollowRequestRepository followRequestRepository;

    public UserController(UserRepository userRepository, FollowRequestRepository followRequestRepository) {
        this.userRepository = userRepository;
        this.followRequestRepository = followRequestRepository;
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
    
    // New endpoints for follow functionality
    
    @PostMapping("/follow")
    public ResponseEntity<?> requestFollow(@RequestBody FollowRequestDTO requestDTO) {
        // Validate users exist
        Optional<User> followerOpt = userRepository.findById(requestDTO.getFollowerId());
        Optional<User> followedOpt = userRepository.findById(requestDTO.getFollowedId());
        
        if (followerOpt.isEmpty() || followedOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid follower or followed user IDs.");
        }
        
        // Check if request already exists
        boolean existingRequest = followRequestRepository.existsByFollowerIdAndFollowedIdAndStatus(
            requestDTO.getFollowerId(), 
            requestDTO.getFollowedId(),
            FollowRequest.RequestStatus.PENDING
        );
        
        if (existingRequest) {
            return ResponseEntity.badRequest().body("Follow request already sent.");
        }
        
        // Create new follow request
        FollowRequest followRequest = new FollowRequest();
        followRequest.setFollowerId(requestDTO.getFollowerId());
        followRequest.setFollowedId(requestDTO.getFollowedId());
        
        followRequestRepository.save(followRequest);
        
        return ResponseEntity.ok("Follow request sent successfully.");
    }
    
    @GetMapping("/{userId}/pending-requests")
    public ResponseEntity<List<FollowRequestResponse>> getPendingRequests(@PathVariable String userId) {
        List<FollowRequest> pendingRequests = followRequestRepository.findByFollowedIdAndStatus(
            userId, FollowRequest.RequestStatus.PENDING
        );
        
        List<FollowRequestResponse> responses = new ArrayList<>();
        
        for (FollowRequest request : pendingRequests) {
            Optional<User> followerOpt = userRepository.findById(request.getFollowerId());
            Optional<User> followedOpt = userRepository.findById(request.getFollowedId());
            
            if (followerOpt.isPresent() && followedOpt.isPresent()) {
                responses.add(new FollowRequestResponse(request, followerOpt.get(), followedOpt.get()));
            }
        }
        
        return ResponseEntity.ok(responses);
    }
    
    @PostMapping("/follow/{requestId}/respond")
    public ResponseEntity<?> respondToFollowRequest(
            @PathVariable String requestId,
            @RequestParam("accept") boolean accept) {
        
        Optional<FollowRequest> requestOpt = followRequestRepository.findById(requestId);
        
        if (requestOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Follow request not found.");
        }
        
        FollowRequest request = requestOpt.get();
        
        // Only respond to pending requests
        if (request.getStatus() != FollowRequest.RequestStatus.PENDING) {
            return ResponseEntity.badRequest().body("Request already processed.");
        }
        
        if (accept) {
            request.setStatus(FollowRequest.RequestStatus.ACCEPTED);
            
            // Update follower and following relationships
            Optional<User> followerOpt = userRepository.findById(request.getFollowerId());
            Optional<User> followedOpt = userRepository.findById(request.getFollowedId());
            
            if (followerOpt.isPresent() && followedOpt.isPresent()) {
                User follower = followerOpt.get();
                User followed = followedOpt.get();
                
                // Update following for follower
                follower.addFollowing(followed.getUserId());
                userRepository.save(follower);
                
                // Update followers for followed
                followed.addFollower(follower.getUserId());
                userRepository.save(followed);
            }
        } else {
            request.setStatus(FollowRequest.RequestStatus.REJECTED);
        }
        
        followRequestRepository.save(request);
        
        return ResponseEntity.ok(accept ? "Follow request accepted." : "Follow request rejected.");
    }
    
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        
        User user = userOpt.get();
        List<User> followers = user.getFollowers().stream()
                .map(id -> userRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(followers);
    }
    
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<User>> getFollowing(@PathVariable String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        
        User user = userOpt.get();
        List<User> following = user.getFollowing().stream()
                .map(id -> userRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(following);
    }
}


