package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.traffic.Traffic;
import com.example.lazier.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficRepository extends JpaRepository<Traffic, Long> {

    boolean existsByLazierUser(LazierUser lazierUser);

    Optional<Traffic> findByLazierUser(LazierUser lazierUser);
}
