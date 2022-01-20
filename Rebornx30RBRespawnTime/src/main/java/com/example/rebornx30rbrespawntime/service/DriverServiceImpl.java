package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DriverServiceImpl {
    private final RaidBossService raidBossService;


    public DriverServiceImpl(RaidBossService raidBossService) {
        this.raidBossService = raidBossService;
    }

    public List<RaidBoss> parseHTMLIntoRBInfo(Document doc) {
        Elements bossInfo = doc.getElementsByClass("middle");
        List<RaidBoss> raidBosses = new ArrayList<>();
        for (Element boss : bossInfo) {
            String name = boss.select("u").first().text();

            List<TextNode> respawns = boss.children().get(5).textNodes();
            boolean alive = respawns.size() == 0;

            String level = boss.textNodes().get(2).text();
            Integer levelNumber = Integer.parseInt(String.valueOf(Arrays.stream(level.split(": ")).skip(1).findFirst().orElse("0")));
            System.out.println();
            LocalDateTime localDateTime = null;
            if (!alive) {
                String timeString = respawns.get(1).text().replace("**", "00").replace(" UTC", "");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                localDateTime = LocalDateTime.parse(timeString, formatter).plusHours(2);

            }
            if (levelNumber >= 70 && nameNotIn(name)) {

                RaidBoss rb = new RaidBoss()
                        .setLevel(levelNumber)
                        .setName(name)
                        .setAlive(alive)
                        .setRespawnStart(localDateTime)
                        .setRespawnEnd(localDateTime == null ? null : localDateTime.plusHours(1));

                raidBosses.add(rb);
            }
        }

        return raidBosses;
    }

    private static boolean nameNotIn(String name) {
        String names = "Valakas Antharas Baium Queen Ant Sailren Benom Scarlet van Halisha Eilhalder von Hellmann" +
                " Soul of Water Ashatur Soul of Fire Nastron Icicle Emperor Bumbalump Daimon the White-Eyed Lilith Anakim";
        return !names.contains(name);
    }
}
