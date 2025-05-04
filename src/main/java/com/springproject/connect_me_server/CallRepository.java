package com.springproject.connect_me_server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CallRepository extends JpaRepository<Call, String> {
    List<Call> findByCallerIdAndActiveTrue(String callerId);
    List<Call> findByReceiverIdAndActiveTrue(String receiverId);
    List<Call> findByChannelName(String channelName);
    
    // Find active call between two users (regardless of who is caller or receiver)
    @Query("SELECT c FROM Call c WHERE " +
           "((c.callerId = :user1Id AND c.receiverId = :user2Id) OR " +
           "(c.callerId = :user2Id AND c.receiverId = :user1Id)) AND " +
           "c.active = true")
    Optional<Call> findActiveCallBetweenUsers(@Param("user1Id") String user1Id, 
                                             @Param("user2Id") String user2Id);
    
    // Find call by status and users
    @Query("SELECT c FROM Call c WHERE " +
           "((c.callerId = :user1Id AND c.receiverId = :user2Id) OR " +
           "(c.callerId = :user2Id AND c.receiverId = :user1Id)) AND " +
           "c.status = :status")
    Optional<Call> findCallByStatusBetweenUsers(@Param("user1Id") String user1Id, 
                                               @Param("user2Id") String user2Id,
                                               @Param("status") String status);
    
    // Find pending calls for a user (where user is receiver)
    List<Call> findByReceiverIdAndStatus(String receiverId, String status);
} 