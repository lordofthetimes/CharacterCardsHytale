package net.lordofthetimes.charactercards.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.command.localchatsubcmd.LocalChatForcedCommand;
import net.lordofthetimes.charactercards.command.localchatsubcmd.LocalChatTestCommand;
import net.lordofthetimes.charactercards.component.LocalChatComponent;
import net.lordofthetimes.charactercards.utils.LocalChatUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.UUID;

public class LocalChatCommand extends CommandBase {
    public LocalChatCommand() {
        super("localchat", "Base local chat command, used to toggle local chat");
        this.addAliases("lc");
        this.addSubCommand(new LocalChatTestCommand());
        this.addSubCommand(new LocalChatForcedCommand());
    }

    @Override
    protected void executeSync(@Nonnull CommandContext context) {
        if(!context.isPlayer()) return;

        Player player = context.senderAs(Player.class);
        player.getWorld().execute(()->{

            Ref<EntityStore> ref = player.getReference();
            Store<EntityStore> store = ref.getStore();

            UUID uuid = store.getComponent(ref, PlayerRef.getComponentType()).getUuid();
            LocalChatComponent component = LocalChatUtils.getComponent(uuid);
            component.setEnabled(!component.isEnabled());
            LocalChatUtils.setComponent(uuid,component);

            context.sendMessage(Message.raw("Local chat is now %s!"
                    .formatted(component.isEnabled() ? "Enabled" : "Disabled"))
                    .color(Color.GREEN));
        });

    }

    @Nullable
    @Override
    public String getPermission() {
        return "charactercards.localchat";
    }

    @Override
    public boolean hasPermission(@Nonnull CommandSender sender) {
        return sender.hasPermission(this.getPermission(),true);
    }
}
