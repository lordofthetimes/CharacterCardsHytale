package net.lordofthetimes.charactercards.controller.character;

import net.lordofthetimes.charactercards.service.CharacterService;

import java.util.UUID;

public interface ICharacterController {

    CharacterService getCharacterService();

    default boolean loadPlayerCharacter(UUID uuid) {
        return getCharacterService().loadPlayerCharacter(uuid);
    }

}
