package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.engine.GameEntity;
import io.github.stephenwelch.orbital.engine.Renderable;
import io.github.stephenwelch.orbital.engine.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StarBackground implements Renderable {

    private class Star {
    }

    private class StarLayer {
        private float minRadius = 1.0f;
        private float maxRadius = 8.0f;
        private int minCount = 100;
        private int maxCount = 200;
        private float translationMultiplier = 1.0f;
        private final long seed;

        public StarLayer(long seed) {
            this.seed = seed;
        }

        private List<Circle> frontLayer = new ArrayList<>();

        public void create() {
            int count = Util.getRandomNumber(minCount, maxCount, seed);
            for(int index = 0; index < count; index++) {
                float radius = Util.getRandomNumber(minRadius, maxRadius, seed);
                float x = Util.getRandomNumber(0, Renderer.CAMERA_WIDTH, seed);
                float y = Util.getRandomNumber(0, Renderer.CAMERA_HEIGHT, seed);
                frontLayer.add(new Circle(x, y, radius));
            }
        }

        public void translate(Vector2 translation) {
            Vector2 scaledTranslation = translation.cpy().scl(translationMultiplier);
            frontLayer = frontLayer.stream()
                                    .map(c -> new Vector2(c.x, c.y))
                                    .map(v -> Util.translateVector(scaledTranslation, v))

                                    .collect(Collectors.toList());
        }

        public float getMinRadius() {
            return minRadius;
        }

        public StarLayer setMinRadius(float minRadius) {
            this.minRadius = minRadius;
            return this;
        }

        public float getMaxRadius() {
            return maxRadius;
        }

        public StarLayer setMaxRadius(float maxRadius) {
            this.maxRadius = maxRadius;
            return this;
        }

        public float getMinCount() {
            return minCount;
        }

        public StarLayer setMinCount(int minCount) {
            this.minCount = minCount;
            return this;
        }

        public float getMaxCount() {
            return maxCount;
        }

        public StarLayer setMaxCount(int maxCount) {
            this.maxCount = maxCount;
            return this;
        }

        public float getTranslationMultiplier() {
            return translationMultiplier;
        }

        public StarLayer setTranslationMultiplier(float translationMultiplier) {
            this.translationMultiplier = translationMultiplier;
            return this;
        }
    }

    public void create() {

    }

    @Override
    public void render(ShapeRenderer renderer) {

    }

    @Override
    public ShapeRenderer.ShapeType getShapeType() {
        return ShapeRenderer.ShapeType.Filled;
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public List<? extends Renderable> getSubComponents() {
        return null;
    }

}
