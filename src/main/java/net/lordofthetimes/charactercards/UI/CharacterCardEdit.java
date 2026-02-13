package net.lordofthetimes.charactercards.UI;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.CharacterCards;
import net.lordofthetimes.charactercards.PluginConfig;
import net.lordofthetimes.charactercards.compatibility.OrbisOriginsCompatibility;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.utils.CardUtils;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.UUID;

public class CharacterCardEdit extends InteractiveCustomUIPage<CharacterCardEdit.Data> {

    private final CharacterCardComponent character;
    private final String username;
    private final UUID ownerUUID;
    private final CharacterCards plugin;
    public CharacterCardEdit(@Nonnull PlayerRef playerRef, CharacterCardComponent character, String username, UUID ownerUUID, CharacterCards plugin) {
        super(playerRef, CustomPageLifetime.CantClose,Data.CODEC);
        this.character = character;
        this.username = username;
        this.ownerUUID = ownerUUID;
        this.plugin = plugin;
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder cmd, @Nonnull UIEventBuilder evt, @Nonnull Store<EntityStore> store) {
        cmd.append("CharacterCards/CharacterCardEdit.ui");
        cmd.set("#titleText.Text","Edit " + username + "'s Character Card");
        cmd.set("#Name.Value", character.getName());
        cmd.set("#Age.Value",character.getAge());
        cmd.set("#Race.Value",character.getRace());
        cmd.set("#Gender.Value",character.getGender());
        cmd.set("#Description.Value",character.getDescription());
        cmd.set("#Lore.Value",character.getLore());

        evt.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#Save",
                new EventData()
                        .append("@Name", "#Name.Value")
                        .append("@Age", "#Age.Value")
                        .append("@Race", "#Race.Value")
                        .append("@Gender", "#Gender.Value")
                        .append("@Description", "#Description.Value")
                        .append("@Lore", "#Lore.Value")
        );

        hideDisabled(cmd,plugin.config.get());
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Data data) {

        String name = data.name !=null && !data.name.isEmpty() ? data.name : "None";
        String age = data.age !=null && !data.age.isEmpty() ? data.age : "None";
        String race = data.race !=null && !data.race.isEmpty() ? data.race : "None";
        String gender = data.gender !=null && !data.gender.isEmpty() ? data.gender : "None";
        String description = data.description !=null && !data.description.isEmpty() ? data.description : "None";
        String lore = data.lore !=null && !data.lore.isEmpty() ? data.lore : "None";

        Universe universe = Universe.get();

        CharacterCardComponent character = new CharacterCardComponent(name,age,race,gender,description,lore);
        PlayerRef playerRef = universe.getPlayer(ownerUUID);

        Player player = store.getComponent(ref,Player.getComponentType());

        if (playerRef == null) {
            player.sendMessage(Message.raw("Owner of the character card is offline. It was not saved").color(Color.RED));
            player.getPageManager().setPage(ref,store, Page.None);
            return;
        }

        universe.getWorld(playerRef.getWorldUuid()).execute(()->{
            Ref<EntityStore> ownerRef = playerRef.getReference();
            CardUtils.setPlayerCharacter(ownerRef.getStore(),ownerRef,character);
        });

        player.sendMessage(Message.raw("Saved Character card!").color(Color.GREEN));
        player.getPageManager().setPage(ref,store, Page.None);
    }

    public void hideDisabled(UICommandBuilder builder, PluginConfig config){
        if(!config.isNameEnabled()){
            builder.set("#NameLabel.Visible",false);
            builder.set("#Name.Visible",false);
        }
        if(!config.isAgeEnabled()){
            builder.set("#AgeLabel.Visible",false);
            builder.set("#Age.Visible",false);
        }
        if(!config.isRaceEnabled() || OrbisOriginsCompatibility.supportEnabled){
            builder.set("#RaceLabel.Visible",false);
            builder.set("#Race.Visible",false);
        }
        if(!config.isGenderEnabled()){
            builder.set("#GenderLabel.Visible",false);
            builder.set("#Gender.Visible",false);
        }
        if(!config.isDescriptionEnabled()){
            builder.set("#DescriptionLabel.Visible",false);
            builder.set("#Description.Visible",false);
        }
        if(!config.isLoreEnabled()){
            builder.set("#LoreLabel.Visible",false);
            builder.set("#Lore.Visible",false);
        }
    }

    public static class Data{

        public String name;
        public String age;
        public String race;
        public String gender;
        public String description;
        public String lore;

        public static final BuilderCodec<Data> CODEC = BuilderCodec.builder(Data.class,Data::new).append(
                new KeyedCodec<>("@Name", Codec.STRING),(data, value) -> data.name = value,
                data -> data.name
        ).add().append(
                new KeyedCodec<>("@Age", Codec.STRING),(data, value) -> data.age = value,
                data -> data.age
        ).add().append(
                new KeyedCodec<>("@Race", Codec.STRING),(data, value) -> data.race = value,
                data -> data.race
        ).add().append(
                new KeyedCodec<>("@Gender", Codec.STRING),(data, value) -> data.gender = value,
                data -> data.gender
        ).add().append(
                new KeyedCodec<>("@Description", Codec.STRING),(data, value) -> data.description = value,
                data -> data.description
        ).add().append(
                new KeyedCodec<>("@Lore", Codec.STRING),(data, value) -> data.lore = value,
                data -> data.lore
        ).add().build();
    }

}
