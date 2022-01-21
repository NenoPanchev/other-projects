package com.example.rebornx30rbrespawntime.web;

import com.example.rebornx30rbrespawntime.service.RaidBossService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

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

    @PostMapping("/addToD/{id}")
    public String addToD(@PathVariable("id") Long id) {

        LocalDateTime timeOfDeath = LocalDateTime.now();
        raidBossService.updateTimeOfDeath(id, timeOfDeath);
        return "redirect:/";
    }
}
