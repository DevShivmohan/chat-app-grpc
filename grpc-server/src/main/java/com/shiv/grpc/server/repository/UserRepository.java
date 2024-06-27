package com.shiv.grpc.server.repository;

import com.shiv.grpc.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(nativeQuery = true, value = "select * from users where email=?1 OR username=?2")
    Optional<User> findByEmailOrUsername(String email, String username);
}
