package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.Exchange;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    Optional<Exchange> findByCurrencyNameOrderByUpdateAtDescCountryNameDesc(String currencyName);

}
