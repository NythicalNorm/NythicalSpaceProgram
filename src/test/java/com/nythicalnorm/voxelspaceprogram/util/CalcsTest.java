package com.nythicalnorm.voxelspaceprogram.util;

import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.junit.jupiter.api.Test;

class CalcsTest {
    @Test
    void planetDimPosToNormalizedVectorTest() {
        Vector3d LastPlanetPos = new Vector3d(0f, Double.NEGATIVE_INFINITY, 0f);
        for (int i = -100; i <= 100; i += 10) {
            Vec3 pos = new Vec3(0, 0, i);
            Vector3d planetPos = Calcs.planetDimPosToNormalizedVector(pos, 6371000, new Quaternionf(), false);
            System.out.println("z:" + pos.z + "Pos = " + planetPos);
            //assertTrue(LastPlanetPos.y < planetPos.y);
            LastPlanetPos = planetPos;
        }
    }
}