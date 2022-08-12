package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import com.example.rebornx30rbrespawntime.model.view.RaidBossViewModel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface RaidBossService {
    void seedRaidBoss(RaidBoss raidBoss);
    List<RaidBossViewModel> getAllRaidBosses();

    void updateInfo() throws UnsupportedAudioFileException, LineUnavailableException, IOException;

    void updateTimeOfDeath(String name, LocalDateTime timeOfDeath);
}
