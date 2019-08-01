package io.github.stephenwelch.orbital.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RendererEffect {

    public final ParticleEffectPool.PooledEffect effect;
    public final boolean reusable;
    private boolean enabled = true;

    public RendererEffect(ParticleEffectPool.PooledEffect effect, boolean reusable) {
        this.effect = effect;
        this.reusable = reusable;
    }

    public void render(SpriteBatch spriteBatch) {
        if(enabled && !effect.isComplete()) {
            Gdx.app.debug("EFFECT", "RENDERING");
            effect.draw(spriteBatch, Gdx.graphics.getDeltaTime());
        }
    }

    public void start() {
        enabled = true;
        effect.start();
    }

    public void stop() {
        enabled = false;
        effect.reset();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void dispose() {
        if(reusable) {
            effect.reset();
        } else {
            effect.free();
        }
    }

}
