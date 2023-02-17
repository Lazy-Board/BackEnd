package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.UserQuotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuotesRepository extends JpaRepository<UserQuotes, String> {

}
