package com.example.lazier.exchangeModule.persist.repository;

import com.example.lazier.exchangeModule.persist.entity.UserExchange;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExchangeRepository extends JpaRepository<UserExchange, String> {
    @Override
    Optional<UserExchange> findById(String userId);

}
