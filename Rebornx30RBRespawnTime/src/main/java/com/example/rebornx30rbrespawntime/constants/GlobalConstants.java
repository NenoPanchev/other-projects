package com.example.rebornx30rbrespawntime.constants;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GlobalConstants {
    public static final String EXCLUDED_RAIDBOSSES = "Valakas Antharas Baium Queen Ant Zaken Sailren Benom Scarlet van Halisha Eilhalder von Hellmann" +
            " Soul of Water Ashutar Soul of Fire Nastron Icicle Emperor Bumbalump Daimon the White-Eyed Lilith Anakim Andreas Van Halter " +
            " Captain of the Ice Queen's Royal Guard Ice Fairy Sirra The 3rd Underwater Guardian Eva's Guardian Millenu Giant Marpanak " +
            " Guardian of the Statue of Giant Karum Taik High Prefect Arak Lord Ishka ";

    public static final String SUB_AND_ALLY_BOSSES = "Shilen's Messenger Cabrio Varka's Hero Shadith Varka's Commander Mos Ketra's Hero Hekaton Ketra's Commander Tayr " +
            "Death Lord Hallate Kernon Longhorn Golkonda";
    public static final String BARAKIEL = "Flame of Splendor Barakiel";
    public static final String SITE_URL = "https://seasons.l2reborn.org/raidbosses-map";
    public static final String SOUND_FILE_PATH = "src/main/resources/static/sounds/tada.wav";
    public static final int LEVEL_BRACKET = 58;
    public static final Map<String, String> namesIDs = Stream.of(new String[][] {
            { "Shilen's Messenger Cabrio", "25035" }, { "Rahha", "25051" }, { "Kernon", "25054" }, { "Bloody Priest Rudelto", "25073" },
            { "Soulless Wild Boar", "25089" }, { "Korim", "25092" }, { "Ghost of the Well Lidia", "25106" }, { "Antharas Priest Cloe", "25109" },
            { "Fierce Tiger King Angel", "25125" }, { "Longhorn Golkonda", "25126" }, { "Hekaton Prime", "25140" }, { "Fire of Wrath Shuriel", "25143" },
            { "Roaring Skylancer", "25163" }, { "Demon Kurikups", "25182" }, { "Fafurion's Herald Lokness", "25198" }, { "Water Dragon Seer Sheshark", "25199" },
            { "Krokian Padisha Sobekk", "25202" }, { "Ocean Flame Ashakiel", "25205" }, { "Death Lord Hallate", "25220" }, { "Roaring Lord Kastor", "25226" },
            { "Storm Winged Naga", "25229" }, { "Spirit of Andras, the Betrayer", "25233" }, { "Ancient Weird Drake", "25234" },
            { "Vanor Chief Kandra", "25235" }, { "Abyss Brukunt", "25238" }, { "Last Lesser Giant Olkuth", "25244" }, { "Last Lesser Giant Glaki", "25245" },
            { "Doom Blade Tanatos", "25248" }, { "Palatanos of Horrific Power", "25249" }, { "Palibati Queen Themis", "25252" }, { "Gargoyle Lord Tiphon", "25255" },
            { "Kernon's Faithful Servant Kelone", "25263" }, { "Bloody Empress Decarbia", "25266" }, { "Beast Lord Behemoth", "25269" },
            { "Death Lord Ipos", "25276" }, { "Anakim's Nemesis Zakaron", "25281" }, { "Death Lord Shax", "25282" }, { "Hestia, Guardian Deity of the Hot Springs", "25293" },
            { "Ketra's Hero Hekaton", "25299" }, { "Ketra's Commander Tayr", "25302" }, { "Ketra's Chief Brakki", "25305" }, { "Varka's Hero Shadith", "25309" },
            { "Varka's Commander Mos", "25312" }, { "Varka's Chief Horus", "25315" }, { "Ember", "25319" }, { "Demon's Agent Falston", "25322" },
            { "Flame of Splendor Barakiel", "25325" }, { "Fairy Queen Timiniel", "25423" }, { "Enmity Ghost Ramdal", "25444" }, { "Immortal Savior Mardil", "25447" },
            { "Cherub Galaxia", "25450" }, { "Meanas Anor", "25453" }, { "Gorgolos", "25467" }, { "Last Titan Utenus", "25470" }, { "Shilen's Priest Hisilrome", "25478" },
            { "Queen Shyeed", "25514" }, { "Plague Golem", "25523" }, { "Flamestone Giant", "25524" }, { "Uruka", "25527" }, { "Anais", "29096" },
            { "Andreas Van Halter", "29062" }, { "Eilhalder von Hellmann", "25328" }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
}
