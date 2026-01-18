package net.lordofthetimes.charactercards.repository;

import net.lordofthetimes.charactercards.PlayerCharacter;

import java.util.UUID;

public interface ICharacterRepository {

    PlayerCharacter getPlayerData(UUID uuid);

}
