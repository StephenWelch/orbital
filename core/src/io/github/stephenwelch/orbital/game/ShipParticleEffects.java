package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import io.github.stephenwelch.orbital.engine.ParticleEffectContainer;
import io.github.stephenwelch.orbital.engine.RendererEffect;

public class ShipParticleEffects implements ParticleEffectContainer {

    public ParticleEffectPool thrustEffectPool = null;
    public RendererEffect mainEngineThrust = null;
    public RendererEffect leftForwardThruster = null;
    public RendererEffect rightForwardThruster = null;
    public RendererEffect leftBackThruster = null;
    public RendererEffect rightBackThruster = null;
    public RendererEffect leftRetrorocket = null;
    public RendererEffect rightRetrorocket = null;

    @Override
    public void create() {
        ParticleEffect thrustEffect = new ParticleEffect();
        thrustEffect.load(Gdx.files.internal("thrust_particle.p"), Gdx.files.internal(""));
        thrustEffect.setEmittersCleanUpBlendFunction(false);

        thrustEffectPool = new ParticleEffectPool(thrustEffect, 10, 10);

        mainEngineThrust = new RendererEffect(thrustEffectPool.obtain(), true);
    }

}
