package net.lordofthetimes.charactercards.service;

import com.hypixel.hytale.math.vector.Vector3d;
import net.lordofthetimes.charactercards.PluginConfig;
import net.lordofthetimes.charactercards.component.LocalChatComponent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LocalChatService {

    private final ConcurrentHashMap<UUID,LocalChatComponent> localChatComponents = new ConcurrentHashMap<>();

    public Boolean loadLocalChatComponent(UUID uuid, PluginConfig config) {
        LocalChatComponent component = new LocalChatComponent();
        component.setForced(config.isLocalChatForced());

        localChatComponents.put(uuid,component);
        return  true;
    }

    public LocalChatComponent getLocalChatComponent(UUID uuid) {
        if(localChatComponents.containsKey(uuid)) return localChatComponents.get(uuid);

        LocalChatComponent component = new LocalChatComponent();
        localChatComponents.put(uuid,component);

        return  component;
    }

    public void setLocalChatComponent(UUID uuid, LocalChatComponent component) {
        localChatComponents.put(uuid,component);
    }

    private Vector3d location = new Vector3d(0,0,0);

    public void setTestPoint(Vector3d location){
        this.location = location.clone();
    }

    public Vector3d getTestPoint(){
        return this.location;
    }

    private Boolean testMode = false;

    public Boolean getTestMode() {
        return testMode;
    }

    public void setTestMode(Boolean testMode) {
        this.testMode = testMode;
    }
}
