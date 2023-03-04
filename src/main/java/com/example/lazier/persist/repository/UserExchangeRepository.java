package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.UserExchange;
import com.example.lazier.persist.entity.module.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExchangeRepository extends JpaRepository<UserExchange, String> {

    Optional<UserExchange> findByLazierUser(LazierUser lazierUser);

    boolean existsByLazierUser(LazierUser lazierUser);

}
