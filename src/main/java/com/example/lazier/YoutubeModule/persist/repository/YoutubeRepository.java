package com.example.lazier.YoutubeModule.persist.repository;

import com.example.lazier.YoutubeModule.persist.entity.YoutubeEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutubeRepository extends JpaRepository<YoutubeEntity, String> {

  List<YoutubeEntity> findTop3ByOrderByCreatedAtDesc();

  List<YoutubeEntity> findAllByCreatedAtBefore(LocalDateTime dateTime);

}
