package com.example.rebornx30rbrespawntime.init;

import com.example.rebornx30rbrespawntime.constants.GlobalConstants;
import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import com.example.rebornx30rbrespawntime.service.DriverServiceImpl;
import com.example.rebornx30rbrespawntime.service.RaidBossService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.rebornx30rbrespawntime.constants.GlobalConstants.SITE_URL;

@Component
public class AppInit implements CommandLineRunner {
    private final RaidBossService raidBossService;
    private final DriverServiceImpl driverService;
    private final WebDriver driver;


    public AppInit(RaidBossService raidBossService, DriverServiceImpl driverService, WebDriver driver) {
        this.raidBossService = raidBossService;
        this.driverService = driverService;
        this.driver = driver;
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Scheduled(fixedRate = 300000)
    public void update() {
        driver.get(SITE_URL);
        Document doc = Jsoup.parse(driver.getPageSource());
        List<RaidBoss> raidBosses = driverService.parseHTMLIntoRBInfo(doc);
        driverService.setTimeOfUpdate(LocalDateTime.now());
        raidBossService.updateInfo(raidBosses);

        System.out.println("5 min passed");
        Toolkit.getDefaultToolkit().beep();
        System.out.print("\007");
        System.out.flush();
    }
}
