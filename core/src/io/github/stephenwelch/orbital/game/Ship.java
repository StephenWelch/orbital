package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.engine.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Ship implements Renderable, GameEntity {

    private final Vector2[] vertices = new Vector2[] {
            new Vector2(0.0f, 5.9475f),
            new Vector2(0.0f, -5.9475f),
            new Vector2(9.3092f, 0.0f)
    };
    private final Vector3 mainEngineThrustSource = new Vector3((vertices[0].x + vertices[1].x) / 2.0f, (vertices[0].y + vertices[1].y) / 2.0f, 180.0f);
    private final World world;

    private Body body = null;
    private Fixture fixture = null;

    private ParticleEffectsDef<ShipParticleEffects> particleEffects = new ParticleEffectsDef<>();

    private enum ShipParticleEffects {
        MAIN_ENGINE,
        LEFT_FORWARD_THRUSTER, RIGHT_FORWARD_THRUSTER,
        LEFT_BACK_THRUSTER, RIGHT_BACK_THRUSTER,
        LEFT_RETRO, RIGHT_RETRO;
    }

    public Ship(World world) {
        this.world = world;
    }

    @Override
    public void create() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(400.0f, 300.0f);
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

        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        // Add all particles to the same pool
        particleEffects.addParticlesToPool("particles/thrust_particle.p", ShipParticleEffects.values());

        String json = gson.toJson(particleEffects);
        try {
            FileWriter jsonWriter = new FileWriter(new File("ship.ppm"));
            jsonWriter.append(json);
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Gdx.app.log("SHIP", "Saved configuration file.");
        }

        particleEffects.create();
        Renderer.getInstance().registerParticleEffect(particleEffects.getEffect(ShipParticleEffects.MAIN_ENGINE));
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

            RendererEffect mainEngineThrust = particleEffects.getEffect(ShipParticleEffects.MAIN_ENGINE);
            mainEngineThrust.effect.setPosition(getMainEngineThrustSourcePosition().x, getMainEngineThrustSourcePosition().y);
            float angle = (float)Math.toDegrees(body.getAngle()) + mainEngineThrustSource.z;
            Renderer.rotateParticleEffect(mainEngineThrust.effect, angle);
            mainEngineThrust.start();
        } else {
            RendererEffect mainEngineThrust = particleEffects.getEffect(ShipParticleEffects.MAIN_ENGINE);
            mainEngineThrust.stop();
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
        Vector2[] translatedVertices = getVertexPositions();
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

    private Vector2[] getVertexPositions() {
        return translate(body.getPosition(), body.getAngle(), vertices);
    }

    private Vector2 getMainEngineThrustSourcePosition() {
        return translate(body.getPosition(), body.getAngle(), Util.truncateVector(mainEngineThrustSource));
    }

    public Vector2[] translate(Vector2 translation, float rotation, Vector2 ... vectors) {
        Vector2[] translatedVectors = new Vector2[vectors.length];
        for(int index = 0; index < vectors.length; index++) {
            translatedVectors[index] = translate(translation, rotation, vectors[index]);
        }
        return translatedVectors;
    }

    public Vector2 translate(Vector2 translation, float rotation, Vector2 vector) {
        // add() and rotate() modify the vector itself, so we apply our changes to the new vector we have created instead.
        return new Vector2().add(vector).rotateRad(rotation).add(translation);
    }

}
