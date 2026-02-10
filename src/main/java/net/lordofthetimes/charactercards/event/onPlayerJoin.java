package net.lordofthetimes.charactercards.event;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.utils.CardUtils;

public class onPlayerJoin {


    public void onPlayerReady(PlayerReadyEvent event) {
        Ref<EntityStore> player = event.getPlayer().getReference();
        Store<EntityStore> store = player.getStore();
        CardUtils.loadPlayerCharacter(store,player);
    }
}
