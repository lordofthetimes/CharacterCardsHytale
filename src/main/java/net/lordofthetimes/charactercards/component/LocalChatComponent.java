package net.lordofthetimes.charactercards.component;

public class LocalChatComponent{

    private boolean forced = false;
    private boolean enabled = false;

    public boolean shouldSendLocalMessage() {
        return forced || enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled(){
        return this.enabled;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public boolean isForced(){
        return this.forced;
    }

    public LocalChatComponent clone() {
        LocalChatComponent local = new LocalChatComponent();
        local.enabled = enabled;
        local.forced = forced;
        return  local;
    }
}
