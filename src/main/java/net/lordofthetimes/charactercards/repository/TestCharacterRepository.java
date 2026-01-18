package net.lordofthetimes.charactercards.repository;

import net.lordofthetimes.charactercards.PlayerCharacter;

import java.util.UUID;

public class TestCharacterRepository implements ICharacterRepository {

    @Override
    public PlayerCharacter getPlayerData(UUID uuid) {
        return new PlayerCharacter(uuid, "Test Name", "Test Race");
    }
}
