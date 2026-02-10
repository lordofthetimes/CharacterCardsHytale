package net.lordofthetimes.charactercards.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import net.lordofthetimes.charactercards.CharacterCards;
import net.lordofthetimes.charactercards.command.charactersubcmd.CharacterChatCommand;
import net.lordofthetimes.charactercards.command.charactersubcmd.CharacterEditCommand;
import net.lordofthetimes.charactercards.command.charactersubcmd.CharacterGuiCommand;
import net.lordofthetimes.charactercards.utils.CardUtils;

import javax.annotation.Nonnull;

public class CharacterCommand extends CommandBase {

    private final CharacterCards plugin;

    public CharacterCommand(CharacterCards plugin){
        super("character","Main command for viewing and changing Character Cards");

        this.withOptionalArg("subcmd","What sub command to use. Will send help message if empty", ArgTypes.STRING);
        this.addAliases("card","profile");


        this.addSubCommand(new CharacterGuiCommand(plugin));
        this.addSubCommand(new CharacterChatCommand(plugin));
        this.addSubCommand(new CharacterEditCommand(plugin));
        this.plugin = plugin;
    }

    @Override
    protected void executeSync(@Nonnull CommandContext commandContext) {
        if(!commandContext.isPlayer()) return;
        Player player = commandContext.senderAs(Player.class);
        Ref<EntityStore> ref = player.getReference();
        player.getWorld().execute(()->{
            Store<EntityStore> store = ref.getStore();
            player.sendMessage(CardUtils.getPlayerCharacterChat(store,ref,player.getDisplayName(),plugin));
        });
    }

    @Override
    public String getPermission() {
        return "charactercards";
    }

}
