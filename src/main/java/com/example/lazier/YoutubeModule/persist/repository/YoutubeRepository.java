package com.example.lazier.YoutubeModule.persist.repository;

import com.example.lazier.YoutubeModule.persist.entity.YoutubeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YoutubeRepository extends JpaRepository<YoutubeEntity, String> {

}
