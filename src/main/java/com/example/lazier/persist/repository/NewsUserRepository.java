package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.NewsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsUserRepository extends JpaRepository<NewsUser, String> {


}
