package net.lordofthetimes.charactercards.adapters.character;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.adapters.ICharacterAdapters;
import net.lordofthetimes.charactercards.service.CharacterService;
import net.lordofthetimes.charactercards.utils.CardFormatter;

import java.awt.*;

public class CharacterChatAdapter implements ICharacterAdapters<Message> {


    CharacterService characterService;


    public CharacterChatAdapter(CharacterService characterService) {
        this.characterService = characterService;
    }

    public Message getPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref){
        CharacterCardComponent character = characterService.getPlayerCharacter(store,ref);
        return CardFormatter.formatChatCard(character);
    }

    @Override
    public CharacterService getCharacterService() {
        return characterService;
    }

}
