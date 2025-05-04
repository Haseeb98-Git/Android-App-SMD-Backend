package com.springproject.connect_me_server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, String> {
    List<Call> findByCallerIdAndActiveTrue(String callerId);
    List<Call> findByReceiverIdAndActiveTrue(String receiverId);
    List<Call> findByChannelName(String channelName);
} 