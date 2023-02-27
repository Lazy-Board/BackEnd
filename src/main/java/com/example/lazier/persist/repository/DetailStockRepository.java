package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.DetailStock;
import com.example.lazier.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailStockRepository extends JpaRepository<DetailStock, String> {

    Optional<DetailStock> findByLazierUser(LazierUser lazierUser);

    boolean existsByLazierUser(LazierUser lazierUser);

}
