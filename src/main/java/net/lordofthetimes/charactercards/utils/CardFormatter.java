package net.lordofthetimes.charactercards.utils;

import com.hypixel.hytale.server.core.Message;
import net.lordofthetimes.charactercards.component.CharacterCardComponent;

import java.awt.*;

public class CardFormatter {

    public static String nameMessage = "Name: %value%";
    public static String ageMessage = "Age: %value%";
    public static String raceMessage = "Race: %value%";
    public static String genderMessage = "Gender: %value%";
    public static String descriptionMessage = "Description: %value%";
    public static String loreMessage = "Lore: %value%";

    public static String formatName(String value){
        return nameMessage.replace("%value%",value);
    }

    public static String formatAge(String value){
        return ageMessage.replace("%value%",value);
    }

    public static String formatRace(String value){
        return raceMessage.replace("%value%",value);
    }

    public static String formatGender(String value){
        return genderMessage.replace("%value%",value);
    }

    public static String formatDescription(String value){
        return descriptionMessage.replace("%value%",value);
    }

    public static String formatLore(String value){
        return loreMessage.replace("%value%",value);
    }

    public static Message formatChatCard(CharacterCardComponent character){
        Message message = Message.raw(String.format("---[%s's Character Card]---\n",character.getName()));

        message = Message.join(message,Message.raw(formatName(character.getName()) + "\n"));
        message = Message.join(message,Message.raw(formatAge(character.getAge()) + "\n"));
        message = Message.join(message,Message.raw(formatRace(character.getRace()) + "\n"));
        message = Message.join(message,Message.raw(formatGender(character.getGender()) + "\n"));
        message = Message.join(message,Message.raw(formatDescription(character.getDescription()) + "\n"));
        message = Message.join(message,Message.raw(formatLore(character.getLore())));
        message.color(Color.GREEN);

        return message;
    }

}
