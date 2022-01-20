import java.time.LocalDateTime;

public class RaidBoss {
    private String name;
    private Integer level;
    private String status;
    private LocalDateTime respawnStart;
    private LocalDateTime respawnEnd;
    private LocalDateTime previousRespawnStart;

    public RaidBoss() {
    }

    public String getName() {
        return name;
    }

    public RaidBoss setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public RaidBoss setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public RaidBoss setStatus(String status) {
        this.status = status;
        return this;
    }


    public LocalDateTime getRespawnStart() {
        return respawnStart;
    }

    public RaidBoss setRespawnStart(LocalDateTime respawnStart) {
        this.respawnStart = respawnStart;
        return this;
    }

    public LocalDateTime getPreviousRespawnStart() {
        return previousRespawnStart;
    }

    public RaidBoss setPreviousRespawnStart(LocalDateTime previousRespawnStart) {
        this.previousRespawnStart = previousRespawnStart;
        return this;
    }

    public LocalDateTime getRespawnEnd() {
        return respawnEnd;
    }

    public RaidBoss setRespawnEnd(LocalDateTime respawnEnd) {
        this.respawnEnd = respawnEnd;
        return this;
    }
}
