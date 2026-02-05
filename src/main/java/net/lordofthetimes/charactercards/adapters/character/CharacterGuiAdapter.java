package net.lordofthetimes.charactercards.adapters.character;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.adapters.ICharacterAdapters;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.hytale.UI.CharacterCardEdit;
import net.lordofthetimes.charactercards.hytale.UI.CharacterCardView;
import net.lordofthetimes.charactercards.service.CharacterService;

import java.util.UUID;

public class CharacterGuiAdapter implements ICharacterAdapters<CustomUIPage> {

    CharacterService characterService;


    public CharacterGuiAdapter(CharacterService characterService) {
        this.characterService = characterService;
    }

    @Override
    public CharacterService getCharacterService() {
        return characterService;
    }

    @Override
    public CharacterCardView getPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref, String username){
        PlayerRef playerRef = store.getComponent(ref,PlayerRef.getComponentType());
        CharacterCardComponent character = store.getComponent(ref, CharacterCardComponent.getComponentType());
        return new CharacterCardView(playerRef,CustomPageLifetime.CanDismiss,character,username);
    }

    public CharacterCardEdit editPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref, String username, UUID uuid){
        PlayerRef playerRef = store.getComponent(ref,PlayerRef.getComponentType());
        CharacterCardComponent character = store.getComponent(ref, CharacterCardComponent.getComponentType());
        return new CharacterCardEdit(playerRef,character,username,this,uuid);
    }
    
}
