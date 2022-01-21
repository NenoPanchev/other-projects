
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        List<RaidBoss> raidBosses = new ArrayList<>();
        String siteUrl = "https://seasons.l2reborn.org/raidbosses-map";


                System.setProperty("webdriver.gecko.driver", "src/main/resources/files/geckodriver.exe");
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(firefoxBinary);
        options.setHeadless(true);
        WebDriver driver = new FirefoxDriver(options);
        driver.get(siteUrl);

        Document doc = Jsoup.parse(driver.getPageSource());
        String text33 = doc.body().text();



        Elements middles = doc.getElementsByClass("middle");


        for (Element middle : middles) {
            String name = middle.select("u").first().text();

            Elements children = middle.children();
            List<TextNode> respawns = middle.children().get(5).textNodes();
            boolean alive = respawns.size() == 0;
            String text = middle.text();
            String level = middle.textNodes().get(2).text();
            Integer levelNumber = Integer.parseInt(String.valueOf(Arrays.stream(level.split(": ")).skip(1).findFirst().orElse("0")));
            System.out.println();
            LocalDateTime localDateTime = null;
            if (!alive) {
                String timeString = respawns.get(1).text().replace("**", "00").replace(" UTC", "");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                localDateTime = LocalDateTime.parse(timeString, formatter).plusHours(2);
                System.out.println();
            }
            if (levelNumber >= 70 && nameNotIn(name)) {

                RaidBoss rb = new RaidBoss()
                        .setLevel(levelNumber)
                        .setName(name)
                        .setStatus(alive ? "Alive" : "Dead")
                        .setRespawnStart(localDateTime)
                        .setRespawnEnd(localDateTime == null ? null : localDateTime.plusHours(1));

                raidBosses.add(rb);
            }
        }

        System.out.println();
    }

    private static boolean nameNotIn(String name) {
                String names = "Valakas Antharas Baium Queen Ant Sailren Benom Scarlet van Halisha Eilhalder von Hellmann" +
                        " Soul of Water Ashatur Soul of Fire Nastron Icicle Emperor Bumbalump Daimon the White-Eyed";


        return !names.contains(name);
    }
}
