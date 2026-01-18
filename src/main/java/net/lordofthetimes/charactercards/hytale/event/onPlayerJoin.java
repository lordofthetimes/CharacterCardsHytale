package net.lordofthetimes.charactercards.hytale.event;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.controller.character.CharacterChatController;
import net.lordofthetimes.charactercards.controller.character.ICharacterController;
import net.lordofthetimes.charactercards.service.CharacterService;

import java.util.UUID;

public class onPlayerJoin {

    final ICharacterController controller;

    public onPlayerJoin(CharacterService service) {
        this.controller = new CharacterChatController(service);
    }

    public void onPlayerReady(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        Store<EntityStore> store = player.getWorld().getEntityStore().getStore();
        UUID uuid = store.getComponent(player.getReference(), UUIDComponent.getComponentType()).getUuid();
        assert uuid != null;
        this.controller.loadPlayerCharacter(uuid);
    }
}
