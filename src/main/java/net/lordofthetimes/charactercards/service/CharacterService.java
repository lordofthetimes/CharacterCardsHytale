package net.lordofthetimes.charactercards.service;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.CharacterCards;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.component.PluginComponents;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CharacterService {

    private final ConcurrentHashMap<UUID, CharacterCardComponent> cache = new ConcurrentHashMap<>();

    public CharacterCardComponent getPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref){
        return store.getComponent(ref, CharacterCardComponent.getComponentType());
    }

    public Boolean loadPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref){
        store.ensureComponent(ref, CharacterCardComponent.getComponentType());
        return true;
    }
}
