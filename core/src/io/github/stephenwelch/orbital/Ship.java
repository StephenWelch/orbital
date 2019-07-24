package io.github.stephenwelch.orbital;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.List;

public class Ship implements Renderable, GameEntity {

    private final Vector2[] vertices = new Vector2[] {
            new Vector2(0.0f, 5.9475f),
            new Vector2(0.0f, -5.9475f),
            new Vector2(9.3092f, 0.0f)
    };


    private final World world;

    private Body body = null;
    private Fixture fixture = null;

    public Ship(World world) {
        this.world = world;
    }

    @Override
    public void create() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(200.0f, 150.0f);
        bodyDef.angle = 0.0f;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void update() {
        float torque = 500.0f;
        float force = 500.0f;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.applyTorque(torque, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.applyTorque(-torque, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            body.applyForceToCenter(force * (float)Math.cos(body.getAngle()), force * (float)Math.sin(body.getAngle()), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            body.applyForceToCenter(-force * (float)Math.cos(body.getAngle()), -force * (float)Math.sin(body.getAngle()), true);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(ShapeRenderer renderer) {
//        Gdx.app.debug("SHIP", "Rendering ship");
//        Gdx.app.debug("SHIP", "Position: " + body.getPosition() + "\tAngle: " + body.getAngle());
        Vector2[] translatedVertices = translate(body.getPosition(), body.getAngle(), vertices);
        renderer.triangle(translatedVertices[0].x, translatedVertices[0].y, translatedVertices[1].x, translatedVertices[1].y, translatedVertices[2].x, translatedVertices[2].y);
    }

    @Override
    public ShapeRenderer.ShapeType getShapeType() {
        return ShapeRenderer.ShapeType.Filled;
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public List<Renderable> getSubComponents() {
        return null;
    }

    /**
     *
     * @param translation
     * @param rotation
     * @param vectors
     * @return An array of brand-new Vector2 objects with the translation and rotation applied.
     */
    public Vector2[] translate(Vector2 translation, float rotation, Vector2 ... vectors) {
        Vector2[] translatedVectors = new Vector2[vectors.length];
        for(int index = 0; index < vectors.length; index++) {
            // add() and rotate() modify the vector itself, so we apply our changes to the new vector we have created instead.
            translatedVectors[index] = new Vector2().add(vectors[index]).rotateRad(rotation).add(translation);
        }
        return translatedVectors;
    }

}
