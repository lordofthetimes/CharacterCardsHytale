package net.lordofthetimes.charactercards.component;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.w3c.dom.html.HTMLOListElement;

import javax.annotation.Nullable;

public class LocalChatComponent implements Component<EntityStore> {

    public static ComponentType<EntityStore, LocalChatComponent> TYPE;

    private boolean forced = false;
    private boolean enabled = false;

    public boolean shouldSendLocalMessage() {
        return forced || enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public static ComponentType<EntityStore, LocalChatComponent> getComponentType(){
        return TYPE;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        LocalChatComponent local = new LocalChatComponent();
        local.enabled = enabled;
        local.forced = forced;
        return  local;
    }
}
