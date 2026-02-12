package net.lordofthetimes.charactercards.command.localchatsubcmd.testsubcmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.utils.LocalChatUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

public class LocalChatTestLocationCommand extends AbstractPlayerCommand {
    public LocalChatTestLocationCommand() {
        super("location","Sets a test point for the local chat");
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        TransformComponent transformComponent = store.getComponent(ref,TransformComponent.getComponentType());

        if (transformComponent == null) {
            playerRef.sendMessage(Message.raw("Could not get player position.").color(Color.RED));
            return;
        }

        Vector3d loc = transformComponent.getPosition();
        LocalChatUtils.setTestPoint(loc);

        playerRef.sendMessage(
                Message.raw(
                    "Test position set to x: %s y: %s z: %s"
                    .formatted(
                        loc.getX(),
                        loc.getY(),
                        loc.getZ()
                    )
                )
                .color(Color.GREEN)
        );
    }

    @Nullable
    @Override
    public String getPermission() {
        return "charactercards.localchat.test.location";
    }

    @Override
    public boolean hasPermission(@Nonnull CommandSender sender) {
        return sender.hasPermission(this.getPermission());
    }
}
