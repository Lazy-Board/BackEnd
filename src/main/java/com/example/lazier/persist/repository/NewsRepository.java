package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.News;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {

  List<News> findTop10ByPressIdOrderByUpdatedAtDesc(String pressId);
  List<News> findAllByUpdatedAtBefore(LocalDateTime localDateTime);



}
