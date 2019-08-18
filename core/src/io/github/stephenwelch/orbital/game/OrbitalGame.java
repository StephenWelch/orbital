package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.google.gson.reflect.TypeToken;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.engine.GameModule;
import io.github.stephenwelch.orbital.engine.input.KeyMapper;
import io.github.stephenwelch.orbital.engine.physics.PhysicsManager;
import io.github.stephenwelch.orbital.engine.renderer.Renderer;
import io.github.stephenwelch.orbital.game.entity.Planet;
import io.github.stephenwelch.orbital.game.entity.ship.Ship;
import io.github.stephenwelch.orbital.game.entity.ship.ShipKeyFunctions;
import io.github.stephenwelch.orbital.game.entity.ship.ShipKeyMap;
import io.github.stephenwelch.orbital.game.entity.star.StarBackground;

import java.util.ArrayList;
import java.util.List;

public class OrbitalGame extends ApplicationAdapter {

	// Game setup
	private List<GameModule> entities = new ArrayList<>();
	private Renderer renderer = Renderer.getInstance();
	private PhysicsManager physicsManager = PhysicsManager.getInstance();
	private Ship ship = new Ship();
	private Planet planet = new Planet(50.0f, /*5.972E24f*/1.0E16f);

	private StarBackground bg = new StarBackground();

//	private KeyMapper<ShipKeyFunctions> keyMapper = Util.loadFromJson(Gdx.files.internal("conf/ship_keys.json"), new TypeToken<KeyMapper<ShipKeyFunctions>>(){});

	// Box2d setup

	@Override
	public void create () {
//		ParticleEffectsDefCreator c = new ParticleEffectsDefCreator();
//		c.create();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("INIT", "Initializing Orbital...");

		renderer.setAntialiasing(true);
		renderer.setRenderList(bg, planet, ship, physicsManager);

		entities.add(ShipKeyMap.getInstance());
		entities.add(renderer);
		entities.add(physicsManager);
		entities.add(ship);
		entities.add(planet);
		entities.add(bg);

		entities.forEach(GameModule::create);

	}

	@Override
	public void render () {
//		Gdx.app.log("UPDATE", "Rendering Orbital");
		entities.forEach(GameModule::update);
	}

	@Override
	public void resize(int width, int height) {
		// Just delegate to renderer
		renderer.resize(width, height);
	}

	@Override
	public void dispose () {
		Gdx.app.log("SHUTDOWN", "Shutting down Orbital");
		entities.forEach(GameModule::dispose);
	}
}
