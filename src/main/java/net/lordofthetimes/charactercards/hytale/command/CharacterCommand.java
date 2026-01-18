package net.lordofthetimes.charactercards.hytale.command;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import net.lordofthetimes.charactercards.controller.character.CharacterChatController;
import net.lordofthetimes.charactercards.service.CharacterService;

import javax.annotation.Nonnull;

public class CharacterCommand extends CommandBase {

    private final CharacterChatController controller;

    public CharacterCommand(CharacterService characterService){
        super("character","Main command for viewing and changing Character Cards");
        this.withOptionalArg("subcmd","What sub command to use. Will send help message if empty", ArgTypes.STRING);
        this.controller = new CharacterChatController(characterService);
    }

    @Override
    protected void executeSync(@Nonnull CommandContext commandContext) {
        commandContext.sendMessage(controller.getPlayerCharacter(commandContext.senderAs(Player.class).getUuid()));
    }


    //this.addSubCommand();
}
