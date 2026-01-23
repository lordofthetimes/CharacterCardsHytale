package net.lordofthetimes.charactercards.hytale.command.charactersubcmd;

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
import net.lordofthetimes.charactercards.adapters.character.CharacterGuiAdapter;
import net.lordofthetimes.charactercards.service.CharacterService;

import javax.annotation.Nonnull;
import java.awt.*;

public class CharacterGuiCommand extends AbstractPlayerCommand {

    private final CharacterGuiAdapter adapter;
    private final OptionalArg<String> playerArg;

    public CharacterGuiCommand(CharacterService characterService){
        super("gui","Main command for viewing Character Cards");
        this.adapter = new CharacterGuiAdapter(characterService);
        playerArg = this.withOptionalArg("player",
                "Whose character card open. Leaving it empty will open it for player running the command", ArgTypes.STRING);
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> execStore, @Nonnull Ref<EntityStore> execRef,
                           @Nonnull PlayerRef execPlayerRef, @Nonnull World world) {
        world.execute(()->{
            Player executor = execStore.getComponent(execRef,Player.getComponentType());

            if(playerArg.get(context) == null || playerArg.get(context).equals("")) {
                CustomUIPage page = adapter.getPlayerCharacter(execStore,execRef,executor.getDisplayName());
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
                CustomUIPage page = adapter.getPlayerCharacter(targetRefStore,targetRef,player.getDisplayName());
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
