package net.lordofthetimes.charactercards;

import java.util.UUID;

public class PlayerCharacter {
    public final UUID uuid;
    public final String name;
    public final String race;

    public PlayerCharacter(UUID uuid, String name, String race) {
        this.uuid = uuid;
        this.name = name;
        this.race = race;
    }
}
