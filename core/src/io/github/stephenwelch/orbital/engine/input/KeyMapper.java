package io.github.stephenwelch.orbital.engine.input;

import com.badlogic.gdx.Gdx;
import io.github.stephenwelch.orbital.engine.GameModule;

import java.util.HashMap;
import java.util.Map;

public abstract class KeyMapper<F extends Enum> implements GameModule {

    private Map<F, KeyMapping> keyMap = new HashMap<>();

    @Override
    public void create() {
        keyMap.forEach((function, mapping) -> {
            Gdx.app.log("KEY_MAPPER", String.format("Function %s registered to key %s with keycode %s", function, mapping.getKey(), mapping.getKeycode()));
        });
    }

    @Override
    public void update() {
        keyMap.forEach((function, configuration) -> configuration.update());
    }

    @Override
    public void dispose() {

    }

    public boolean getKey(F function) {
        return keyMap.get(function).isActivated();
    }

    public Map<F, KeyMapping> getKeyMap() {
        return keyMap;
    }
}
