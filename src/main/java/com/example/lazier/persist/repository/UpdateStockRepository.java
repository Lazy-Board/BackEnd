package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.entity.module.UpdateStock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateStockRepository extends JpaRepository<UpdateStock, Long> {

    Optional<UpdateStock> findByLazierUser(LazierUser lazierUser);

    boolean existsByLazierUser(LazierUser lazierUser);

}
