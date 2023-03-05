package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.entity.module.UpdateExchange;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateExchangeRepository extends JpaRepository<UpdateExchange, Long> {

    Optional<UpdateExchange> findByLazierUser(LazierUser lazierUser);

    boolean existsByLazierUser(LazierUser lazierUser);

}
