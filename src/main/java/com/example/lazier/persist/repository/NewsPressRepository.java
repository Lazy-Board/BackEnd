package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.NewsPress;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsPressRepository extends JpaRepository<NewsPress, String> {

  List<NewsPress> findFirstByOrderByPressIdAsc();

  Optional<NewsPress> findByPressName(String pressName);


}
