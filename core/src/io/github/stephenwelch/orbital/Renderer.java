package io.github.stephenwelch.orbital;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Renderer implements GameEntity {

    private static Renderer instance = new Renderer();

    public final int CAMERA_WIDTH = 800;
    public final int CAMERA_HEIGHT = 600;
    public final int WINDOW_WIDTH = 800;
    public final int WINDOW_HEIGHT = 600;

    private OrthographicCamera camera = new OrthographicCamera();
    private Viewport viewport;
    private ShapeRenderer renderer;
    private Renderable[] renderList = new Renderable[0];
    private SpriteBatch effectSpriteBatch = null;
    private List<RendererEffect> effectList = new ArrayList<>();

    private boolean enableAntialiasing = false;

    private Renderer(Renderable ... renderables) {
        setRenderList(renderables);
    }

    @Override
    public void create() {
        renderer = new ShapeRenderer();
        effectSpriteBatch = new SpriteBatch();

        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        viewport = new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT, camera);
    }

    @Override
    public void update() {
        Gdx.app.debug("RENDERER", "Updating renderer: " + renderList);
        float zoomInc = 0.025f;
        if(Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            camera.zoom = Math.max(0.025f, camera.zoom - zoomInc);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            camera.zoom = Math.min(5.5f, camera.zoom + zoomInc);
        }
//        Gdx.app.debug("RENDERER", "Zoom: " + camera.zoom);
        camera.update();
        clear();
        for(Renderable r : renderList) {
            render(r);
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private void render(Renderable renderable) {
        Gdx.app.debug("RENDERER", "Running render");
        if(renderable.getSubComponents() != null) {
            for(Renderable subComponent : renderable.getSubComponents()) {
                render(subComponent);
            }
        }
        renderComponent(renderable);

        effectSpriteBatch.begin();
        effectList.forEach(effect -> {
            effect.effect.draw(effectSpriteBatch, Gdx.graphics.getDeltaTime());
            if(effect.effect.isComplete()) {
                if(effect.reusable) {
                    effect.effect.reset();
                } else {
                    effect.effect.free();
                    effectList.remove(effect);
                }
            }
        });
        effectSpriteBatch.end();

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

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setRenderList(Renderable ... renderList) {
        this.renderList = renderList;
    }

    public Renderer setAntialiasing(boolean enabled) {
        this.enableAntialiasing = enabled;
        return this;
    }

    public void addEffects(RendererEffect ... effects) {
        this.effectList.addAll(Arrays.asList(effects));
    }

    public static Renderer getInstance() {
        return instance;
    }

}
