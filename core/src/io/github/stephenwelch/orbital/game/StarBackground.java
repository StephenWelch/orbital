package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.engine.Renderable;
import io.github.stephenwelch.orbital.engine.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StarBackground implements Renderable {

    public void create() {

    }

    @Override
    public void render(ShapeRenderer renderer) {

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
        return null;
    }

}
