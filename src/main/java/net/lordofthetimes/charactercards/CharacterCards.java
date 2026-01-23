package net.lordofthetimes.charactercards;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.component.PluginComponents;
import net.lordofthetimes.charactercards.hytale.command.CharacterCommand;
import net.lordofthetimes.charactercards.hytale.command.charactersubcmd.CharacterGuiCommand;
import net.lordofthetimes.charactercards.hytale.event.onPlayerJoin;
import net.lordofthetimes.charactercards.service.CharacterService;

import javax.annotation.Nonnull;

public class CharacterCards extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    private CharacterService characterService;
    private CharacterCommand characterCommand;
    private onPlayerJoin playerJoinEvent;

    public CharacterCards(@Nonnull JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Hello from " + this.getName() + " version " + this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up plugin " + this.getName());

        PluginComponents.characterCard =
                this.getEntityStoreRegistry().registerComponent(
                        CharacterCardComponent.class,
                        "PlayerCharacterCard",
                        CharacterCardComponent.CODEC
                );

        characterService = new CharacterService();
        characterCommand = new CharacterCommand(characterService);


        playerJoinEvent = new onPlayerJoin(characterService);

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, playerJoinEvent::onPlayerReady);
        this.getCommandRegistry().registerCommand(characterCommand);
        this.getCommandRegistry().registerCommand(new CharacterGuiCommand(characterService));
        
    }
}
