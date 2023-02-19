package com.example.lazier.stockModule.persist.repository;

import com.example.lazier.stockModule.persist.entity.Stock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByStockNameOrderByUpdateAtDesc(String stockName);

}
