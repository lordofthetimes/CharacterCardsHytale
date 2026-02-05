package net.lordofthetimes.charactercards.hytale.UI;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.Position;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.TargetUtil;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.utils.CardFormatter;

import javax.annotation.Nonnull;

public class CharacterCardView extends CustomUIPage {

    private final CharacterCardComponent character;
    private final String username;

    public CharacterCardView(@Nonnull PlayerRef playerRef, @Nonnull CustomPageLifetime lifetime, CharacterCardComponent character,String username) {
        super(playerRef, lifetime);
        this.character = character;
        this.username = username;
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder uiCommandBuilder, @Nonnull UIEventBuilder uiEventBuilder, @Nonnull Store<EntityStore> store) {
        uiCommandBuilder.append("CharacterCards/CharacterCardView.ui");
        uiCommandBuilder.set("#Name.Text", CardFormatter.formatName(character.getName()));
        uiCommandBuilder.set("#Age.Text",CardFormatter.formatAge(character.getAge()));
        uiCommandBuilder.set("#Race.Text",CardFormatter.formatRace(character.getRace()));
        uiCommandBuilder.set("#Gender.Text",CardFormatter.formatGender(character.getGender()));
        uiCommandBuilder.set("#Description.Text",CardFormatter.formatDescription(character.getDescription()));
        uiCommandBuilder.set("#Lore.Text",CardFormatter.formatLore(character.getLore()));
        uiCommandBuilder.set("#titleText.Text",username+"'s Character Card");

    }
}
