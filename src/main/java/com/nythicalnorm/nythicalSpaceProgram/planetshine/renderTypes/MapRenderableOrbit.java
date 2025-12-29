package com.nythicalnorm.nythicalSpaceProgram.planetshine.renderTypes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.Orbit;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.map.MapRenderer;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.map.OrbitDrawer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MapRenderableOrbit extends MapRenderable {
    Orbit orbitOf;

    public MapRenderableOrbit(MapRelativeState mapRelativeState, Orbit orbitOf, Orbit parentBody) {
        super(mapRelativeState, parentBody);
        this.orbitOf = orbitOf;
    }

    @Override
    public Vector3f render(PoseStack poseStack, Matrix4f projectionMatrix) {
        OrbitDrawer.drawOrbit(orbitOf, MapRenderer.SCALE_FACTOR, poseStack, projectionMatrix);
        return null;
    }
}
