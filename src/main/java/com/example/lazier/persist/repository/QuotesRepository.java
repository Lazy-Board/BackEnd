package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotesRepository extends JpaRepository<Quotes, Long> {

}
