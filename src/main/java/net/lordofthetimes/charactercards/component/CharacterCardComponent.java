package net.lordofthetimes.charactercards.component;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class CharacterCardComponent implements Component<EntityStore> {

    public static ComponentType<EntityStore, CharacterCardComponent> TYPE;

    public static final BuilderCodec<CharacterCardComponent> CODEC = BuilderCodec.<CharacterCardComponent>builder(
            CharacterCardComponent.class,
            CharacterCardComponent::new
    ).append(
            new KeyedCodec<>("Name", Codec.STRING),(data, value) -> data.name = value,
            data -> data.name
    ).add().append(
            new KeyedCodec<>("Age", Codec.STRING),(data, value) -> data.age = value,
            data -> data.age
    ).add().append(
            new KeyedCodec<>("Race", Codec.STRING),(data, value) -> data.race = value,
            data -> data.race
    ).add().append(
            new KeyedCodec<>("Gender", Codec.STRING),(data, value) -> data.gender = value,
            data -> data.gender
    ).add().append(
            new KeyedCodec<>("Description", Codec.STRING),(data, value) -> data.description = value,
            data -> data.description
    ).add().append(
            new KeyedCodec<>("Lore", Codec.STRING),(data, value) -> data.lore = value,
            data -> data.lore
    ).add().build();

    private String name;
    private String age;
    private String race;
    private String gender;
    private String description;
    private String lore;

    public CharacterCardComponent(String name, String age, String race, String gender, String description, String lore) {
        this.name = name;
        this.age = age;
        this.race = race;
        this.gender = gender;
        this.description = description;
        this.lore = lore;
    }

    public CharacterCardComponent() {
        this.name = "None";
        this.age = "None";
        this.race = "None";
        this.gender = "None";
        this.description = "None";
        this.lore = "None";
    }

    public static ComponentType<EntityStore, CharacterCardComponent> getComponentType(){
        return TYPE;
    }

    @Override
    public Component<EntityStore> clone() {
        CharacterCardComponent character = new CharacterCardComponent();
        character.name = this.name;
        character.age = this.age;
        character.race = this.race;
        character.gender = this.gender;
        character.description = this.description;
        character.lore = this.lore;
        return character;
    }

    // Getters
    public String getName() { return name; }
    public String getAge() { return age; }
    public String getRace() { return race; }
    public String getGender() { return gender; }
    public String getDescription() { return description; }
    public String getLore() { return lore; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(String age) { this.age = age; }
    public void setRace(String race) { this.race = race; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDescription(String description) { this.description = description; }
    public void setLore(String lore) { this.lore = lore; }
}
