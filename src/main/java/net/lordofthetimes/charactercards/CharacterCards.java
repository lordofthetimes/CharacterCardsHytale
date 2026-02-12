package net.lordofthetimes.charactercards;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import net.lordofthetimes.charactercards.command.LocalChatCommand;
import net.lordofthetimes.charactercards.command.localchatsubcmd.LocalChatTestCommand;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.command.CharacterCommand;
import net.lordofthetimes.charactercards.event.EnsureComponents;
import net.lordofthetimes.charactercards.event.LocalChat;

import javax.annotation.Nonnull;

public class CharacterCards extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    public final Config<PluginConfig> config = this.withConfig("CharacterCardsConfig", PluginConfig.CODEC);

    private CharacterCommand characterCommand;
    private LocalChatCommand localChatCommand;

    private EnsureComponents ensureComponents;
    private LocalChat localChat;

    public CharacterCards(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        config.save();
        PluginConfig conf = config.get();
        LOGGER.atInfo().log("Setting up plugin " + this.getName());

        registerComponents();

        registerCommands();

        registerEvents();
    }

    private void registerComponents(){
        CharacterCardComponent.TYPE =
                this.getEntityStoreRegistry().registerComponent(
                        CharacterCardComponent.class,
                        "PlayerCharacterCard",
                        CharacterCardComponent.CODEC
                );
    }

    private  void registerCommands(){
        characterCommand = new CharacterCommand(this);
        localChatCommand = new LocalChatCommand();

        this.getCommandRegistry().registerCommand(characterCommand);
        this.getCommandRegistry().registerCommand(localChatCommand);
    }

    private  void registerEvents(){
        localChat = new LocalChat(this);
        ensureComponents = new EnsureComponents(this);

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ensureComponents::onPlayerReady);
        this.getEventRegistry().registerGlobal(PlayerChatEvent.class,localChat::onPlayerChat);
    }
}
