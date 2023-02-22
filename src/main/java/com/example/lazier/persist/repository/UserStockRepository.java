package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.UserStock;
import com.example.lazier.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock, String> {

    Optional<UserStock> findByLazierUser(LazierUser lazierUser);

    boolean existsByLazierUser(LazierUser lazierUser);

}
