package com.example.rebornx30rbrespawntime.web;

import com.example.rebornx30rbrespawntime.service.RaidBossService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {
    private final RaidBossService raidBossService;

    public HomeController(RaidBossService raidBossService) {
        this.raidBossService = raidBossService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("allBosses", raidBossService.getAllRaidBosses());
        return "index";
    }

    @GetMapping(value = "/add", params = {"bossName", "tod"})
    public String addToD(@RequestParam String bossName, @RequestParam String tod) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime timeOfDeath = LocalDateTime.parse(tod, dateTimeFormatter);
        raidBossService.updateTimeOfDeath(bossName, timeOfDeath);
        return "redirect:/";
    }
}
