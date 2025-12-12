package com.nythicalnorm.nythicalSpaceProgram.planetshine.networking;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class ClientTimeHandler {
    private static double clientSideSolarSystemTime = 0d;
    //private static CircularBuffer<ServerTimeFrame> serverTimeFrames = new CircularBuffer<>(5);
    private static double lerpVar = 0d;
    private static double serverSolarSystemTimeTarget = 0d;

    private static float deltaTime = 0f;

    private static volatile double serverNewSolarSystemTimeTarget = 0d;
    public static volatile double lastUpdatedTimeWarpPerSec = 0;
    private static volatile boolean isServerUpdated = false;


    public static void UpdateState(double serverTime, double TimePassedPerSec){
        //serverTimeFrames.add(new ServerTimeFrame(Util.getMillis(), serverTime, TimePassedPerSec));
        lastUpdatedTimeWarpPerSec = TimePassedPerSec;
        serverNewSolarSystemTimeTarget = serverTime;
        isServerUpdated = true;
    }

    public static double calculateCurrentTime(float partialTick) {
        if (isServerUpdated) {
            serverSolarSystemTimeTarget = serverNewSolarSystemTimeTarget;
            lerpVar = 0;
            isServerUpdated = false;
        }

        deltaTime = Minecraft.getInstance().getDeltaFrameTime();
        lerpVar = lerpVar + deltaTime;

        lerpVar = Mth.clamp(lerpVar, 0f, 1f);

        //NythicalSpaceProgram.log("lerpvar: " + lerpVar);
        clientSideSolarSystemTime = Mth.lerp(lerpVar, clientSideSolarSystemTime, serverSolarSystemTimeTarget);


        return clientSideSolarSystemTime;
    }

    public static double getLastUpdatedTimeWarpPerSec() {
        return lastUpdatedTimeWarpPerSec;
    }

    public static double getClientSideSolarSystemTime() {
        return clientSideSolarSystemTime;
    }

    private static class ServerTimeFrame {
        final long recievedTimestamp;
        final double serverSideTime;
        final double timeWarpPerSec;

        public ServerTimeFrame(long recievedTimestamp, double serverSideTime, double timewarpPerSec) {
            this.recievedTimestamp = recievedTimestamp;
            this.serverSideTime = serverSideTime;
            this.timeWarpPerSec = timewarpPerSec;
        }
    }
}
