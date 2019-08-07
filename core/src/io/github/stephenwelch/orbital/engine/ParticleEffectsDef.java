package io.github.stephenwelch.orbital.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.stephenwelch.orbital.Util;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ParticleEffectsDef<E extends Enum> {

    // So we have to use transient instead of @Expose because @Expose will serialize a data structure but not the data it contains.
    private transient final Vector3 defaultParticlePosition;
    private transient final int initialCapacity, maxCapacity;

    public final Map<E, String> particleNamePoolFileMap = new HashMap<>();
    public final Map<E, Vector3> particleNamePosition = new HashMap<>();

    private transient final Map<E, ParticleEffectPool> particleNamePoolMap = new HashMap<>();
    private transient final Map<E, RendererEffect> particleNameEffectMap = new HashMap<>();

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
        for(E particle : particles) {
            // Map each particle to corresponding pool
            particleNamePoolFileMap.put(particle, poolFilePath);
            // Create a new position mapping for each particle if one doesn't exist at (0, 0, 0)
            particleNamePosition.put(particle, defaultParticlePosition);
        }
    }

    public RendererEffect getEffect(E particleName) {
        return particleNameEffectMap.get(particleName);
    }

    public Vector3 getEffectPosition(E particleName) {
        return particleNamePosition.get(particleName);
    }

    public Vector3 getAdjustedEffectPosition(E particleName, Vector3 adjustment) {
        Vector3 effectPosition = getEffectPosition(particleName);
        Vector2 newPosition = Util.translateAndRotateVector(Util.truncateVector(adjustment), adjustment.z, Util.truncateVector(effectPosition));
        float newAngle = adjustment.z + effectPosition.z;
        return new Vector3(newPosition.x, newPosition.y, newAngle);
    }

    public RendererEffect getEffectWithAdjustedPosition(E particleName, Vector3 adjustment) {
        RendererEffect effect = getEffect(particleName);
        effect.setTranslationAndRotation(getAdjustedEffectPosition(particleName, adjustment));
        return effect;
    }

    public void saveToJson(FileHandle fileHandle) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        try(Writer jsonWriter = fileHandle.writer(false)) {
            jsonWriter.append(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Gdx.app.log("PARTICLE_EFFECT_DEF", "Saved configuration file.");
        }
    }

    public void registerAll() {
        particleNameEffectMap.forEach((particleName, effect) -> {
            Renderer.getInstance().registerParticleEffect(effect);
        });
    }

//    public static <T extends Enum> ParticleEffectsDef<T> loadFromJson(FileHandle fileHandle) {
//        String json = Util.loadFromFile(fileHandle);
//        Gson gson = new Gson();
//        ParticleEffectsDef<T> particleEffectsDef = gson.fromJson(json, ParticleEffectsDef.class);
//    }

}
