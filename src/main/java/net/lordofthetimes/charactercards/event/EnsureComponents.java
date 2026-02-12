package net.lordofthetimes.charactercards.event;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.CharacterCards;
import net.lordofthetimes.charactercards.utils.CardUtils;
import net.lordofthetimes.charactercards.utils.LocalChatUtils;

import java.util.UUID;

public class EnsureComponents {

    private final CharacterCards plugin;

    public EnsureComponents(CharacterCards plugin){
        this.plugin = plugin;
    }

    public void onPlayerReady(PlayerReadyEvent event) {
        Ref<EntityStore> player = event.getPlayer().getReference();
        Store<EntityStore> store = player.getStore();

        UUID uuid = store.getComponent(player, UUIDComponent.getComponentType()).getUuid();

        CardUtils.loadPlayerCharacter(store,player);
        LocalChatUtils.loadLocalChatComponent(uuid,plugin.config.get());
    }
}
