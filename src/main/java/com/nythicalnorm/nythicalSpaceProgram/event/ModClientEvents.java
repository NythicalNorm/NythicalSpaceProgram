package com.nythicalnorm.nythicalSpaceProgram.event;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NythicalSpaceProgram.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)

@OnlyIn(Dist.CLIENT)
public class ModClientEvents {
//    @SubscribeEvent
//    public static void OnRenderLevelEvent(RenderLevelStageEvent event) {
//        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
//            ModRenderSky.renderSkylight(event.getStage(), event.getLevelRenderer(), event.getPoseStack(), event.getProjectionMatrix(),
//                    event.getRenderTick(), event.getCamera(), event.getFrustum());
//        }
//    }
}
