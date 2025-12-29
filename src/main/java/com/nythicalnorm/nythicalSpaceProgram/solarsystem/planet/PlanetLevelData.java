package com.nythicalnorm.nythicalSpaceProgram.solarsystem.planet;

import com.nythicalnorm.nythicalSpaceProgram.solarsystem.PlanetsProvider;
import net.minecraft.nbt.CompoundTag;

import java.util.Optional;

public class PlanetLevelData {
    private String planetName;

    public PlanetLevelData(String planetName) {
        this.planetName = planetName;
    }

    public PlanetLevelData() {
        this.planetName = "";
    }

    public String getPlanetName() {
        return planetName;
    }

    public PlanetaryBody getPlanetaryBody(PlanetsProvider planets) {
        return planets.getPlanet(planetName);
    }

    public double getAccelerationDueToGravity(PlanetsProvider planets) {
        PlanetaryBody plnt = planets.getPlanet(this.planetName);
        double g = plnt.getAccelerationDueToGravity();
        double adjustedg = g*0.1d*0.08d;
        return adjustedg;
    }

    public CompoundTag saveNBT(CompoundTag nbt) {
        nbt.putString("NSP.planetName", this.planetName);
        return nbt;
    }

    public void loadNBT(CompoundTag nbt) {
        this.planetName = nbt.getString("NSP.planetName");
    }
//
//    public void copyFrom(@NotNull PlanetLevelData oldStore) {
//        planetName = oldStore.planetName;
//    }
//
//    public void encode (FriendlyByteBuf buffer) {
//        buffer.writeUtf (this.planetName);
//    }
//
//    public void decode (FriendlyByteBuf buffer) {
//        this.planetName = buffer.readUtf();
//    }
}
