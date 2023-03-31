package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.UserQuotes;
import com.example.lazier.persist.entity.module.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuotesRepository extends JpaRepository<UserQuotes, Long> {

    boolean existsByLazierUser(LazierUser lazierUser);

    Optional<UserQuotes> findByLazierUser(LazierUser lazierUser);
}
