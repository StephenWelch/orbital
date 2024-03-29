package io.github.stephenwelch.orbital.engine.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public interface Renderable {

    void render(ShapeRenderer renderer);
    ShapeRenderer.ShapeType getShapeType();
    Color getColor();
    List<? extends Renderable> getSubComponents();


}
