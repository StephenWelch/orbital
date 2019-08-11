package io.github.stephenwelch.orbital.game.entity.star;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.stephenwelch.orbital.engine.GameModule;
import io.github.stephenwelch.orbital.engine.renderer.Renderable;
import io.github.stephenwelch.orbital.engine.renderer.Renderer;

import java.util.List;

class Star implements GameModule, Renderable {

    private PointLight light;
    private Color color;
    private Vector2 position;
    private float radius;

    public Star(Vector2 position, float radius, Color color) {
        this.color = color;
        this.position = position;
        this.radius = radius;
    }

    @Override
    public void create() {
        light = new PointLight(Renderer.getInstance().getRayHandler(), 4, Color.LIGHT_GRAY, radius * 8f, 0f, 0f);
        light.setSoft(true);
        light.setSoftnessLength(0.5f);
    }

    @Override
    public void update() {
        light.setPosition(position);
    }

    @Override
    public void dispose() {
//        light.dispose();
    }

    @Override
    public void render(ShapeRenderer renderer) {
//        Gdx.app.debug("STAR", "Rendering star at: " + position + " with radius: " + radius);
        renderer.circle(position.x, position.y, radius);
    }

    public void moveIntoScreenBounds() {
        Renderer renderer = Renderer.getInstance();
        if(position.x > renderer.getRightCameraEdgePosition()) {
            position.x = renderer.getLeftCameraEdgePosition() + (position.x - renderer.getRightCameraEdgePosition());
        }
        if(position.x < renderer.getLeftCameraEdgePosition()) {
            position.x = renderer.getRightCameraEdgePosition() - (renderer.getLeftCameraEdgePosition() - position.x);
        }
        if(position.y > renderer.getTopCameraEdgePosition()) {
            position.y = renderer.getBottomCameraEdgePosition() + (position.y - renderer.getTopCameraEdgePosition());
        }
        if(position.y < renderer.getBottomCameraEdgePosition()) {
            position.y = renderer.getTopCameraEdgePosition() - (renderer.getBottomCameraEdgePosition() - position.y);
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
