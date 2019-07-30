package io.github.stephenwelch.orbital.engine;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public class RendererEffect {

    public final ParticleEffectPool.PooledEffect effect;
    public final boolean reusable;

    public RendererEffect(ParticleEffectPool.PooledEffect effect, boolean reusable) {
        this.effect = effect;
        this.reusable = reusable;
    }

}
