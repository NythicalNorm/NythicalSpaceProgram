package com.nythicalnorm.nythicalSpaceProgram.common;

import com.nythicalnorm.nythicalSpaceProgram.solarsystem.OrbitalElements;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.PlanetaryBody;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class Planets {
    public static HashMap<String, PlanetaryBody> PLANETARY_BODIES = new HashMap<>();

    public static PlanetaryBody NILA = PLANETARY_BODIES.put("nila", new PlanetaryBody(new OrbitalElements(
            5,5.240010829674768E+00,6.476694128611285E-02,
            3.081359034620368E+02,1.239837028145578E+02,0,
            1000,null),
            ResourceLocation.parse("nythicalspaceprogram:textures/planets/moon_axis.png")));

    public static PlanetaryBody getPlanet(String key) {
        return PLANETARY_BODIES.get(key);
    }
}
