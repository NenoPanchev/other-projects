package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.constants.GlobalConstants;
import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.rebornx30rbrespawntime.constants.GlobalConstants.EXCLUDED_RAIDBOSSES;
import static com.example.rebornx30rbrespawntime.constants.GlobalConstants.LEVEL_BRACKET;

@Component
public class DriverServiceImpl {
    public DriverServiceImpl() {
    }

    public List<RaidBoss> parseHTMLIntoRBInfo(Document doc) {
        Elements bossInfo = doc.getElementsByClass("middle");
        List<RaidBoss> raidBosses = new ArrayList<>();
        for (Element boss : bossInfo) {
            String name = boss.select("u").first().text();

            List<TextNode> respawns = boss.children().get(5).textNodes();

            boolean alive = respawns.size() == 0;

            String level = boss.textNodes().get(2).text();
            Integer levelNumber = Integer.parseInt(Arrays.stream(level.split(": ")).skip(1).findFirst().orElse("0"));
            LocalDateTime respawnLocal = null;
            LocalDateTime respawnEnd = null;


            if (!alive) {
                String timeString = respawns.get(1).text().replace("**", "00").replace(" UTC", "");
                String startString = Arrays.stream(timeString.split(" - ")).findFirst().orElse("");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime respawnStartUCT = LocalDateTime.parse(startString, formatter);
                respawnLocal = respawnStartUCT.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                respawnEnd = respawnLocal.plusHours(1);
            }
            if (levelNumber >= LEVEL_BRACKET && nameNotIn(name)) {
                if (name.equals(GlobalConstants.BARAKIEL) && respawnLocal != null) {
                    respawnEnd = respawnLocal.plusMinutes(30);
                }

                RaidBoss rb = new RaidBoss()
                        .setLevel(levelNumber)
                        .setName(name)
                        .setAlive(alive)
                        .setRespawnStart(respawnLocal)
                        .setRespawnEnd(respawnEnd);

                raidBosses.add(rb);
            }
        }

        return raidBosses;
    }

    private boolean nameNotIn(String name) {
        return !EXCLUDED_RAIDBOSSES.contains(name);
    }

}
