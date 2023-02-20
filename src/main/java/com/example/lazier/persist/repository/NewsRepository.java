package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {


}
