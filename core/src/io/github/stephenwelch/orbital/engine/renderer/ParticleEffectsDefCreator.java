package io.github.stephenwelch.orbital.engine.renderer;

import com.badlogic.gdx.Gdx;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.game.entity.ship.Ship;

public class ParticleEffectsDefCreator {

    public void create() {
        ParticleEffectsDef<Ship.ShipParticleEffects> shipParticleEffectsDef = new ParticleEffectsDef<>();
        // Add all particles to the same pool
        shipParticleEffectsDef.addParticlesToPool("particles/thrust_particle.p", Ship.ShipParticleEffects.values());
        Util.saveToJson(Gdx.files.external("ship.ppm"), shipParticleEffectsDef);
    }

}
