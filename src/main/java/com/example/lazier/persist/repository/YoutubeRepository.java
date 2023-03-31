package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.Youtube;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutubeRepository extends JpaRepository<Youtube, String> {

  List<Youtube> findTop3ByOrderByCreatedAtDesc();

  List<Youtube> findAllByCreatedAtBefore(LocalDateTime dateTime);

}
