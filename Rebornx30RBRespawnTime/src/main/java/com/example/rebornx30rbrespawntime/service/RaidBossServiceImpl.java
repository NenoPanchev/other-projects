package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import com.example.rebornx30rbrespawntime.repository.RaidBossRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaidBossServiceImpl implements RaidBossService{
    private final RaidBossRepository raidBossRepository;

    public RaidBossServiceImpl(RaidBossRepository raidBossRepository) {
        this.raidBossRepository = raidBossRepository;
    }

    @Override
    public void seedRaidBosses(List<RaidBoss> raidBosses) {
        if (raidBossRepository.count() == 0) {
            raidBossRepository.saveAll(raidBosses);
        }
    }

    @Override
    public List<RaidBoss> getAllRaidBosses() {
        return raidBossRepository.findAllByOrderByRespawnEndDesc();
    }
}
