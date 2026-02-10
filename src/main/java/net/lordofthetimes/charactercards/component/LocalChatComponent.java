package net.lordofthetimes.charactercards.component;

public class LocalChatComponent {

    private boolean forced = false;
    private boolean enabled = false;

    public boolean shouldSendLocalMessage() {
        return forced || enabled;
    }
}
