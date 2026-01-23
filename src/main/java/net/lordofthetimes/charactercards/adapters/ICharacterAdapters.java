package net.lordofthetimes.charactercards.adapters;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.service.CharacterService;

public interface ICharacterAdapters<T> {

    CharacterService getCharacterService();

    default  boolean loadPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref) {
        return getCharacterService().loadPlayerCharacter(store,ref);
    }

    default  boolean setPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref, CharacterCardComponent character){
        return getCharacterService().setPlayerCharacter(store,ref,character);
    }

    T getPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref,String username);


}
