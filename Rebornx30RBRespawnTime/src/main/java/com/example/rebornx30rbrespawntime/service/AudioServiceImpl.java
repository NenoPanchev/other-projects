package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.constants.GlobalConstants;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@Component
public class AudioServiceImpl {
    private final Clip clip = AudioSystem.getClip();


    public AudioServiceImpl() throws LineUnavailableException {
    }

    public void openSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File file = new File(GlobalConstants.SOUND_FILE_PATH);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
        clip.open(audioIn);
    }

    public void playSound() {
        clip.start();
        clip.setFramePosition(0);
        clip.flush();
    }

}
