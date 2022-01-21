package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import com.example.rebornx30rbrespawntime.repository.RaidBossRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.rebornx30rbrespawntime.constants.GlobalConstants.*;

@Service
public class RaidBossServiceImpl implements RaidBossService {
    private final RaidBossRepository raidBossRepository;
    private final DriverServiceImpl driverService;

    public RaidBossServiceImpl(RaidBossRepository raidBossRepository, DriverServiceImpl driverService) {
        this.raidBossRepository = raidBossRepository;
        this.driverService = driverService;
    }

    @Override
    public void seedRaidBoss(RaidBoss rb) {
        if (SUB_AND_ALLY_BOSSES.contains(rb.getName())) {
            rb.setRespawnTime(8L);
        } else if (rb.getName().equals(BARAKIEL)) {
            rb.setRespawnTime(6L);
        }
        raidBossRepository.save(rb);
    }

    @Override
    public List<RaidBoss> getAllRaidBosses() {
        return raidBossRepository.findAllByOrderByRespawnEnd();
    }

    @Override
    public void updateInfo(List<RaidBoss> raidBosses) {
        for (RaidBoss rb : raidBosses) {
            if (!raidBossRepository.existsByName(rb.getName())) {
                seedRaidBoss(rb);
                continue;
            }

            RaidBoss rbEntity = raidBossRepository.findByName(rb.getName()).orElseThrow();

            if (rbEntity.getRespawnStart() != rb.getRespawnStart()) {
                Duration duration = Duration.between(rbEntity.getRespawnStart(), rb.getRespawnStart());
                if (duration.toHours() > rbEntity.getRespawnTime() + 1) {
                    rbEntity.setRespawnStart(rb.getRespawnStart());
                    rbEntity.setRespawnEnd(rb.getRespawnEnd());
                    raidBossRepository.save(rbEntity);
                } else {
                    setRespawnByTimeOfDeath(rbEntity, rb, driverService.getTimeOfUpdate());
                }
            }
        }
    }

    @Override
    public void updateTimeOfDeath(Long id, LocalDateTime timeOfDeath) {

    }

    private void setRespawnByTimeOfDeath(RaidBoss rbEntity, RaidBoss rbNewInfo, LocalDateTime timeOfDeath) {
        rbEntity.setTimeOfDeath(timeOfDeath);

        Long hoursDifference = Duration.between(timeOfDeath, rbEntity.getRespawnStart()).toHours();
        int minuteOfDeath = timeOfDeath.getMinute();
        if (hoursDifference == rbEntity.getRespawnTime()) {
            rbEntity.setRespawnStart(rbNewInfo.getRespawnStart().plusMinutes(minuteOfDeath));
            rbEntity.setRespawnEnd(rbNewInfo.getRespawnEnd());
        } else {
            rbEntity.setRespawnStart(rbNewInfo.getRespawnStart());
            rbEntity.setRespawnEnd(rbNewInfo.getRespawnStart().plusMinutes(minuteOfDeath));
        }
        raidBossRepository.save(rbEntity);
    }
}
