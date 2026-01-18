package net.lordofthetimes.charactercards.service;

import net.lordofthetimes.charactercards.PlayerCharacter;
import net.lordofthetimes.charactercards.repository.ICharacterRepository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CharacterService {

    private final ConcurrentHashMap<UUID, PlayerCharacter> cache = new ConcurrentHashMap<>();
    private final ICharacterRepository repository;


    public CharacterService(ICharacterRepository repository) {
        this.repository = repository;
    }

    public PlayerCharacter getPlayerCharacter(UUID uuid){
        return cache.get(uuid);
    }

    public Boolean loadPlayerCharacter(UUID uuid){
        PlayerCharacter character = repository.getPlayerData(uuid);
        cache.put(uuid,character);
        return true;
    }
}
