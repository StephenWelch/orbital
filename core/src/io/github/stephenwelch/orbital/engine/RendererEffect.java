package io.github.stephenwelch.orbital.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class RendererEffect {

    public final ParticleEffectPool.PooledEffect effect;
    public final boolean reusable;
    private boolean started = false;

    public RendererEffect(ParticleEffectPool.PooledEffect effect, boolean reusable) {
        this.effect = effect;
        this.reusable = reusable;
    }

    public void render(SpriteBatch spriteBatch) {
        if(started) {
            effect.draw(spriteBatch, Gdx.graphics.getDeltaTime());
        } else if(effect.isComplete()) {
            stop();
        }
    }

    public void start() {
        if(!started ) {
//            Gdx.app.debug("EFFECT", "Starting");
            started = true;
            effect.reset();
            effect.start();
        }
    }

    public void stop() {
        if(started) {
//            Gdx.app.debug("EFFECT", "Stopping");
            started = false;
            effect.reset();
        }
        if(effect.isComplete() && !reusable) {
            effect.free();
        }
    }

    public void setTranslationAndRotation(Vector3 translationAndRotation) {
        effect.setPosition(translationAndRotation.x, translationAndRotation.y);
        Renderer.rotateParticleEffect(effect, translationAndRotation.z);
    }

}
