package com.example.lazier.QuotesModule.persist.repository;

import com.example.lazier.QuotesModule.persist.entity.UserQuotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuotesRepository extends JpaRepository<UserQuotes, String> {

}
