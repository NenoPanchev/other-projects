package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.constants.GlobalConstants;
import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import com.example.rebornx30rbrespawntime.model.view.RaidBossViewModel;
import com.example.rebornx30rbrespawntime.repository.RaidBossRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.rebornx30rbrespawntime.constants.GlobalConstants.*;

@Service
public class RaidBossServiceImpl implements RaidBossService {
    private final RaidBossRepository raidBossRepository;
    private final DriverServiceImpl driverService;
    private final ModelMapper modelMapper;
    private final AudioServiceImpl audioService;

    public RaidBossServiceImpl(RaidBossRepository raidBossRepository, DriverServiceImpl driverService, ModelMapper modelMapper, AudioServiceImpl audioService) {
        this.raidBossRepository = raidBossRepository;
        this.driverService = driverService;
        this.modelMapper = modelMapper;
        this.audioService = audioService;
    }

    @Override
    public void seedRaidBoss(RaidBoss rb) {
        if (SUB_AND_ALLY_BOSSES.contains(rb.getName())) {
            rb.setRespawnTime(8L);
        } else if (rb.getName().equals(BARAKIEL)) {
            rb.setRespawnTime(6L);
        }
        String rebornId = namesIDs.get(rb.getName());
        rb.setRebornID(rebornId)
                .setDropURL(String.format("https://interlude.wiki/db/npc/%s.html", rebornId))
                .setLocationURL(String.format("https://interlude.wiki/db/loc/%s.html", rebornId));
        raidBossRepository.save(rb);
    }

    @Override
    public List<RaidBossViewModel> getAllRaidBosses() {
        return raidBossRepository
                .findAllByOrderByRespawnEnd()
                .stream()
                .map(entity -> modelMapper.map(entity, RaidBossViewModel.class)
                .setRespawnStart(entity.getRespawnStart() == null ? "" : getTimeFrom(entity.getRespawnStart()))
                .setRespawnEnd(entity.getRespawnEnd() == null ? "" : Arrays.stream(getTimeFrom(entity.getRespawnEnd()).split(" - ")).skip(1).findFirst().orElse(""))
                        .setRespawnStartTime(entity.getRespawnStart() == null ? null : entity.getRespawnStart())
                .setTimeOfDeath(entity.getTimeOfDeath() == null ? "" : getTimeFrom(entity.getTimeOfDeath())))
                .collect(Collectors.toList());
    }

    private String getTimeFrom(LocalDateTime localDateTime) {
        String[] str = localDateTime.toString().split("T");
        String hour = str[1].substring(0, 5);
        String[] date = str[0].split("-");
        String day = date[2];
        String month = date[1];
        return String.format("%s.%s  -  %s", day, month, hour);
    }

    @Override
    public void updateInfo(List<RaidBoss> raidBosses) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
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
                audioService.playSound();
                continue;
            }

            if (rbEntity.isAlive() && !rb.isAlive()) {
                rbEntity.setRespawnStart(rb.getRespawnStart())
                        .setAlive(rb.isAlive())
                        .setRespawnEnd(rb.getRespawnEnd());
                raidBossRepository.save(rbEntity);

//                setRespawnByTimeOfDeath(rbEntity, rb, driverService.getTimeOfUpdate());
                continue;
            }

            Long hoursDifference = Math.abs(Duration.between(rb.getRespawnStart(), rbEntity.getRespawnStart()).toHours());
            if (hoursDifference != 0) {
                    rbEntity.setRespawnStart(rb.getRespawnStart());
                    rbEntity.setRespawnEnd(rb.getRespawnEnd());
                    rbEntity.setTimeOfDeath(null);
                    rbEntity.setAlive(false);
                    raidBossRepository.save(rbEntity);
            }
        }
    }

    @Override
    public void updateTimeOfDeath(String name, LocalDateTime timeOfDeath) {
        RaidBoss rbEntity = raidBossRepository.findByName(name).orElseThrow();
        rbEntity.setTimeOfDeath(timeOfDeath);
        Long hoursDifference = Duration.between(timeOfDeath, rbEntity.getRespawnStart()).toHours();

        int minuteOfDeath = timeOfDeath.getMinute();
        if (hoursDifference < rbEntity.getRespawnTime()) {
            rbEntity.setRespawnStart(rbEntity.getRespawnStart().plusMinutes(minuteOfDeath));
        } else {
            rbEntity.setRespawnEnd(rbEntity.getRespawnStart().plusMinutes(minuteOfDeath));
        }
        raidBossRepository.save(rbEntity);
    }

    private void setRespawnByTimeOfDeath(RaidBoss rbEntity, RaidBoss rbNewInfo, LocalDateTime timeOfDeath) {
        rbEntity.setTimeOfDeath(timeOfDeath);
        rbEntity.setAlive(false);

        Long hoursDifference = Duration.between(timeOfDeath, rbNewInfo.getRespawnStart()).toHours();
        int minuteOfDeath = timeOfDeath.getMinute();
        if (hoursDifference < rbEntity.getRespawnTime()) {
            rbEntity.setRespawnStart(rbNewInfo.getRespawnStart().plusMinutes(minuteOfDeath));
            rbEntity.setRespawnEnd(rbNewInfo.getRespawnEnd());
        } else {
            rbEntity.setRespawnStart(rbNewInfo.getRespawnStart());
            rbEntity.setRespawnEnd(rbNewInfo.getRespawnStart().plusMinutes(minuteOfDeath));
        }
        raidBossRepository.save(rbEntity);
    }
}
