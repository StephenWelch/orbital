package io.github.stephenwelch.orbital;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Renderer implements GameEntity {

    private OrthographicCamera camera = new OrthographicCamera();
    private Viewport viewport;
    private ShapeRenderer renderer;
    private List<Renderable> renderList;

    public Renderer(Renderable ... renderables) {
        renderList = Arrays.asList(renderables);
    }

    @Override
    public void create() {
        renderer = new ShapeRenderer();

        camera.setToOrtho(false, 400, 300);
        viewport = new FitViewport(800, 600, camera);
    }

    @Override
    public void update() {
        camera.update();
        clear();
        renderList.forEach(this::render);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    public void render(Renderable renderable) {
        if(renderable.getSubComponents() != null) {
            for(Renderable subComponent : renderable.getSubComponents()) {
                render(subComponent);
            }
        }
        renderComponent(renderable);
    }

    private void renderComponent(Renderable renderable) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(renderable.getShapeType());
        renderer.setColor(renderable.getColor());
        renderable.render(renderer);
        renderer.end();
    }

    public void clear() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

}
