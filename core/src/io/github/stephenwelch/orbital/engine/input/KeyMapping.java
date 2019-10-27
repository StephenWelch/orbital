package io.github.stephenwelch.orbital.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyMapping {

    private String key;
    private ActivationType activationType;
    private transient Integer keycode = null;
    private transient boolean lastActivated = false;
    private transient boolean activated = false;

    public KeyMapping(String key, ActivationType activationType) {
        this.key = key;
        this.activationType = activationType;
    }

    public enum ActivationType {
        TOGGLE,
        WHEN_PRESSED,
        WHILE_PRESSED
    }

    public void update() {

        if(keycode == null) {
            keycode = Input.Keys.valueOf(key);
        }

        switch(activationType) {
            case TOGGLE:
                if(Gdx.input.isKeyJustPressed(keycode)) {
                    activated = !activated;
                }
                break;
            case WHEN_PRESSED:
                activated = Gdx.input.isKeyJustPressed(keycode);
                break;
            case WHILE_PRESSED:
            default:
                activated = Gdx.input.isKeyPressed(keycode);
                break;
        }
        if(isRisingEdge()) {
            System.out.println("Key " + key + " pressed!");
        }

        lastActivated = activated;
    }

    public String getKey() {
        return key;
    }

    public Integer getKeycode() {
        return keycode;
    }

    public ActivationType getActivationType() {
        return activationType;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean hasChanged() {
        return activated != lastActivated;
    }

    public boolean isRisingEdge() {
        return activated && hasChanged();
    }

    public boolean isFallingEdge() {
        return !activated && hasChanged();
    }

}
