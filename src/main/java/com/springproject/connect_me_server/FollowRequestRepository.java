package com.springproject.connect_me_server;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FollowRequestRepository extends JpaRepository<FollowRequest, String> {
    List<FollowRequest> findByFollowedIdAndStatus(String followedId, FollowRequest.RequestStatus status);
    List<FollowRequest> findByFollowerIdAndStatus(String followerId, FollowRequest.RequestStatus status);
    boolean existsByFollowerIdAndFollowedIdAndStatus(String followerId, String followedId, FollowRequest.RequestStatus status);
} 