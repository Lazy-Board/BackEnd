package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.UpdateExchange;
import com.example.lazier.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateExchangeRepository extends JpaRepository<UpdateExchange, Long> {

    Optional<UpdateExchange> findByLazierUser(LazierUser lazierUser);

    boolean existsByLazierUser(LazierUser lazierUser);

}
