package net.lordofthetimes.charactercards.command.localchatsubcmd;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import net.lordofthetimes.charactercards.command.localchatsubcmd.testsubcmd.LocalChatTestLocationCommand;
import net.lordofthetimes.charactercards.utils.LocalChatUtils;

import javax.annotation.Nonnull;
import java.awt.*;

public class LocalChatTestCommand extends CommandBase {
    public LocalChatTestCommand() {
        super("test", "Switches test mode for local chat on or off");
        this.addSubCommand(new LocalChatTestLocationCommand());
        this.requirePermission("charactercards.localchat.test");
    }

    @Override
    protected void executeSync(@Nonnull CommandContext commandContext) {
        LocalChatUtils.setTestMode(!LocalChatUtils.getTestMode());

        commandContext.sendMessage(Message.raw("Test mode is now set to %s!".formatted(LocalChatUtils.getTestMode())).color(Color.GREEN));

    }
}
