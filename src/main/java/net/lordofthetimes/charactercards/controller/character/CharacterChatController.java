package net.lordofthetimes.charactercards.controller.character;

import com.hypixel.hytale.server.core.Message;
import net.lordofthetimes.charactercards.PlayerCharacter;
import net.lordofthetimes.charactercards.service.CharacterService;

import java.awt.*;
import java.util.UUID;

public class CharacterChatController implements ICharacterController{


    CharacterService characterService;


    public CharacterChatController(CharacterService characterService) {
        this.characterService = characterService;
    }

    public Message getPlayerCharacter(UUID uuid){
        PlayerCharacter character = characterService.getPlayerCharacter(uuid);
        return Message.raw(character.name).color(Color.GREEN);
    }

    @Override
    public CharacterService getCharacterService() {
        return characterService;
    }
}
