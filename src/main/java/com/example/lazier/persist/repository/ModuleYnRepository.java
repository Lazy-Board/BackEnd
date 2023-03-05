package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.ModuleYn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModuleYnRepository extends JpaRepository<ModuleYn, Long> {

	@Query("select my from ModuleYn my where my.lazierUser.userId = :userId")
	ModuleYn findAllByUserId(@Param("userId") Long userId);
}
