package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import com.example.rebornx30rbrespawntime.model.view.RaidBossViewModel;
import com.example.rebornx30rbrespawntime.repository.RaidBossRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.rebornx30rbrespawntime.constants.GlobalConstants.*;

@Service
public class RaidBossServiceImpl implements RaidBossService {
    private final RaidBossRepository raidBossRepository;
    private final DriverServiceImpl driverService;
    private final ModelMapper modelMapper;

    public RaidBossServiceImpl(RaidBossRepository raidBossRepository, DriverServiceImpl driverService, ModelMapper modelMapper) {
        this.raidBossRepository = raidBossRepository;
        this.driverService = driverService;
        this.modelMapper = modelMapper;
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
    public List<RaidBossViewModel> getAllRaidBosses() {
        return raidBossRepository
                .findAllByOrderByRespawnEnd()
                .stream()
                .map(entity -> modelMapper.map(entity, RaidBossViewModel.class)
                .setRespawnStart(getTimeFrom(entity.getRespawnStart()))
                .setRespawnEnd(getTimeFrom(entity.getRespawnEnd()))
                .setTimeOfDeath(entity.getTimeOfDeath() == null ? "" : getTimeFrom(entity.getTimeOfDeath())))
                .collect(Collectors.toList());
    }

    private String getTimeFrom(LocalDateTime localDateTime) {
        String[] str = localDateTime.toString().split("T");
        return str[1].substring(0, 5);
    }

    @Override
    public void updateInfo(List<RaidBoss> raidBosses) {
        for (RaidBoss rb : raidBosses) {
            if (!raidBossRepository.existsByName(rb.getName())) {
                seedRaidBoss(rb);
                continue;
            }

            RaidBoss rbEntity = raidBossRepository.findByName(rb.getName()).orElseThrow();

            if (rb.isAlive()) {
                rbEntity.setRespawnStart(null)
                        .setRespawnEnd(null)
                        .setTimeOfDeath(null)
                        .setAlive(true);
                raidBossRepository.save(rbEntity);
                continue;
            }

            Long entityHour = Long.valueOf(rbEntity.getRespawnStart() == null ? 0 : rbEntity.getRespawnStart().getHour());
            Long hoursDifference = Duration.between(rb.getRespawnStart() - entityHour));
            if (hoursDifference != 0) {
                rbEntity.setAlive(false);
                if (hoursDifference > rbEntity.getRespawnTime() + 1) {
                    rbEntity.setRespawnStart(rb.getRespawnStart());
                    rbEntity.setRespawnEnd(rb.getRespawnEnd());
                    rbEntity.setTimeOfDeath(null);
                    raidBossRepository.save(rbEntity);
                } else {
                    setRespawnByTimeOfDeath(rbEntity, rb, driverService.getTimeOfUpdate());
                }
            }
        }
    }

    @Override
    public void updateTimeOfDeath(String name, LocalDateTime timeOfDeath) {
        RaidBoss rbEntity = raidBossRepository.findByName(name).orElseThrow();
        rbEntity.setTimeOfDeath(timeOfDeath);
        Long hoursDifference = Duration.between(timeOfDeath, rbEntity.getRespawnStart()).toHours();

        int minuteOfDeath = timeOfDeath.getMinute();
        if (hoursDifference <= rbEntity.getRespawnTime()) {
            rbEntity.setRespawnStart(rbEntity.getRespawnStart().plusMinutes(minuteOfDeath));
        } else {
            rbEntity.setRespawnEnd(rbEntity.getRespawnStart().plusMinutes(minuteOfDeath));
        }
        raidBossRepository.save(rbEntity);
    }

    private void setRespawnByTimeOfDeath(RaidBoss rbEntity, RaidBoss rbNewInfo, LocalDateTime timeOfDeath) {
        rbEntity.setTimeOfDeath(timeOfDeath);

        Long hoursDifference = Duration.between(timeOfDeath, rbNewInfo.getRespawnStart()).toHours();
        int minuteOfDeath = timeOfDeath.getMinute();
        if (hoursDifference <= rbEntity.getRespawnTime()) {
            rbEntity.setRespawnStart(rbNewInfo.getRespawnStart().plusMinutes(minuteOfDeath));
            rbEntity.setRespawnEnd(rbNewInfo.getRespawnEnd());
        } else {
            rbEntity.setRespawnStart(rbNewInfo.getRespawnStart());
            rbEntity.setRespawnEnd(rbNewInfo.getRespawnStart().plusMinutes(minuteOfDeath));
        }
        raidBossRepository.save(rbEntity);
    }
}
