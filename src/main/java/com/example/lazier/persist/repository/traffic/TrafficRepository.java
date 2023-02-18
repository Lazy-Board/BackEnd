package com.example.lazier.persist.repository.traffic;

import com.example.lazier.persist.entity.traffic.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficRepository extends JpaRepository<Traffic, String> {

}
