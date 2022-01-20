package com.example.rebornx30rbrespawntime.web;

import com.example.rebornx30rbrespawntime.service.RaidBossService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
