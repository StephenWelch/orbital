package io.github.stephenwelch.orbital.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class PhysicsManager implements GameEntity, Renderable {

    private static PhysicsManager instance = new PhysicsManager();
    public static final float G = (float)6.674E-11;

    private World world = new World(new Vector2(0, 0), true);

    private Box2DDebugRenderer debugRenderer;
    private Camera debugRenderCamera;
    private boolean renderPhysics = false;
    private List<GravitationalBody> gravitationalBodies = new ArrayList<>();
    private List<Renderable> debugVectors = new ArrayList<>();

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

        debugVectors.clear();
        for(GravitationalBody source : gravitationalBodies) {
            for(GravitationalBody target : gravitationalBodies) {
                Vector2 distance = source.getBody().getPosition().sub(target.getBody().getPosition());

                if(distance.len() > 0) {
                    float angle = MathUtils.degreesToRadians * distance.angle();
                    float force = (G * source.getMass() * target.getMass()) / (distance.len() * distance.len());
                    Vector2 gravity = new Vector2((float)Math.cos(angle) * force, (float)Math.sin(angle) * force).scl(1.0f);
                    target.getBody().applyForceToCenter(gravity, true);
//                    Gdx.app.debug("PHYSICS", "Force: " + force + "\tAngle: " + angle + "\tDistance: " + distance);
                    debugVectors.add(new RenderableVector2(target.getBody().getPosition(), gravity).setColor(Color.GREEN));
//                    debugVectors.add(new RenderableVector2(target.getBody().getPosition(), distance).setColor(Color.BLUE));
                }
            }
        }

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    @Override
    public void dispose() {

    }

    public void registerGravitationalBody(GravitationalBody body) {
        gravitationalBodies.add(body);
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

    @Override
    public void render(ShapeRenderer renderer) {
        if(renderPhysics) {

        }
    }

    @Override
    public ShapeRenderer.ShapeType getShapeType() {
        return null;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public List<Renderable> getSubComponents() {
        if(renderPhysics) {
            return debugVectors;
        } else {
            return null;
        }
    }
}
