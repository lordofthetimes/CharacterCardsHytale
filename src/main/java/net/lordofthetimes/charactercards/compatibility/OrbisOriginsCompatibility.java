package net.lordofthetimes.charactercards.compatibility;

import com.hexvane.orbisorigins.data.PlayerSpeciesData;
import com.hexvane.orbisorigins.species.SpeciesData;
import com.hexvane.orbisorigins.species.SpeciesRegistry;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class OrbisOriginsCompatibility {

    public static boolean supportEnabled = false;

    public static String getSpeciesName(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull World world){

        String speciesId = PlayerSpeciesData.getSelectedSpeciesId(ref,store,world);

        SpeciesData species = SpeciesRegistry.getSpecies(speciesId);

        return  species.getDisplayName();
    }

}
