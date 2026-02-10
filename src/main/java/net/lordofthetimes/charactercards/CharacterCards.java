package net.lordofthetimes.charactercards;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.command.CharacterCommand;
import net.lordofthetimes.charactercards.event.onPlayerJoin;

import javax.annotation.Nonnull;

public class CharacterCards extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    public final Config<PluginConfig> config = this.withConfig("CharacterCardsConfig", PluginConfig.CODEC);

    private CharacterCommand characterCommand;
    private onPlayerJoin playerJoinEvent;

    public CharacterCards(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        config.save();

        LOGGER.atInfo().log("Setting up plugin " + this.getName());

        CharacterCardComponent.TYPE =
                this.getEntityStoreRegistry().registerComponent(
                        CharacterCardComponent.class,
                        "PlayerCharacterCard",
                        CharacterCardComponent.CODEC
                );

        characterCommand = new CharacterCommand(this);


        playerJoinEvent = new onPlayerJoin();

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, playerJoinEvent::onPlayerReady);
        this.getCommandRegistry().registerCommand(characterCommand);
        
    }
}
