package com.example.lazier.exchangeModule.persist.repository;

import com.example.lazier.exchangeModule.persist.entity.Exchange;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    /**
     * 환율 정보 조회 (10개 통화의 10가지 정보) - 사용자 조건으로
     */
    Optional<List<Exchange>> findByUserId(String userId);

    /**
     * 통화별 상세정보 조회 (ex. 미국 통화명의 10가지 정보 조회) - 사용자, 통화명 조건으로
     */
    List<Exchange> findExchangesByUserIdAndCurrencyName(String userId, String currencyName);

    /**
     * 환율 정보 삭제
     */
    void deleteByUserId(String userId);



}
