package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;

import java.util.List;

public interface RaidBossService {
    void seedRaidBosses(List<RaidBoss> raidBosses);
    List<RaidBoss> getAllRaidBosses();
}
