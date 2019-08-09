package io.github.stephenwelch.orbital.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.engine.GameEntity;
import io.github.stephenwelch.orbital.engine.Renderable;
import io.github.stephenwelch.orbital.engine.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class StarLayer implements GameEntity, Renderable {
    private final float width, height;
    private float minRadius = 1.0f;
    private float maxRadius = 2.0f;
    private int minCount = 100;
    private int maxCount = 200;
    private Color minColor, maxColor = Color.WHITE;
    private float translationMultiplier = 0.5f;
    private final long seed;

    public StarLayer(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;
    }

    private List<Star> layer = new ArrayList<>();

    public void create() {
        int count = Util.getRandomNumber(minCount, maxCount, seed);
        Gdx.app.log("STAR_LAYER", String.format("Creating star layer with %s stars.", count));
        for(int index = 0; index < count; index++) {
            float x = Util.getRandomNumber(0f, width, seed);
            float y = Util.getRandomNumber(0f, height, seed);
            float radius = Util.getRandomNumber(minRadius, maxRadius, seed);
            Color color = minColor.lerp(maxColor, Util.getRandomNumber(0f, 1f, seed));
            Star star = new Star(new Vector2(x, y), radius, color);
            layer.add(star);
//            Gdx.app.debug("STAR_LAYER", String.format("Created star: %s", star));
        }
        layer.forEach(GameEntity::create);
    }

    @Override
    public void update() {
        layer.forEach(GameEntity::update);
    }

    @Override
    public void dispose() {
        layer.forEach(GameEntity::dispose);
    }

    public void translate(Vector2 translation) {
        Vector2 scaledTranslation = translation.cpy().scl(translationMultiplier);
        layer = layer.stream()
                .map(c -> c.setPosition(Util.translateVector(scaledTranslation, c.getPosition())))
                .collect(Collectors.toList());
    }

    public void scroll(Vector2 translation) {
//        translate(translation);

        layer.stream().filter(s -> !Renderer.getInstance().isInWindow(s.getPosition())).forEach(Star::moveIntoScreenBounds);
    }

    @Override
    public void render(ShapeRenderer renderer) {

    }

    @Override
    public ShapeRenderer.ShapeType getShapeType() {
        return null;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public List<? extends Renderable> getSubComponents() {
        return layer;
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

    public Color getMinColor() {
        return minColor;
    }

    public StarLayer setMinColor(Color minColor) {
        this.minColor = minColor;
        return this;
    }

    public Color getMaxColor() {
        return maxColor;
    }

    public StarLayer setMaxColor(Color maxColor) {
        this.maxColor = maxColor;
        return this;
    }

}
