package io.github.stephenwelch.orbital.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsManager implements GameEntity {

    private static PhysicsManager instance = new PhysicsManager();

    private World world = new World(new Vector2(0, 0), true);

    private Box2DDebugRenderer debugRenderer;
    private Camera debugRenderCamera;
    private boolean renderPhysics = false;

    private PhysicsManager() {};

    @Override
    public void create() {
        debugRenderer = new Box2DDebugRenderer();
        debugRenderCamera = Renderer.getInstance().getCamera();
    }

    @Override
    public void update() {
        if(renderPhysics && debugRenderCamera != null) {
            debugRenderer.render(world, debugRenderCamera.combined);
        }
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    @Override
    public void dispose() {

    }

    public PhysicsManager setRenderPhysics(boolean enabled) {
        renderPhysics = enabled;
        return this;
    }

    public World getWorld() {
        return world;
    }

    public static PhysicsManager getInstance() {
        return instance;
    }

}
