package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.Stock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByStockNameOrderByUpdateAtDesc(String stockName);

}
