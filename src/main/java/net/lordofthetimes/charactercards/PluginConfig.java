package net.lordofthetimes.charactercards;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.map.MapCodec;

import java.util.HashMap;
import java.util.Map;

public class PluginConfig {
    public static final BuilderCodec<PluginConfig> CODEC = BuilderCodec.builder(PluginConfig.class, PluginConfig::new)
            .append(new KeyedCodec<>("Name",new MapCodec<>(Codec.STRING, HashMap::new)),
                    (config, value) -> config.name = value,
                    (config) -> config.name).add()
            .append(new KeyedCodec<>("Age",new MapCodec<>(Codec.STRING, HashMap::new)),
                    (config, value) -> config.age = value,
                    (config) -> config.age).add()
            .append(new KeyedCodec<>("Race",new MapCodec<>(Codec.STRING, HashMap::new)),
                    (config, value) -> config.race = value,
                    (config) -> config.race).add()
            .append(new KeyedCodec<>("Gender",new MapCodec<>(Codec.STRING, HashMap::new)),
                    (config, value) -> config.gender = value,
                    (config) -> config.gender).add()
            .append(new KeyedCodec<>("Description",new MapCodec<>(Codec.STRING, HashMap::new)),
                    (config, value) -> config.description = value,
                    (config) -> config.description).add()
            .append(new KeyedCodec<>("Lore",new MapCodec<>(Codec.STRING, HashMap::new)),
                    (config, value) -> config.lore = value,
                    (config) -> config.lore).add()
            .build();

    private Map<String, String> name = new HashMap<>(Map.of(
            "Enabled_Comment", "Enables showing and setting name",
            "Enabled", "true",

            "Message_Comment", "What message to use for Name, %value% is a placeholder",
            "Message", "Name : %value%"
    ));

    private Map<String, String> age = new HashMap<>(Map.of(
            "Enabled_Comment", "Enables showing and setting age",
            "Enabled", "true",

            "Message_Comment", "What message to use for Age, %value% is a placeholder",
            "Message", "Age : %value%"
    ));

    private Map<String, String> race = new HashMap<>(Map.of(
            "Enabled_Comment", "Enables showing and setting race",
            "Enabled", "true",

            "Message_Comment", "What message to use for Race, %value% is a placeholder",
            "Message", "Race : %value%"
    ));

    private Map<String, String> gender = new HashMap<>(Map.of(
            "Enabled_Comment", "Enables showing and setting gender",
            "Enabled", "true",

            "Message_Comment", "What message to use for Gender, %value% is a placeholder",
            "Message", "Gender : %value%\n"
    ));

    private Map<String, String> description = new HashMap<>(Map.of(
            "Enabled_Comment", "Enables showing and setting description",
            "Enabled", "true",

            "Message_Comment", "What message to use for Description, %value% is a placeholder",
            "Message", "Description : %value%\n"
    ));

    private Map<String, String> lore = new HashMap<>(Map.of(
            "Enabled_Comment", "Enables showing and setting lore",
            "Enabled", "true",

            "Message_Comment", "What message to use for Lore, %value% is a placeholder",
            "Message", "Lore : %value%\n",

            "Color_Comment","Currently unused",
            "Color", ""
    ));

    private Map<String, String> localChat = new HashMap<>() {{
        put("Enabled_Comment", "If local chat feature is enabled");
        put("Enabled", "true");

        put("Distance_Comment", "Maximum distance between players for local chat to show");
        put("Distance", "100");

        put("Mode_Comment", "Should the local chat be optional with command /local or should it be forced and require /shout to talk globally. Modes: COMMAND, FORCED . If wrong values is set defaults to COMMAND ");
        put("Mode", "COMMAND");

        put("PrefixMode_Comment", "Defaults to CUSTOM if wrong or not specified, correct values : DISTANCE, CUSTOM, NONE. DISTANCE or NONE recommended for FORCED mode");
        put("PrefixMode", "custom");

        put("CustomPrefix_Comment", "What prefix will display when prefixMode is set to custom, %player% is placeholder for the display name");
        put("CustomPrefix", "[Local] %player%");

        put("PrefixColor_Comment", "Currently unused");
        put("PrefixColor", "");
    }};



    public String formatName(String value){
        return name.get("Message").replace("%value%",value);
    }
    public String formatAge(String value){
        return age.get("Message").replace("%value%",value);
    }
    public String formatRace(String value){
        return race.get("Message").replace("%value%",value);
    }
    public String formatGender(String value){
        return gender.get("Message").replace("%value%",value);
    }
    public String formatDescription(String value){
        return description.get("Message").replace("%value%",value);
    }
    public String formatLore(String value){
        return lore.get("Message").replace("%value%",value);
    }

    public boolean isNameEnabled() {
        return Boolean.parseBoolean(name.getOrDefault("Enabled", "false"));
    }

    public boolean isAgeEnabled() {
        return Boolean.parseBoolean(age.getOrDefault("Enabled", "false"));
    }

    public boolean isRaceEnabled() {
        return Boolean.parseBoolean(race.getOrDefault("Enabled", "false"));
    }

    public boolean isGenderEnabled() {
        return Boolean.parseBoolean(gender.getOrDefault("Enabled", "false"));
    }

    public boolean isDescriptionEnabled() {
        return Boolean.parseBoolean(description.getOrDefault("Enabled", "false"));
    }

    public boolean isLoreEnabled() {
        return Boolean.parseBoolean(lore.getOrDefault("Enabled", "false"));
    }


}
