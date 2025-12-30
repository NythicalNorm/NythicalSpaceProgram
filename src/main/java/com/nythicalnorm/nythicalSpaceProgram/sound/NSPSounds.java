package com.nythicalnorm.nythicalSpaceProgram.sound;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NSPSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NythicalSpaceProgram.MODID);

    public static final RegistryObject<SoundEvent> HANDHELD_PROPELLER_START = registerSoundEvents("propeller_start");
    public static final RegistryObject<SoundEvent> HANDHELD_PROPELLER_RUN = registerSoundEvents("propeller_run");
    public static final RegistryObject<SoundEvent> HANDHELD_PROPELLER_STOP = registerSoundEvents("propeller_stop");

    //public static final ForgeSoundType PROPELLER_SOUNDS = new ForgeSoundType(1f, 1f, NSPSounds.HANDHELD_PROPELLER_START);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent
                .createVariableRangeEvent(NythicalSpaceProgram.rl(name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
