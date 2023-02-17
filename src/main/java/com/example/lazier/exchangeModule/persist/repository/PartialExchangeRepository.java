package com.example.lazier.exchangeModule.persist.repository;

import com.example.lazier.exchangeModule.persist.entity.PartialExchange;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartialExchangeRepository extends JpaRepository<PartialExchange, Long> {

    Optional<List<PartialExchange>> findByUserId(String userId);

    /**
     * 환율 정보 삭제
     */
    void deleteByUserId(String userId);

}
