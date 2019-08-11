package io.github.stephenwelch.orbital.engine.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class RenderableVector2 extends Vector2 implements Renderable {

    private Color color = Color.WHITE;
    private Vector2 sourcePoint = new Vector2();

    public RenderableVector2(Vector2 sourcePoint, Vector2 renderedVector) {
        super(renderedVector);
        this.sourcePoint = sourcePoint;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.line(sourcePoint, sourcePoint.cpy().add(this));
    }

    @Override
    public ShapeRenderer.ShapeType getShapeType() {
        return ShapeRenderer.ShapeType.Line;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public RenderableVector2 setColor(Color color) {
        this.color = color;
        return this;
    }

    public Vector2 getSourcePoint() {
        return sourcePoint;
    }

    public RenderableVector2 setSourcePoint(Vector2 sourcePoint) {
        this.sourcePoint = sourcePoint;
        return this;
    }

    @Override
    public List<Renderable> getSubComponents() {
        return null;
    }

}
