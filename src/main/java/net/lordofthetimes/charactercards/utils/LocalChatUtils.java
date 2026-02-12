package net.lordofthetimes.charactercards.utils;


import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.Message;
import net.lordofthetimes.charactercards.PluginConfig;
import net.lordofthetimes.charactercards.component.LocalChatComponent;
import net.lordofthetimes.charactercards.service.LocalChatService;

import java.util.UUID;

public class LocalChatUtils {

    private static final LocalChatService service = new LocalChatService();

    public static Boolean loadLocalChatComponent(UUID uuid, PluginConfig config){
        return service.loadLocalChatComponent(uuid,config);
    }

    public static LocalChatComponent getComponent(UUID uuid){
        return service.getLocalChatComponent(uuid);
    }

    public static void setComponent(UUID uuid, LocalChatComponent component){
        service.setLocalChatComponent(uuid,component);
    }

    public static Message localChatCustomPrefix(PluginConfig config, String displayName,String content){
        String prefix = config.getCustomPrefix().replace("%player%",displayName);
        return Message.join(Message.raw(prefix),Message.raw(content));
    }

    public static Message localChatDistancePrefix(double distance, String displayName,String content){
        String prefix = "[%.1fm] %s: ".formatted(distance,displayName);
        return Message.join(Message.raw(prefix),Message.raw(content));
    }

    public static void setTestPoint(Vector3d location){
        service.setTestPoint(location);
    }

    public static Vector3d getTestPoint(){
        return service.getTestPoint();
    }

    public static Boolean getTestMode() {
        return service.getTestMode();
    }

    public static void setTestMode(Boolean testMode) {
        service.setTestMode(testMode);
    }

}
