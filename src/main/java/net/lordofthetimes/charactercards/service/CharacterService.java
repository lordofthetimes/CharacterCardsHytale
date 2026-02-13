package net.lordofthetimes.charactercards.service;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.compatibility.OrbisOriginsCompatibility;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CharacterService {

    public CharacterCardComponent getPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref){
        return store.getComponent(ref, CharacterCardComponent.getComponentType());
    }

    public Boolean loadPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref){
        CharacterCardComponent characterCard = store.ensureAndGetComponent(ref, CharacterCardComponent.getComponentType());
        if(OrbisOriginsCompatibility.supportEnabled){
            String race = OrbisOriginsCompatibility.getSpeciesName(ref,store,store.getExternalData().getWorld());
            characterCard.setRace(race);
        }
        return true;
    }

    public Boolean setPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref, CharacterCardComponent character){
        store.replaceComponent(ref,CharacterCardComponent.getComponentType(),character);
        return true;
    }


}
