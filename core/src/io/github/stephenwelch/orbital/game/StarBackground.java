package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.engine.GameEntity;
import io.github.stephenwelch.orbital.engine.Renderable;
import io.github.stephenwelch.orbital.engine.Renderer;

import java.util.Arrays;
import java.util.List;

public class StarBackground implements GameEntity, Renderable {

    private StarLayer backLayer = new StarLayer(1234)
            .setMinColor(Color.WHITE).setMaxColor(Color.WHITE)
            .setMinCount(100).setMaxCount(100)
            .setMinRadius(1f).setMaxRadius(1.5f);

    private List<StarLayer> layers = Arrays.asList(backLayer);

    private Vector2 lastPosition = new Vector2();

    public void create() {
        backLayer.create();
        lastPosition = getCameraPosition();
    }

    @Override
    public void update() {
        Vector2 currentPosition = getCameraPosition();
        Vector2 delta = currentPosition.cpy().sub(lastPosition);
        if(!delta.equals(Vector2.Zero)) System.out.println(delta);
        layers.forEach(l -> l.scroll(delta));
        lastPosition = currentPosition;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(ShapeRenderer renderer) {

    }

    private Vector2 getCameraPosition() {
        return Util.truncateVector(Renderer.getInstance().getCamera().position);
    }

    @Override
    public ShapeRenderer.ShapeType getShapeType() {
        return ShapeRenderer.ShapeType.Filled;
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public List<? extends Renderable> getSubComponents() {
        return layers;
    }

}
