package io.github.stephenwelch.orbital.game.entity.star;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.engine.GameModule;
import io.github.stephenwelch.orbital.engine.renderer.Renderable;
import io.github.stephenwelch.orbital.engine.renderer.Renderer;

import java.util.Arrays;
import java.util.List;

public class StarBackground implements GameModule, Renderable {

    private StarLayer backLayer = new StarLayer(Renderer.CAMERA_WIDTH, Renderer.CAMERA_HEIGHT, 1234)
            .setMinColor(Color.WHITE).setMaxColor(Color.WHITE)
            .setMinCount(1000).setMaxCount(1000)
            .setMinRadius(1f).setMaxRadius(1.5f);

    private List<StarLayer> layers = Arrays.asList(backLayer);

    private Vector2 lastPosition = new Vector2();

    public void create() {
        layers.forEach(GameModule::create);
        lastPosition = getCameraPosition();
    }

    @Override
    public void update() {
        Vector2 currentPosition = getCameraPosition();
        Vector2 delta = currentPosition.cpy().sub(lastPosition);
        layers.forEach(l -> l.translate(delta));
        layers.forEach(l -> l.scroll(delta));

        layers.forEach(GameModule::update);

        lastPosition = currentPosition;
    }

    @Override
    public void dispose() {
        layers.forEach(GameModule::dispose);
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
