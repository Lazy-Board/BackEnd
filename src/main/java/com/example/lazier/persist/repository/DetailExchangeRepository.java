package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.DetailExchange;
import com.example.lazier.persist.entity.module.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailExchangeRepository extends JpaRepository<DetailExchange, Long> {

    Optional<DetailExchange> findByLazierUser(LazierUser lazierUser);

    boolean existsByLazierUser(LazierUser lazierUser);

}
