package net.lordofthetimes.charactercards.command.charactersubcmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.CharacterCards;
import net.lordofthetimes.charactercards.utils.CardUtils;

import javax.annotation.Nonnull;
import java.awt.*;

public class CharacterGuiCommand extends AbstractPlayerCommand {

    private final OptionalArg<String> playerArg;
    private final CharacterCards plugin;

    public CharacterGuiCommand(CharacterCards plugin){
        super("gui","Main command for viewing Character Cards");
        this.plugin = plugin;
        playerArg = this.withOptionalArg("player",
                "Whose character card open. Leaving it empty will open it for player running the command", ArgTypes.STRING);
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> execStore, @Nonnull Ref<EntityStore> execRef,
                           @Nonnull PlayerRef execPlayerRef, @Nonnull World world) {
        world.execute(()->{
            Player executor = execStore.getComponent(execRef,Player.getComponentType());

            if(playerArg.get(context) == null || playerArg.get(context).equals("")) {
                CustomUIPage page = CardUtils.getPlayerCharacterView(execStore,execRef,executor.getDisplayName(),plugin);
                executor.getPageManager().openCustomPage(execRef,execStore,page);
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
                Player player = targetRefStore.getComponent(targetRef,Player.getComponentType());
                CustomUIPage page = CardUtils.getPlayerCharacterView(targetRefStore,targetRef,player.getDisplayName(),plugin);
                executor.getWorld().execute(()->{
                    executor.getPageManager().openCustomPage(execRef,execStore,page);
                });
            });

        });
    }

    @Override
    public String getPermission() {
        return "charactercards.gui";
    }
}
