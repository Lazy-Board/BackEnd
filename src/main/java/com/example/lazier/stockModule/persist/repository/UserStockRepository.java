package com.example.lazier.stockModule.persist.repository;

import com.example.lazier.stockModule.persist.entity.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock, String> {

}
