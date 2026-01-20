package net.lordofthetimes.charactercards.hytale.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.adapters.character.CharacterChatAdapter;
import net.lordofthetimes.charactercards.hytale.command.charactersubcmd.CharacterChatCommand;
import net.lordofthetimes.charactercards.hytale.command.charactersubcmd.CharacterGuiCommand;
import net.lordofthetimes.charactercards.service.CharacterService;

import javax.annotation.Nonnull;

public class CharacterCommand extends CommandBase {

    private final CharacterChatAdapter adapter;

    public CharacterCommand(CharacterService characterService){
        super("character","Main command for viewing and changing Character Cards");

        this.withOptionalArg("subcmd","What sub command to use. Will send help message if empty", ArgTypes.STRING);
        this.addAliases("card","profile");

        this.adapter = new CharacterChatAdapter(characterService);

        this.addSubCommand(new CharacterGuiCommand(characterService));
        this.addSubCommand(new CharacterChatCommand(adapter));
    }

    @Override
    protected void executeSync(@Nonnull CommandContext commandContext) {
        if(!commandContext.isPlayer()) return;
        Player player = commandContext.senderAs(Player.class);
        Ref<EntityStore> ref = player.getReference();
        player.getWorld().execute(()->{
            Store<EntityStore> store = ref.getStore();
            player.sendMessage(adapter.getPlayerCharacter(store,ref));
        });
    }

    @Override
    public String getPermission() {
        return "charactercards";
    }

}
