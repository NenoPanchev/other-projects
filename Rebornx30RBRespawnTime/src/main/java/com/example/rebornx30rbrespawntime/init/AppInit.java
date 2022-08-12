package com.example.rebornx30rbrespawntime.init;

import com.example.rebornx30rbrespawntime.service.AudioServiceImpl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {
    private final AudioServiceImpl audioService;


    public AppInit(AudioServiceImpl audioService) {

        this.audioService = audioService;
    }

    @Override
    public void run(String... args) throws Exception {
        audioService.openSound();
    }

}
