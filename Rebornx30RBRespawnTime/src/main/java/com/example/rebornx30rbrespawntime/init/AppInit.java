package com.example.rebornx30rbrespawntime.init;

import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import com.example.rebornx30rbrespawntime.service.DriverServiceImpl;
import com.example.rebornx30rbrespawntime.service.RaidBossService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppInit implements CommandLineRunner {
    public static final String SITE_URL = "https://seasons.l2reborn.org/raidbosses-map";
    private final RaidBossService raidBossService;
    private final DriverServiceImpl driverService;


    public AppInit(RaidBossService raidBossService, DriverServiceImpl driverService) {
        this.raidBossService = raidBossService;
        this.driverService = driverService;

    }

    @Override
    public void run(String... args) throws Exception {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/files/geckodriver.exe");
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(firefoxBinary);
        options.setHeadless(true);
        WebDriver driver = new FirefoxDriver(options);
        driver.get(SITE_URL);
        Document doc = Jsoup.parse(driver.getPageSource());
        List<RaidBoss> raidBosses = driverService.parseHTMLIntoRBInfo(doc);

        raidBossService.seedRaidBosses(raidBosses);



    }

//    @Scheduled(cron = "${refresh-cron}")
//    public void update() {
//        driver.get(SITE_URL);
//        Document doc = Jsoup.parse(driver.getPageSource());
//        List<RaidBoss> raidBosses = driverService.parseHTMLIntoRBInfo(doc);
//        System.out.println("5 min passed");
//        raidBossService.seedRaidBosses(raidBosses);
//    }
}
