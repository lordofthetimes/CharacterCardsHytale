package net.lordofthetimes.charactercards.hytale.command.charactersubcmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.adapters.character.CharacterGuiAdapter;
import net.lordofthetimes.charactercards.hytale.UI.CharacterCardEdit;
import net.lordofthetimes.charactercards.service.CharacterService;

import javax.annotation.Nonnull;
import java.awt.*;

public class CharacterEditCommand extends AbstractPlayerCommand {

    private final CharacterGuiAdapter adapter;
    private final OptionalArg<String> playerArg;

    public CharacterEditCommand(CharacterService characterService){
        super("edit","Command for editing Character Cards");
        this.adapter = new CharacterGuiAdapter(characterService);
        playerArg = this.withOptionalArg("player",
                "Whose character card edit menu will open. Leaving it empty will open it for player running the command", ArgTypes.STRING);
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> execStore, @Nonnull Ref<EntityStore> execRef,
                           @Nonnull PlayerRef execPlayerRef, @Nonnull World world) {
        world.execute(()->{
            Player executor = execStore.getComponent(execRef,Player.getComponentType());

            if(playerArg.get(context) == null || playerArg.get(context).equals("")) {
                InteractiveCustomUIPage<CharacterCardEdit.Data> page = adapter.editPlayerCharacter(execStore,execRef,executor.getDisplayName(),execPlayerRef.getUuid());
                executor.getPageManager().openCustomPage(execRef,execStore,page);
                return;
            }
            if(!executor.hasPermission("charactercards.admin")) return;
            PlayerRef target = Universe.get().getPlayer(playerArg.get(context), NameMatching.EXACT_IGNORE_CASE);
            if(target == null){
                executor.sendMessage(Message.raw("This player is offline or does not exist").color(Color.YELLOW));
                return;
            }
            Universe.get().getWorld(target.getWorldUuid()).execute(()->{
                Ref<EntityStore> targetRef = target.getReference();
                Store<EntityStore> targetRefStore = targetRef.getStore();
                Player player = targetRefStore.getComponent(targetRef,Player.getComponentType());
                InteractiveCustomUIPage<CharacterCardEdit.Data> page = adapter.editPlayerCharacter(targetRefStore,targetRef,player.getDisplayName(),targetRefStore.getComponent(targetRef, UUIDComponent.getComponentType()).getUuid());
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
