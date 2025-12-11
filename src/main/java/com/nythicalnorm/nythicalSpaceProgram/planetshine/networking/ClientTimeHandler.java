package com.nythicalnorm.nythicalSpaceProgram.planetshine.networking;

import com.nythicalnorm.nythicalSpaceProgram.util.CircularBuffer;
import net.minecraft.Util;
import net.minecraft.util.Mth;

public class ClientTimeHandler {
    private static double clientSideSolarSystemTime = 0d;
    private static CircularBuffer<ServerTimeFrame> serverTimeFrames = new CircularBuffer<>(5);
    private static ServerTimeFrame prevTimeFrame;

    private static double lerpStep = 0d;

    public static double lastUpdatedTimeWarpPerSec = 0;

    public static void UpdateState(double serverTime, double TimePassedPerSec){
        serverTimeFrames.add(new ServerTimeFrame(Util.getMillis(), serverTime, TimePassedPerSec));

        lastUpdatedTimeWarpPerSec = TimePassedPerSec;
    }

    public static double calculateCurrentTime(float partialTick) {
        if (lerpStep >= 1) {
            serverTimeFrames.get();
            lerpStep = 0;
        }
        if (prevTimeFrame != null && !serverTimeFrames.isEmpty()) {
            ServerTimeFrame nextFrame = serverTimeFrames.peek();
            double timeDiff = nextFrame.recievedTimestamp - prevTimeFrame.recievedTimestamp;

            clientSideSolarSystemTime = Mth.lerp(lerpStep, prevTimeFrame.serverSideTime, nextFrame.serverSideTime);
            lerpStep += partialTick;


        } else if (!serverTimeFrames.isEmpty() && prevTimeFrame == null){
            prevTimeFrame = serverTimeFrames.get();
        }

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
