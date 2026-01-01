package com.nythicalnorm.nythicalSpaceProgram.planetshine.networking;

import com.nythicalnorm.nythicalSpaceProgram.util.Calcs;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientTimeHandler {
    private static long clientSideSolarSystemTime = 0L;
    private static long serverSolarSystemTimeTarget = 0L;
    private static double lerpVar = 0d;

    private static float deltaTime = 0f;

    private static volatile long serverNewSolarSystemTimeTarget = 0L;
    public static volatile long lastUpdatedTimeWarpPerSec = 0;
    private static volatile boolean isServerUpdated = false;

    public static void UpdateState(long serverTime, long TimePassedPerSec){
        lastUpdatedTimeWarpPerSec = TimePassedPerSec;
        serverNewSolarSystemTimeTarget = serverTime;
        isServerUpdated = true;
    }

    public static long calculateCurrentTime() {
        if (isServerUpdated) {
            serverSolarSystemTimeTarget = serverNewSolarSystemTimeTarget;
            lerpVar = 0;
            isServerUpdated = false;
        }

        deltaTime = Minecraft.getInstance().getDeltaFrameTime();
        lerpVar = lerpVar + deltaTime;
        lerpVar = Mth.clamp(lerpVar, 0f, 1f);

        clientSideSolarSystemTime = lerpTime(lerpVar, clientSideSolarSystemTime, serverSolarSystemTimeTarget);

        return clientSideSolarSystemTime;
    }

    public static long lerpTime(double pDelta, long pStart, long pEnd) {
        double diff = (double) (pEnd - pStart);
        return pStart + (long) (pDelta * diff);
    }

    public static double getLastUpdatedTimeWarpPerSec() {
        return lastUpdatedTimeWarpPerSec;
    }

    public static long getClientSideSolarSystemTime() {
        return clientSideSolarSystemTime;
    }

    public static double solarSystemTimeInSec() {
        return Calcs.timeLongToDouble(clientSideSolarSystemTime);
    }
}
