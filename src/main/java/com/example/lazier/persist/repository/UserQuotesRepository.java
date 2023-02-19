package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.UserQuotes;
import com.example.lazier.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuotesRepository extends JpaRepository<UserQuotes, String> {

    boolean existsByLazierUser(LazierUser lazierUser);

    Optional<UserQuotes> findByLazierUser(LazierUser lazierUser);
}
