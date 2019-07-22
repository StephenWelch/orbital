package io.github.stephenwelch.orbital;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public class Ship implements Renderable, GameEntity {



    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(ShapeRenderer renderer) {
        Gdx.app.debug("SHIP", "Rendering ship");
        renderer.triangle(0.0f, 9.3092f, -5.9475f, 0.0f, 5.9475f, 0.0f);
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

}
