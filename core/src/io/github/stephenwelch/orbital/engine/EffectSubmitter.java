package io.github.stephenwelch.orbital.engine;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface EffectSubmitter {

    BlockingQueue<RendererEffect> getActiveParticleEffects();
    boolean removeParticleEffect(RendererEffect effectToRemove);

}
