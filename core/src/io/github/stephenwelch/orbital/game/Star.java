package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.stephenwelch.orbital.engine.Renderable;

import java.util.List;

class Star implements Renderable {
    private Color color;
    private Vector2 position;
    private float radius;

    public Star(Vector2 position, float radius, Color color) {
        this.color = color;
        this.position = position;
        this.radius = radius;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.circle(position.x, position.y, radius);
    }

    @Override
    public ShapeRenderer.ShapeType getShapeType() {
        return ShapeRenderer.ShapeType.Filled;
    }

    @Override
    public List<? extends Renderable> getSubComponents() {
        return null;
    }

    public Color getColor() {
        return color;
    }

    public Star setColor(Color color) {
        this.color = color;
        return this;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Star setPosition(Vector2 position) {
        this.position = position;
        return this;
    }

    public float getRadius() {
        return radius;
    }

    public Star setRadius(float radius) {
        this.radius = radius;
        return this;
    }
}
