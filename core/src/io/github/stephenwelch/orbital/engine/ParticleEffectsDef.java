package io.github.stephenwelch.orbital.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ParticleEffectsDef<E extends Enum> {

    private final Vector3 defaultParticlePosition;
    private final int initialCapacity, maxCapacity;
    @Expose
    public final Map<E, String> particleNamePoolFileMap = new HashMap<>();
    @Expose
    public final Map<E, Vector3> particleNamePosition = new HashMap<>();

    private final Map<E, ParticleEffectPool> particleNamePoolMap = new HashMap<>();
    private final Map<E, RendererEffect> particleNameEffectMap = new HashMap<>();

    public ParticleEffectsDef(Vector3 defaultParticlePosition, int initialCapacity, int maxCapacity) {
        this.defaultParticlePosition = defaultParticlePosition;
        this.initialCapacity = initialCapacity;
        this.maxCapacity = maxCapacity;
    }

    public ParticleEffectsDef() {
        this(new Vector3(0f, 0f, 0f),  8, 64);
    }

    public void create() {
        particleNamePoolFileMap.forEach((particle, filePath) -> {
            ParticleEffect particleEffect = new ParticleEffect();
            particleEffect.load(Gdx.files.internal(filePath), Gdx.files.internal(filePath.substring(0, filePath.lastIndexOf("/"))));
            particleNamePoolMap.computeIfAbsent(particle, k -> new ParticleEffectPool(particleEffect, initialCapacity, maxCapacity));
        });
        particleNamePoolMap.forEach((particle, pool) -> {
            particleNameEffectMap.computeIfAbsent(particle, k -> new RendererEffect(pool.obtain(), true));
        });
    }

    public void addParticlesToPool(String poolFilePath, E ... particles) {
        // Map each particle to corresponding pool
        for(E particle : particles) {
            particleNamePoolFileMap.put(particle, poolFilePath);
        }
        // Create a new position mapping for each particle if one doesn't exist at (0, 0, 0)
        for(E particle : particles) {
            particleNamePosition.computeIfAbsent(particle, k -> defaultParticlePosition);
        }
    }

    public RendererEffect getEffect(E particleName) {
        return particleNameEffectMap.get(particleName);
    }

}
