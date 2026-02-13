package net.lordofthetimes.charactercards.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fi.sulku.hytale.TinyMsg;

import javax.annotation.Nonnull;

public class ShoutCommand extends AbstractPlayerCommand {

    private final RequiredArg message;

    public ShoutCommand() {
        super("shout", "Bypasses local chat and sends the message globally. Has configurable cost");
        message = this.withRequiredArg("message","Message to send globally",ArgTypes.STRING);
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef executor, @Nonnull World world) {
        String displayName = store.getComponent(ref, DisplayNameComponent.getComponentType()).getDisplayName().getRawText();
        world.getPlayerRefs().forEach((playerRef)->{
            playerRef.sendMessage(
                    Message.join(
                            TinyMsg.parse("<red><bold>[SHOUT] %s: ".formatted(displayName)),
                            Message.raw(message.get(context).toString())
                    )
            );
        });
    }
}
