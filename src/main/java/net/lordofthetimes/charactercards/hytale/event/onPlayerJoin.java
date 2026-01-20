package net.lordofthetimes.charactercards.hytale.event;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.adapters.character.CharacterChatAdapter;
import net.lordofthetimes.charactercards.adapters.ICharacterAdapters;
import net.lordofthetimes.charactercards.service.CharacterService;

public class onPlayerJoin {

    private final ICharacterAdapters controller;

    public onPlayerJoin(CharacterService service) {
        this.controller = new CharacterChatAdapter(service);
    }

    public void onPlayerReady(PlayerReadyEvent event) {
        Ref<EntityStore> player = event.getPlayer().getReference();
        Store<EntityStore> store = player.getStore();
        controller.loadPlayerCharacter(store,player);
    }
}
