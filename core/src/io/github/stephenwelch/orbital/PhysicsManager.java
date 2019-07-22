package io.github.stephenwelch.orbital;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsManager implements GameEntity {

    private World world = new World(new Vector2(0, 0), true);

    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private Camera debugRenderCamera = null;
    private boolean renderPhysics = false;

    public PhysicsManager(Camera camera) {
        this.debugRenderCamera = camera;
    }

    @Override
    public void create() {

    }

    @Override
    public void update() {
        if(renderPhysics && debugRenderCamera != null) {
            debugRenderer.render(world, debugRenderCamera.combined);
        }
    }

    @Override
    public void dispose() {

    }

    public PhysicsManager setRenderPhysics(boolean enabled) {
        renderPhysics = enabled;
        return this;
    }

}
