package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.constants.GlobalConstants;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@Component
public class AudioServiceImpl {


    public AudioServiceImpl() {
    }

    public void playSound() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File(GlobalConstants.SOUND_FILE_PATH);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
        clip.flush();
    }
}
