package net.lordofthetimes.charactercards.utils;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.CharacterCards;
import net.lordofthetimes.charactercards.PluginConfig;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.UI.CharacterCardEdit;
import net.lordofthetimes.charactercards.UI.CharacterCardView;
import net.lordofthetimes.charactercards.service.CharacterService;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class CardUtils {

    private static final CharacterService service = new CharacterService();

    public static CharacterCardEdit editPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref, String username, UUID uuid){
        PlayerRef playerRef = store.getComponent(ref,PlayerRef.getComponentType());
        CharacterCardComponent character = store.getComponent(ref, CharacterCardComponent.getComponentType());
        return new CharacterCardEdit(playerRef,character,username,uuid);
    }

    public static boolean setPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref, CharacterCardComponent character){
        return service.setPlayerCharacter(store,ref,character);
    }

    public static CharacterCardView getPlayerCharacterView(Store<EntityStore> store, Ref<EntityStore> ref, String username, CharacterCards plugin){
        PlayerRef playerRef = store.getComponent(ref,PlayerRef.getComponentType());
        CharacterCardComponent character = store.getComponent(ref, CharacterCardComponent.getComponentType());
        return new CharacterCardView(playerRef, CustomPageLifetime.CanDismiss,character,username,plugin);
    }

    public static boolean loadPlayerCharacter(Store<EntityStore> store, Ref<EntityStore> ref) {
        return service.loadPlayerCharacter(store,ref);
    }

    public static Message getPlayerCharacterChat(Store<EntityStore> store, Ref<EntityStore> ref, String username, CharacterCards plugin){
        PluginConfig config = plugin.config.get();
        CharacterCardComponent character = store.getComponent(ref, CharacterCardComponent.getComponentType());

        return Stream.of(

                        config.isNameEnabled() ? Message.raw(config.formatName(character.getName())) : null,
                        config.isAgeEnabled() ? Message.raw(config.formatAge(character.getAge())) : null,
                        config.isRaceEnabled() ? Message.raw(config.formatRace(character.getRace())) : null,
                        config.isGenderEnabled() ? Message.raw(config.formatGender(character.getGender())) : null,
                        config.isDescriptionEnabled() ? Message.raw(config.formatDescription(character.getDescription())) : null,
                        config.isLoreEnabled() ? Message.raw(config.formatLore(character.getLore())) : null
                )
                .filter(Objects::nonNull)
                .reduce(Message.raw("---[ "+username+"'s Character Card ]---\n"), Message::join);

    }

}
