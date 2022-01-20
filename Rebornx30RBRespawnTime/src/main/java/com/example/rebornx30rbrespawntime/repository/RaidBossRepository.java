package com.example.rebornx30rbrespawntime.repository;

import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaidBossRepository extends JpaRepository<RaidBoss, Long> {
    List<RaidBoss> findAllByOrderByRespawnEndDesc();
}
