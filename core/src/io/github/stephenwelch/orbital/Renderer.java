package io.github.stephenwelch.orbital;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.List;

public class Renderer implements GameEntity {

    private OrthographicCamera camera = new OrthographicCamera();
    private Viewport viewport;
    private ShapeRenderer renderer;
    private List<Renderable> renderList;

    private boolean enableAntialiasing = false;

    public Renderer(Renderable ... renderables) {
        setRenderList(renderables);
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

        if (enableAntialiasing) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        } else {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setRenderList(Renderable ... renderList) {
        this.renderList = Arrays.asList(renderList);
    }

    public Renderer setAntialiasing(boolean enabled) {
        enableAntialiasing = enabled;
        return this;
    }

}
