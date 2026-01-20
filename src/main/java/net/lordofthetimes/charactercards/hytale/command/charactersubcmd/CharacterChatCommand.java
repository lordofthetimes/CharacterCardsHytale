package net.lordofthetimes.charactercards.hytale.command.charactersubcmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.adapters.character.CharacterChatAdapter;
import net.lordofthetimes.charactercards.service.CharacterService;

import javax.annotation.Nonnull;
import java.awt.*;

public class CharacterChatCommand extends AbstractPlayerCommand {

    private final CharacterChatAdapter adapter;
    private final OptionalArg<String> playerArg;

    public CharacterChatCommand(CharacterChatAdapter adapter){
        super("chat","Command for viewing character card as formatted chat message");
        playerArg = this.withOptionalArg("player",
                "Whose character card open. Leaving it empty will open it for player running the command", ArgTypes.STRING);
        this.adapter = adapter;
    }

    @Override
    public String getPermission() {
        return "charactercards.chat";
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> execStore, @Nonnull Ref<EntityStore> execRef,
                           @Nonnull PlayerRef execPlayerRef, @Nonnull World world) {
        world.execute(()->{
            Player executor = execStore.getComponent(execRef,Player.getComponentType());

            if(playerArg.get(context) == null || playerArg.get(context).equals("")) {
                executor.sendMessage(adapter.getPlayerCharacter(execStore,execRef));
                return;
            }

            PlayerRef target = Universe.get().getPlayer(playerArg.get(context), NameMatching.EXACT_IGNORE_CASE);
            if(target == null){
                executor.sendMessage(Message.raw("This player is offline or does not exist").color(Color.YELLOW));
                return;
            }
            Universe.get().getWorld(target.getWorldUuid()).execute(()->{
                Ref<EntityStore> targetRef = target.getReference();
                Store<EntityStore> targetRefStore = targetRef.getStore();
                Message message = adapter.getPlayerCharacter(targetRefStore,targetRef);
                executor.sendMessage(message);
            });

        });
    }
}
