package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.google.gson.reflect.TypeToken;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.engine.*;

import java.util.List;

public class Ship implements Renderable, GameEntity {

    private final Vector2[] vertices = new Vector2[] {
            new Vector2(0.0f, 5.9475f),
            new Vector2(0.0f, -5.9475f),
            new Vector2(9.3092f, 0.0f)
    };

    private Body body = null;
    private Fixture fixture = null;

    private ParticleEffectsDef<ShipParticleEffects> particleEffects = null;

    public enum ShipParticleEffects {
        MAIN_ENGINE,
        LEFT_FORWARD_THRUSTER, RIGHT_FORWARD_THRUSTER,
        LEFT_BACK_THRUSTER, RIGHT_BACK_THRUSTER,
        LEFT_RETRO, RIGHT_RETRO;
    }

    @Override
    public void create() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(400.0f, 300.0f);
        bodyDef.angle = 0.0f;

        body = PhysicsManager.getInstance().getWorld().createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        fixture = body.createFixture(fixtureDef);

        shape.dispose();

        particleEffects = Util.loadFromJson(Gdx.files.internal("particles/ship.ppm"), new TypeToken<ParticleEffectsDef<ShipParticleEffects>>() {});
        particleEffects.create();
        particleEffects.registerAll();
    }

    @Override
    public void update() {
        float torque = 500.0f;
        float force = 500.0f;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.applyTorque(torque, true);

            RendererEffect rightForward = particleEffects.getEffectWithAdjustedPosition(ShipParticleEffects.RIGHT_FORWARD_THRUSTER, getTranslationRotation());
            RendererEffect leftBack = particleEffects.getEffectWithAdjustedPosition(ShipParticleEffects.LEFT_BACK_THRUSTER, getTranslationRotation());

            rightForward.start();
            leftBack.start();
        } else {
            particleEffects.getEffect(ShipParticleEffects.RIGHT_FORWARD_THRUSTER).stop();
            particleEffects.getEffect(ShipParticleEffects.LEFT_BACK_THRUSTER).stop();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.applyTorque(-torque, true);

            RendererEffect leftForward = particleEffects.getEffectWithAdjustedPosition(ShipParticleEffects.LEFT_FORWARD_THRUSTER, getTranslationRotation());
            RendererEffect rightBack = particleEffects.getEffectWithAdjustedPosition(ShipParticleEffects.RIGHT_BACK_THRUSTER, getTranslationRotation());

            leftForward.start();
            rightBack.start();
        } else {
            particleEffects.getEffect(ShipParticleEffects.LEFT_FORWARD_THRUSTER).stop();
            particleEffects.getEffect(ShipParticleEffects.RIGHT_BACK_THRUSTER).stop();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            body.applyForceToCenter(force * (float)Math.cos(body.getAngle()), force * (float)Math.sin(body.getAngle()), true);

            RendererEffect mainEngineThrust = particleEffects.getEffectWithAdjustedPosition(ShipParticleEffects.MAIN_ENGINE, getTranslationRotation());
            mainEngineThrust.start();
        } else {
            particleEffects.getEffect(ShipParticleEffects.MAIN_ENGINE).stop();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            body.applyForceToCenter(-force * (float)Math.cos(body.getAngle()), -force * (float)Math.sin(body.getAngle()), true);

            RendererEffect leftRetro = particleEffects.getEffectWithAdjustedPosition(ShipParticleEffects.LEFT_RETRO, getTranslationRotation());
            RendererEffect rightRetro = particleEffects.getEffectWithAdjustedPosition(ShipParticleEffects.RIGHT_RETRO, getTranslationRotation());

            leftRetro.start();
            rightRetro.start();
        } else {
            particleEffects.getEffect(ShipParticleEffects.LEFT_RETRO).stop();
            particleEffects.getEffect(ShipParticleEffects.RIGHT_RETRO).stop();
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
        return Util.translateAndRotateVectors(body.getPosition(), (float)Math.toDegrees(body.getAngle()), vertices);
    }

    private Vector3 getTranslationRotation() {
        return new Vector3(body.getPosition().x, body.getPosition().y, (float)Math.toDegrees(body.getAngle()));
    }

}
