package com.example.rebornx30rbrespawntime.init;

import com.example.rebornx30rbrespawntime.service.RaidBossService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {
    private final RaidBossService raidBossService;


    public AppInit(RaidBossService raidBossService) {
        this.raidBossService = raidBossService;
    }

    @Override
    public void run(String... args) throws Exception {
//        raidBossService.exportRaidBosses();
        raidBossService.initialSeedRaidBosses();
    }

}
