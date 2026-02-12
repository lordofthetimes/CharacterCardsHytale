package net.lordofthetimes.charactercards.UI;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.CharacterCards;
import net.lordofthetimes.charactercards.PluginConfig;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;

import javax.annotation.Nonnull;

public class CharacterCardView extends CustomUIPage {

    private final CharacterCardComponent character;
    private final String username;
    private final CharacterCards plugin;

    public CharacterCardView(@Nonnull PlayerRef playerRef, @Nonnull CustomPageLifetime lifetime, CharacterCardComponent character, String username, CharacterCards plugin) {
        super(playerRef, lifetime);
        this.character = character;
        this.username = username;
        this.plugin = plugin;
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder builder, @Nonnull UIEventBuilder uiEventBuilder, @Nonnull Store<EntityStore> store) {
        PluginConfig config = plugin.config.get();
        builder.append("CharacterCards/CharacterCardView.ui");

        builder.set("#titleText.Text",username+"'s Character Card");

        builder.set("#Name.Text", config.formatName(character.getName()));
        builder.set("#Age.Text",config.formatAge(character.getAge()));
        builder.set("#Race.Text",config.formatRace(character.getRace()));
        builder.set("#Gender.Text",config.formatGender(character.getGender()));
        builder.set("#Description.Text",config.formatDescription(character.getDescription()));
        builder.set("#Lore.Text",config.formatLore(character.getLore()));

        removeDisabled(builder,config);

    }

    public void removeDisabled(UICommandBuilder builder, PluginConfig config){
        if(!config.isNameEnabled()) builder.remove("#Name");
        if(!config.isAgeEnabled()) builder.remove("#Age");
        if(!config.isRaceEnabled()) builder.remove("#Race");
        if(!config.isGenderEnabled()) builder.remove("#Gender");
        if(!config.isDescriptionEnabled()) builder.remove("#Description");
        if(!config.isLoreEnabled()) builder.remove("#Lore");
    }
}
