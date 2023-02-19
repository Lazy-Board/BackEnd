package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotesRepository extends JpaRepository<Quotes, Long> {

}
