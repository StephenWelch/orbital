package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import io.github.stephenwelch.orbital.engine.*;

import java.util.List;

public class Planet implements Renderable, GameEntity, GravitationalBody {

    private final float radius;
    private final float mass;

    private Body body = null;
    private Fixture fixture = null;

    public Planet(float radius, float mass) {
        this.radius = radius;
        this.mass = mass;
    }

    @Override
    public void create() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Renderer.CAMERA_WIDTH / 2.0f, Renderer.CAMERA_HEIGHT / 2.0f);
        bodyDef.angle = 0.0f;

        body = PhysicsManager.getInstance().getWorld().createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = mass / getArea();
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        fixture = body.createFixture(fixtureDef);

        shape.dispose();

        PhysicsManager.getInstance().registerGravitationalBody(this);

    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.circle(body.getPosition().x, body.getPosition().y, radius);
    }

    @Override
    public ShapeRenderer.ShapeType getShapeType() {
        return ShapeRenderer.ShapeType.Filled;
    }

    @Override
    public Color getColor() {
        return Color.CORAL;
    }

    @Override
    public List<Renderable> getSubComponents() {
        return null;
    }

    public float getArea() {
        return (float)Math.PI * radius * radius;
    }

    @Override
    public float getMass() {
        return body.getMass();
    }

    @Override
    public Body getBody() {
        return body;
    }
}
