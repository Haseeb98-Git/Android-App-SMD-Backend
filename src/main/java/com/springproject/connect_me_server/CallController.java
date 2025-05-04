package com.springproject.connect_me_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/calls")
public class CallController {
    
    @Value("${agora.appId:5163f926ecb843d99b5ca62c2527f5c2}")
    private String appId;
    
    // No need for certificate in testing mode
    
    @Autowired
    private CallRepository callRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Generate dummy token for testing (no authentication)
    @GetMapping("/token")
    public ResponseEntity<TokenResponse> generateToken(
            @RequestParam String channelName,
            @RequestParam String uid,
            @RequestParam(defaultValue = "1") int role) {
        
        // For testing: return a dummy token
        String dummyToken = "test_token_" + System.currentTimeMillis();
        return ResponseEntity.ok(new TokenResponse(dummyToken, channelName, uid));
    }
    
    // Initiate a call
    @PostMapping("/initiate")
    public ResponseEntity<CallResponse> initiateCall(@RequestBody CallRequest request) {
        // Validate users exist
        if (!userRepository.existsById(request.getCallerId()) || 
            !userRepository.existsById(request.getReceiverId())) {
            return ResponseEntity.badRequest().build();
        }
        
        // Check if there's already an active call between these users
        Optional<Call> existingCall = callRepository.findActiveCallBetweenUsers(
            request.getCallerId(), request.getReceiverId());
        
        Call call;
        if (existingCall.isPresent()) {
            // Update existing call
            call = existingCall.get();
            call.setStatus("pending");
            call.setActive(true);
        } else {
            // Create new call
            call = new Call(
                request.getCallerId(),
                request.getReceiverId(),
                request.getCallType(),
                request.getChannelName()
            );
        }
        
        call = callRepository.save(call);
        
        // Get user names
        Optional<User> callerOpt = userRepository.findById(request.getCallerId());
        Optional<User> receiverOpt = userRepository.findById(request.getReceiverId());
        
        String callerName = callerOpt.map(User::getUserName).orElse("");
        String receiverName = receiverOpt.map(User::getUserName).orElse("");
        
        // Return empty token for testing (client will generate token as needed)
        String dummyToken = "";
        return ResponseEntity.ok(new CallResponse(
            call.getCallId(),
            call.getChannelName(),
            dummyToken,
            call.getCallType(),
            call.getCallerId(),
            call.getReceiverId(),
            callerName,
            receiverName,
            call.getStatus(),
            true
        ));
    }
    
    // Accept a call
    @PostMapping("/{callId}/accept")
    public ResponseEntity<CallResponse> acceptCall(@PathVariable String callId) {
        Optional<Call> callOpt = callRepository.findById(callId);
        
        if (callOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Call call = callOpt.get();
        call.acceptCall(); // Set status to "ongoing"
        call = callRepository.save(call);
        
        // Get user names
        Optional<User> callerOpt = userRepository.findById(call.getCallerId());
        Optional<User> receiverOpt = userRepository.findById(call.getReceiverId());
        
        String callerName = callerOpt.map(User::getUserName).orElse("");
        String receiverName = receiverOpt.map(User::getUserName).orElse("");
        
        // Return call details with token
        String dummyToken = "test_token_" + System.currentTimeMillis();
        return ResponseEntity.ok(new CallResponse(
            call.getCallId(),
            call.getChannelName(),
            dummyToken,
            call.getCallType(),
            call.getCallerId(),
            call.getReceiverId(),
            callerName,
            receiverName,
            call.getStatus(),
            true
        ));
    }
    
    // End a call
    @PostMapping("/{callId}/end")
    public ResponseEntity<CallResponse> endCall(@PathVariable String callId) {
        Optional<Call> callOpt = callRepository.findById(callId);
        
        if (callOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Call call = callOpt.get();
        call.endCall(); // Set status to "off" and active to false
        call = callRepository.save(call);
        
        // Get user names
        Optional<User> callerOpt = userRepository.findById(call.getCallerId());
        Optional<User> receiverOpt = userRepository.findById(call.getReceiverId());
        
        String callerName = callerOpt.map(User::getUserName).orElse("");
        String receiverName = receiverOpt.map(User::getUserName).orElse("");
        
        return ResponseEntity.ok(new CallResponse(
            call.getCallId(),
            call.getChannelName(),
            "",
            call.getCallType(),
            call.getCallerId(),
            call.getReceiverId(),
            callerName,
            receiverName,
            call.getStatus(),
            false
        ));
    }
    
    // Check call status between two users
    @GetMapping("/status")
    public ResponseEntity<CallResponse> checkCallStatus(
            @RequestParam String user1Id,
            @RequestParam String user2Id) {
        
        Optional<Call> callOpt = callRepository.findActiveCallBetweenUsers(user1Id, user2Id);
        
        if (callOpt.isEmpty()) {
            return ResponseEntity.ok(new CallResponse("", "", "", "", "", "", "", "", "off", false));
        }
        
        Call call = callOpt.get();
        
        // Get user names
        Optional<User> callerOpt = userRepository.findById(call.getCallerId());
        Optional<User> receiverOpt = userRepository.findById(call.getReceiverId());
        
        String callerName = callerOpt.map(User::getUserName).orElse("");
        String receiverName = receiverOpt.map(User::getUserName).orElse("");
        
        return ResponseEntity.ok(new CallResponse(
            call.getCallId(),
            call.getChannelName(),
            "",
            call.getCallType(),
            call.getCallerId(),
            call.getReceiverId(),
            callerName,
            receiverName,
            call.getStatus(),
            call.isActive()
        ));
    }
    
    // Get pending calls for a user
    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<Object> getPendingCalls(@PathVariable String userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.badRequest().build();
        }
        
        // Find calls where user is the receiver and status is pending
        var pendingCalls = callRepository.findByReceiverIdAndStatus(userId, "pending");
        
        return ResponseEntity.ok(pendingCalls);
    }
} 