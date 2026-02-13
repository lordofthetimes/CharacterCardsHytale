package net.lordofthetimes.charactercards.event;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fi.sulku.hytale.TinyMsg;
import net.lordofthetimes.charactercards.CharacterCards;
import net.lordofthetimes.charactercards.PluginConfig;
import net.lordofthetimes.charactercards.component.LocalChatComponent;
import net.lordofthetimes.charactercards.utils.LocalChatUtils;

public class LocalChat {

    private final CharacterCards plugin;

    public LocalChat(CharacterCards plugin) {
        this.plugin = plugin;
    }

    public void onPlayerChat(PlayerChatEvent event) {
        PluginConfig config = plugin.config.get();
        PlayerRef sender = event.getSender();
        Ref<EntityStore> senderRef = sender.getReference();
        Store<EntityStore> store = senderRef.getStore();

        LocalChatComponent localChat = LocalChatUtils.getComponent(sender.getUuid());

        if(!localChat.shouldSendLocalMessage()) return;

        event.setCancelled(true);

        World world = Universe.get().getWorld(sender.getWorldUuid());

        if(LocalChatUtils.getTestMode()){
            test(sender,store,senderRef, world,config,event.getContent());
        }
        scanAndSend(sender,store,senderRef, world,config,event.getContent());
    }

    public double distance(Vector3d sender, Vector3d target) {
        double dx = sender.getX() - target.getX();
        double dy = sender.getY() - target.getY();
        double dz = sender.getZ() - target.getZ();
        return dx*dx + dy*dy + dz*dz;
    }

    public Message getMessage(String displayName, PluginConfig config, String content, double distance,boolean isSender){
        if(config.isLocalChatNoPrefix()){
            return  Message.join(TinyMsg.parse("<bold><green>" + displayName + "</green></bold>: "),Message.raw(content));
        }else if(config.isLocalChatDistancePrefix()){
            if(isSender) return Message.join(TinyMsg.parse("<bold><green>" + displayName + "</green></bold>: "),Message.raw(content));
            return LocalChatUtils.localChatDistancePrefix(distance,displayName,content);
        }else{
            return LocalChatUtils.localChatCustomPrefix(config,displayName,content);
        }
    }

    private void test(PlayerRef sender,Store<EntityStore> store,Ref<EntityStore> senderRef, World world, PluginConfig config,String content){

        world.execute(()->{
            int maxDistance = config.getDistance();

            TransformComponent transformSender = store.getComponent(senderRef, TransformComponent.getComponentType());
            Message displayName = store.getComponent(senderRef, DisplayNameComponent.getComponentType()).getDisplayName();

            double distance = distance(transformSender.getPosition(),LocalChatUtils.getTestPoint());
            double realDistance = Math.sqrt(distance);
            Vector3d senderPos = transformSender.getPosition();
            Vector3d targetPos = LocalChatUtils.getTestPoint();

            sender.sendMessage(TinyMsg.parse(
                    "<yellow>[TEST MODE]</yellow><green> Sender: %s %s %s | Target: %s %s %s"
                            .formatted(
                                    senderPos.getX(), senderPos.getY(), senderPos.getZ(),
                                    targetPos.getX(), targetPos.getY(), targetPos.getZ()
                            )
            ));
            sender.sendMessage(TinyMsg.parse("<yellow>[TEST MODE]</yellow><green> Distance to target is %s blocks".formatted(realDistance)));
            if(distance <= maxDistance * maxDistance){
                sender.sendMessage(TinyMsg.parse("<yellow>[TEST MODE]</yellow><green> Target is in distance"));
                sender.sendMessage(
                        Message.join(
                                TinyMsg.parse("<yellow>[TEST MODE]</yellow><green> Message received by target: "),
                                getMessage(displayName.getRawText(),config,content,realDistance,false)
                        )
                );

            }
            else{
                sender.sendMessage(TinyMsg.parse("<yellow>[TEST MODE]</yellow><green> Target is not in distance"));
            }

        });

    }

    private void scanAndSend(PlayerRef sender,Store<EntityStore> store,Ref<EntityStore> senderRef, World world, PluginConfig config,String content){
        world.execute(()->{

            int maxDistance = config.getDistance();

            TransformComponent transformSender = store.getComponent(senderRef, TransformComponent.getComponentType());
            Message displayName = store.getComponent(senderRef, DisplayNameComponent.getComponentType()).getDisplayName();
            //log message sent on local chat
            plugin.getLogger().atInfo().log("%s: %s".formatted(displayName.getRawText(),content));

            world.getPlayerRefs().stream().forEach(playerRef -> {
                TransformComponent transformTarget = store.getComponent(playerRef.getReference(), TransformComponent.getComponentType());
                double distance = distance(transformSender.getPosition(),transformTarget.getPosition());
                double realDistance = Math.sqrt(distance);
                if(distance <= maxDistance * maxDistance){
                    playerRef.sendMessage(
                            getMessage(displayName.getRawText(),config,content,realDistance, playerRef.getUuid().equals(sender.getUuid()))
                    );
                }
            });

        });
    }

}
