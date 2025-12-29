package com.nythicalnorm.nythicalSpaceProgram.planetshine.renderTypes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.Orbit;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.planet.PlanetaryBody;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.map.MapRenderer;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.renderers.PlanetRenderer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MapRenderablePlanet extends MapRenderable {
    protected PlanetaryBody planetBody;

    public MapRenderablePlanet(PlanetaryBody planetBody, MapRelativeState mapRelativeState, @Nullable Orbit parentBody) {
        super(mapRelativeState, parentBody);
        this.planetBody = planetBody;
    }

    @Override
    public Vector3f render(PoseStack poseStack, Matrix4f projectionMatrix) {
        Vector3f pos = getPos(planetBody, MapRenderer.getCurrentFocusedBody());
        poseStack.translate(pos.x,pos.y, pos.z);

        float PlanetSize = (float) (2f* MapRenderer.SCALE_FACTOR*planetBody.getRadius());
        poseStack.scale(PlanetSize, PlanetSize, PlanetSize);
        poseStack.mulPose(planetBody.getRotation());

        PlanetRenderer.render(planetBody, poseStack, projectionMatrix);

        return pos;
    }

    public PlanetaryBody getBody() {
        return planetBody;
    }
}
