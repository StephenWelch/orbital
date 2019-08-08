package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import io.github.stephenwelch.orbital.engine.GameEntity;
import io.github.stephenwelch.orbital.engine.ParticleEffectsDefCreator;
import io.github.stephenwelch.orbital.engine.PhysicsManager;
import io.github.stephenwelch.orbital.engine.Renderer;

import java.util.ArrayList;
import java.util.List;

public class OrbitalGame extends ApplicationAdapter {

	// Game setup
	private List<GameEntity> entities = new ArrayList<>();
	private Renderer renderer = Renderer.getInstance();
	private PhysicsManager physicsManager = PhysicsManager.getInstance();
	private Ship ship = new Ship();
	private Planet planet = new Planet(50.0f, /*5.972E24f*/10000000000000000f);

	private StarLayer bg = new StarLayer(1234)
			.setMinColor(Color.WHITE).setMaxColor(Color.WHITE)
			.setMinCount(100).setMaxCount(100)
			.setMinRadius(1).setMaxRadius(8);

	// Box2d setup

	@Override
	public void create () {
//		ParticleEffectsDefCreator c = new ParticleEffectsDefCreator();
//		c.create();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("INIT", "Initializing Orbital...");

//		bg.create();

		physicsManager.setRenderPhysics(true);

		renderer.setAntialiasing(false);
		renderer.setRenderList(ship, planet, physicsManager, bg);

		entities.add(renderer);
		entities.add(physicsManager);
		entities.add(ship);
		entities.add(planet);

		entities.forEach(GameEntity::create);
	}

	@Override
	public void render () {
//		Gdx.app.log("UPDATE", "Rendering Orbital");
		entities.forEach(GameEntity::update);
	}

	@Override
	public void resize(int width, int height) {
		// Just delegate to renderer
		renderer.resize(width, height);
	}

	@Override
	public void dispose () {
		Gdx.app.log("SHUTDOWN", "Shutting down Orbital");
		entities.forEach(GameEntity::dispose);
	}
}
