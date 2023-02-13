package com.example.lazier.QuotesModule.persist.repository;

import com.example.lazier.QuotesModule.persist.entity.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotesRepository extends JpaRepository<Quotes, Long> {

}
