package io.github.stephenwelch.orbital.engine.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.List;

/**
 * Provides a way to render debug information per-body using code from Box2DDebugRenderer.
 */
public class RenderableDebugBody implements Renderable {

    private final ShapeRenderer renderer;
    private final Body body;
    private Color color = Color.WHITE;
    private boolean renderAABB = false;
    private boolean renderShape = true;

    public final Color AABB_COLOR = new Color(1.0f, 0, 1.0f, 1f);
    private final static Vector2[] vertices = new Vector2[1000];
    private final static Vector2 lower = new Vector2();
    private final static Vector2 upper = new Vector2();

    public RenderableDebugBody(Body body, ShapeRenderer renderer) {
        this.body = body;
        this.renderer = renderer;
        // initialize vertices array
        for (int i = 0; i < vertices.length; i++)
            vertices[i] = new Vector2();
    }

    @Override
    public void render(ShapeRenderer renderer) {
        for(Fixture fixture : body.getFixtureList()) {
            if(renderShape) {
                drawShape(fixture, body.getTransform(), this.getColor());
            }
            if(renderAABB) {
                drawAABB(fixture, body.getTransform());
            }
        }
    }

    @Override
    public ShapeRenderer.ShapeType getShapeType() {
        return ShapeRenderer.ShapeType.Line;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public RenderableDebugBody setColor(Color color) {
        this.color = color;
        return this;
    }

    public boolean isRenderAABB() {
        return renderAABB;
    }

    public RenderableDebugBody setRenderAABB(boolean renderAABB) {
        this.renderAABB = renderAABB;
        return this;
    }

    public boolean isRenderShape() {
        return renderShape;
    }

    public RenderableDebugBody setRenderShape(boolean renderShape) {
        this.renderShape = renderShape;
        return this;
    }

    @Override
    public List<? extends Renderable> getSubComponents() {
        return null;
    }

    private static Vector2 t = new Vector2();
    private static Vector2 axis = new Vector2();

    private void drawShape (Fixture fixture, Transform transform, Color color) {
        if (fixture.getType() == Shape.Type.Circle) {
            CircleShape circle = (CircleShape) fixture.getShape();
            t.set(circle.getPosition());
            transform.mul(t);
            drawSolidCircle(t, circle.getRadius(), axis.set(transform.vals[Transform.COS], transform.vals[Transform.SIN]), color);
            return;
        }

        if (fixture.getType() == Shape.Type.Edge) {
            EdgeShape edge = (EdgeShape) fixture.getShape();
            edge.getVertex1(vertices[0]);
            edge.getVertex2(vertices[1]);
            transform.mul(vertices[0]);
            transform.mul(vertices[1]);
            drawSolidPolygon(vertices, 2, color, true);
            return;
        }

        if (fixture.getType() == Shape.Type.Polygon) {
            PolygonShape chain = (PolygonShape) fixture.getShape();
            int vertexCount = chain.getVertexCount();
            for (int i = 0; i < vertexCount; i++) {
                chain.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
            }
            drawSolidPolygon(vertices, vertexCount, color, true);
            return;
        }

        if (fixture.getType() == Shape.Type.Chain) {
            ChainShape chain = (ChainShape) fixture.getShape();
            int vertexCount = chain.getVertexCount();
            for (int i = 0; i < vertexCount; i++) {
                chain.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
            }
            drawSolidPolygon(vertices, vertexCount, color, false);
        }
    }

    private final Vector2 f = new Vector2();
    private final Vector2 v = new Vector2();
    private final Vector2 lv = new Vector2();

    private void drawSolidCircle (Vector2 center, float radius, Vector2 axis, Color color) {
        float angle = 0;
        float angleInc = 2 * (float)Math.PI / 20;
        renderer.setColor(color.r, color.g, color.b, color.a);
        for (int i = 0; i < 20; i++, angle += angleInc) {
            v.set((float)Math.cos(angle) * radius + center.x, (float)Math.sin(angle) * radius + center.y);
            if (i == 0) {
                lv.set(v);
                f.set(v);
                continue;
            }
            renderer.line(lv.x, lv.y, v.x, v.y);
            lv.set(v);
        }
        renderer.line(f.x, f.y, lv.x, lv.y);
        renderer.line(center.x, center.y, 0, center.x + axis.x * radius, center.y + axis.y * radius, 0);
    }

    private void drawSolidPolygon (Vector2[] vertices, int vertexCount, Color color, boolean closed) {
        renderer.setColor(color.r, color.g, color.b, color.a);
        lv.set(vertices[0]);
        f.set(vertices[0]);
        for (int i = 1; i < vertexCount; i++) {
            Vector2 v = vertices[i];
            renderer.line(lv.x, lv.y, v.x, v.y);
            lv.set(v);
        }
        if (closed) renderer.line(f.x, f.y, lv.x, lv.y);
    }

    private void drawAABB (Fixture fixture, Transform transform) {
        if (fixture.getType() == Shape.Type.Circle) {

            CircleShape shape = (CircleShape)fixture.getShape();
            float radius = shape.getRadius();
            vertices[0].set(shape.getPosition());
            transform.mul(vertices[0]);
            lower.set(vertices[0].x - radius, vertices[0].y - radius);
            upper.set(vertices[0].x + radius, vertices[0].y + radius);

            // define vertices in ccw fashion...
            vertices[0].set(lower.x, lower.y);
            vertices[1].set(upper.x, lower.y);
            vertices[2].set(upper.x, upper.y);
            vertices[3].set(lower.x, upper.y);

            drawSolidPolygon(vertices, 4, AABB_COLOR, true);
        } else if (fixture.getType() == Shape.Type.Polygon) {
            PolygonShape shape = (PolygonShape)fixture.getShape();
            int vertexCount = shape.getVertexCount();

            shape.getVertex(0, vertices[0]);
            lower.set(transform.mul(vertices[0]));
            upper.set(lower);
            for (int i = 1; i < vertexCount; i++) {
                shape.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
                lower.x = Math.min(lower.x, vertices[i].x);
                lower.y = Math.min(lower.y, vertices[i].y);
                upper.x = Math.max(upper.x, vertices[i].x);
                upper.y = Math.max(upper.y, vertices[i].y);
            }

            // define vertices in ccw fashion...
            vertices[0].set(lower.x, lower.y);
            vertices[1].set(upper.x, lower.y);
            vertices[2].set(upper.x, upper.y);
            vertices[3].set(lower.x, upper.y);

            drawSolidPolygon(vertices, 4, AABB_COLOR, true);
        }
    }

}
