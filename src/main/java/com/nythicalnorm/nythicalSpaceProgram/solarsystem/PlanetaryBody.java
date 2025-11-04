
package com.nythicalnorm.nythicalSpaceProgram.solarsystem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class PlanetaryBody {
    private final OrbitalElements orbitalElements;

    private Quaternionf northPoleDir;
    public ResourceLocation texture; //temp position

    public PlanetaryBody (OrbitalElements orbitalElements, ResourceLocation texture) {
        this.orbitalElements = orbitalElements;
        this.texture = texture;
    }

    public Vec3 CalculateCartesianPosition(double TimeElapsed) {

        return  orbitalElements.ToCartesian(TimeElapsed);
    }

    public Quaternionf getRotationAt(double TimeElapsed) {
        return northPoleDir; // placeHolder
    }
}
