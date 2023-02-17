package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.user.LazierUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<LazierUser, Long> {

    boolean existsByUserEmail(String userEmail);
    Optional<LazierUser> findByEmailAuthKey(String uuid);
    Optional<LazierUser> findByUserEmail(String useEmail);
    Optional<LazierUser> findByUserId(Long username);
    Optional<LazierUser> findByOauthId(String oauthId);
}

