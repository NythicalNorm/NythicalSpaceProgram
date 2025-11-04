package com.nythicalnorm.nythicalSpaceProgram.planetshine;

import com.nythicalnorm.nythicalSpaceProgram.common.Planets;
import com.nythicalnorm.nythicalSpaceProgram.network.PacketHandler;
import com.nythicalnorm.nythicalSpaceProgram.network.ServerBoundTimeWarpChange;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CelestialStateSupplier {
    private static double lastUpdatedSolarSystemTime = 0;
    private static double lastUpdatedTimePassedPerSec = 0;
    //private static long UpdateRecievedTime = 0;

    public static void UpdateState(double currentTime, double TimePassedPerSec){
        lastUpdatedSolarSystemTime = currentTime;
        lastUpdatedTimePassedPerSec = TimePassedPerSec;
        //UpdateRecievedTime = System.currentTimeMillis();
    }

    public static Vec3 getPlanetPositon(String PlanetKey, float partialTicks) {
        double timeChanged = (partialTicks/20);
        return Planets.getPlanet(PlanetKey).CalculateCartesianPosition(lastUpdatedSolarSystemTime + timeChanged);
    }

    public static void TryChangeTimeWarp(boolean DoInc) {
        double sign = 2;
        if (!DoInc) {
            sign = 0.5;
        }
        PacketHandler.sendToServer(new ServerBoundTimeWarpChange(sign * lastUpdatedTimePassedPerSec));
    }
}
