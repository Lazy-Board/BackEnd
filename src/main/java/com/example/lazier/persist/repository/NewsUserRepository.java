package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.NewsUser;
import com.example.lazier.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsUserRepository extends JpaRepository<NewsUser, Long> {
  boolean existsByLazierUser (LazierUser lazierUser);

  Optional<NewsUser> findByLazierUser (LazierUser lazierUser);

}
