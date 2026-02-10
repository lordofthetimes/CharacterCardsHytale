package net.lordofthetimes.charactercards.utils;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.lordofthetimes.charactercards.PluginConfig;
import net.lordofthetimes.charactercards.component.LocalChatComponent;

public class LocalChatUtils {

    public static Boolean loadLocalChatComponent(Store<EntityStore> store, Ref<EntityStore> ref, PluginConfig config){
        if(config.isLocalChatEnabled()){
            LocalChatComponent localChatComponent = store.ensureAndGetComponent(ref, LocalChatComponent.getComponentType());
            localChatComponent.setForced(config.isLocalChatForced());
            return true;
        }
        return false;
    }

}
