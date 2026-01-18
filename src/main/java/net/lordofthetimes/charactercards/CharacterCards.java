package net.lordofthetimes.charactercards;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import net.lordofthetimes.charactercards.controller.character.ICharacterController;
import net.lordofthetimes.charactercards.hytale.command.CharacterCommand;
import net.lordofthetimes.charactercards.hytale.event.onPlayerJoin;
import net.lordofthetimes.charactercards.repository.ICharacterRepository;
import net.lordofthetimes.charactercards.repository.TestCharacterRepository;
import net.lordofthetimes.charactercards.service.CharacterService;

import javax.annotation.Nonnull;

public class CharacterCards extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    private ICharacterRepository repository;
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
        repository = new TestCharacterRepository();
        characterService = new CharacterService(repository);
        characterCommand = new CharacterCommand(characterService);

        playerJoinEvent = new onPlayerJoin(characterService);

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, playerJoinEvent::onPlayerReady);
        this.getCommandRegistry().registerCommand(characterCommand);
    }
}
