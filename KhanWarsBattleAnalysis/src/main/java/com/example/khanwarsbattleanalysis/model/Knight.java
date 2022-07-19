package com.example.khanwarsbattleanalysis.model;

import com.example.khanwarsbattleanalysis.model.enums.KnightClassEnum;

public class Knight {
    private KnightClassEnum knightClass;
    private Integer level;
    private Integer power;
    private Integer currentHealth;
    private Integer health;

    public Knight() {
    }

    public KnightClassEnum getKnightClass() {
        return knightClass;
    }

    public Knight setKnightClass(KnightClassEnum knightClass) {
        this.knightClass = knightClass;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public Knight setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getPower() {
        return power;
    }

    public Knight setPower(Integer power) {
        this.power = power;
        return this;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public Knight setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
        return this;
    }

    public Integer getHealth() {
        return health;
    }

    public Knight setHealth(Integer health) {
        this.health = health;
        return this;
    }
}
