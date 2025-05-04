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

    // Initiate a call - simplified for testing
    @PostMapping("/initiate")
    public ResponseEntity<CallResponse> initiateCall(@RequestBody CallRequest request) {
        // Validate users exist
        if (!userRepository.existsById(request.getCallerId()) ||
                !userRepository.existsById(request.getReceiverId())) {
            return ResponseEntity.badRequest().build();
        }

        // Create new call
        Call call = new Call(
                request.getCallerId(),
                request.getReceiverId(),
                request.getCallType(),
                request.getChannelName()
        );

        call = callRepository.save(call);

        // Return empty token for testing (client will use null token)
        String dummyToken = "";
        return ResponseEntity.ok(CallResponse.fromEntity(call, dummyToken));
    }

    // End a call
    @PostMapping("/{callId}/end")
    public ResponseEntity<String> endCall(@PathVariable String callId) {
        Optional<Call> callOpt = callRepository.findById(callId);

        if (callOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Call call = callOpt.get();
        call.endCall();
        callRepository.save(call);

        return ResponseEntity.ok("Call ended");
    }

    // Get active calls for a user
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<Object> getActiveCalls(@PathVariable String userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.badRequest().build();
        }

        // Find calls where user is either caller or receiver
        var callerCalls = callRepository.findByCallerIdAndActiveTrue(userId);
        var receiverCalls = callRepository.findByReceiverIdAndActiveTrue(userId);

        // Combine the lists
        callerCalls.addAll(receiverCalls);

        return ResponseEntity.ok(callerCalls);
    }
} 