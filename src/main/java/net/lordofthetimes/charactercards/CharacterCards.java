package net.lordofthetimes.charactercards;

import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.plugin.PluginBase;
import com.hypixel.hytale.server.core.util.Config;
import net.lordofthetimes.charactercards.command.LocalChatCommand;
import net.lordofthetimes.charactercards.command.ShoutCommand;
import net.lordofthetimes.charactercards.compatibility.OrbisOriginsCompatibility;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;
import net.lordofthetimes.charactercards.command.CharacterCommand;
import net.lordofthetimes.charactercards.event.EnsureComponents;
import net.lordofthetimes.charactercards.event.LocalChat;

import javax.annotation.Nonnull;

public class CharacterCards extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    public final Config<PluginConfig> config = this.withConfig("CharacterCardsConfig", PluginConfig.CODEC);

    private EnsureComponents ensureComponents;
    private LocalChat localChat;

    private CharacterCommand characterCommand;
    private LocalChatCommand localChatCommand;
    private ShoutCommand shoutCommand;

    public CharacterCards(@Nonnull JavaPluginInit init) {
        super(init);
    }
    @Override
    protected void setup() {
        config.save();
        PluginConfig conf = config.get();
        LOGGER.atInfo().log("Setting up plugin " + this.getName());

        tryEnableCompatibilities();

        registerComponents();

        if(conf.isLocalChatEnabled()) registerLocalChat();


        registerCommands();

        if(conf.isShoutEnabled()) registerShout();

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

        this.getCommandRegistry().registerCommand(characterCommand);

    }

    private  void registerEvents(){
        ensureComponents = new EnsureComponents(this);

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ensureComponents::onPlayerReady);
    }

    private void registerLocalChat(){
        localChatCommand = new LocalChatCommand();
        this.getCommandRegistry().registerCommand(localChatCommand);

        localChat = new LocalChat(this);
        this.getEventRegistry().registerGlobal(PlayerChatEvent.class,localChat::onPlayerChat);

    }

    private void registerShout(){
        shoutCommand = new ShoutCommand();

        this.getCommandRegistry().registerCommand(shoutCommand);
    }

    private void tryEnableCompatibilities(){
        tryEnableOrbisOriginsCompatibility();
    }

    private void tryEnableOrbisOriginsCompatibility(){
        PluginBase plugin = HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("hexvane:OrbisOrigins"));
        if(config.get().isOrbisOriginsSupportEnabled() && plugin.isEnabled()){
            OrbisOriginsCompatibility.supportEnabled = true;
        }
    }
}
