package com.springproject.connect_me_server;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    List<User> findByUserNameContainingIgnoreCase(String keyword);

}
