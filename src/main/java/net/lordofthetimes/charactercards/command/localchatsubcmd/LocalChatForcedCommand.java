package net.lordofthetimes.charactercards.command.localchatsubcmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.component.LocalChatComponent;
import net.lordofthetimes.charactercards.utils.LocalChatUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.UUID;

public class LocalChatForcedCommand extends AbstractPlayerCommand {

    private final OptionalArg<String> playerArg;

    public LocalChatForcedCommand() {
        super("forced", "Changes if the chat is forced or not.");
        playerArg = this.withOptionalArg("player",
                "What player to change for. If missing takes executor by default", ArgTypes.STRING);
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef executor, @Nonnull World world) {
        boolean argumentMissing = playerArg.get(context) == null || playerArg.get(context).equals("");
        if(argumentMissing && !context.isPlayer()) return;

        if(!argumentMissing){
            PlayerRef target = Universe.get().getPlayer(playerArg.get(context), NameMatching.EXACT_IGNORE_CASE);
            if(target == null){
                executor.sendMessage(Message.raw("This player is offline or does not exist").color(Color.YELLOW));
                return;
            }
            String displayName = store.getComponent(target.getReference(), DisplayNameComponent.getComponentType()).getDisplayName().getRawText();
            UUID uuid = target.getUuid();
            LocalChatComponent component = LocalChatUtils.getComponent(uuid);
            component.setForced(!component.isForced());
            LocalChatUtils.setComponent(uuid,component);

            context.sendMessage(Message.raw("Forced mode is now set to %s for %s".formatted(component.isForced(),displayName)).color(Color.GREEN));
        }
        else{
            UUID uuid = executor.getUuid();
            LocalChatComponent component = LocalChatUtils.getComponent(uuid);
            component.setForced(!component.isForced());
            LocalChatUtils.setComponent(uuid,component);

            context.sendMessage(Message.raw("Forced mode is now set to %s".formatted(component.isForced())).color(Color.GREEN));

        }
    }

    @Nullable
    @Override
    public String getPermission() {
        return "charactercards.localchat.forced";
    }

    @Override
    public boolean hasPermission(@Nonnull CommandSender sender) {
        return sender.hasPermission(this.getPermission());
    }
}
