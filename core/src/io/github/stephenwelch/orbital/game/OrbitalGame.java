package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
	private PhysicsManager physicsManager = new PhysicsManager(renderer.getCamera());
	private Ship ship = new Ship(physicsManager.getWorld());

	// Box2d setup

	@Override
	public void create () {
//		ParticleEffectsDefCreator c = new ParticleEffectsDefCreator();
//		c.create();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("INIT", "Initializing Orbital...");

		physicsManager.setRenderPhysics(false);

		renderer.setAntialiasing(false);
		renderer.setRenderList(ship);

		entities.add(renderer);
		entities.add(physicsManager);
		entities.add(ship);

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
