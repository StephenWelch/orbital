package io.github.stephenwelch.orbital.engine;

import java.util.List;

public interface EffectSubmitter {

    List<RendererEffect> getActiveParticleEffects();
    boolean removeParticleEffect(RendererEffect effectToRemove);

}
