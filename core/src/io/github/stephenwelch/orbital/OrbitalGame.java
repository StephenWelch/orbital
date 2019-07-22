package io.github.stephenwelch.orbital;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class OrbitalGame extends ApplicationAdapter {

	// Game setup
	private List<GameEntity> entities = new ArrayList<>();
	private Ship ship = new Ship();
	private Renderer renderer = new Renderer(ship);

	// Box2d setup

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("INIT", "Initializing Orbital...");

		entities.add(renderer);
		entities.add(ship);

		entities.forEach(GameEntity::create);
	}

	@Override
	public void render () {
//		Gdx.app.log("UPDATE", "Rendering Orbital");
		entities.forEach(GameEntity::update);
	}
	
	@Override
	public void dispose () {
		Gdx.app.log("SHUTDOWN", "Shutting down Orbital");
		entities.forEach(GameEntity::dispose);
	}
}
