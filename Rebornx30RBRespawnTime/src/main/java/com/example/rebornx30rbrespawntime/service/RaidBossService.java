package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.model.view.RaidBossViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface RaidBossService {
    List<RaidBossViewModel> getAllRaidBosses();

    void exportRaidBosses() throws IOException;

    void initialSeedRaidBosses() throws FileNotFoundException;

    void update() throws IOException;
}
