package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.stephenwelch.orbital.engine.Renderable;
import io.github.stephenwelch.orbital.engine.Renderer;

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
//        Gdx.app.debug("STAR", "Rendering star at: " + position + " with radius: " + radius);
        renderer.circle(position.x, position.y, radius);
    }

    public boolean isOutOfScreenBounds() {
        if((position.x > Renderer.CAMERA_WIDTH || position.x < 0) && (position.y > Renderer.CAMERA_HEIGHT || position.y < 0)) {
            return true;
        }
        return false;
    }

    public void moveIntoScreenBounds() {
        if(position.x > Renderer.CAMERA_WIDTH) {
            position.x = Renderer.CAMERA_WIDTH - position.x;
        }
        if(position.x < 0) {
            position.x = Renderer.CAMERA_WIDTH + position.x;
        }
        if(position.y > Renderer.CAMERA_HEIGHT) {
            position.y = Renderer.CAMERA_HEIGHT - position.y;
        }
        if(position.y < 0) {
            position.y = Renderer.CAMERA_HEIGHT + position.y;
        }
    }

    public String toString() {
        return String.format("Position: %s\tRadius: %s\tColor: %s", position, radius, color);
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
