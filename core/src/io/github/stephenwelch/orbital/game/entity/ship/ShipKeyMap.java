package io.github.stephenwelch.orbital.game.entity.ship;

import com.badlogic.gdx.Gdx;
import com.google.gson.reflect.TypeToken;
import io.github.stephenwelch.orbital.Util;
import io.github.stephenwelch.orbital.engine.input.KeyMapper;

public class ShipKeyMap extends KeyMapper<ShipKeyFunctions> {
    private static ShipKeyMap ourInstance = Util.loadFromJson(Gdx.files.internal("conf/ship_keys.json"), new TypeToken<ShipKeyMap>(){});

    public static ShipKeyMap getInstance() {
        return ourInstance;
    }

    private ShipKeyMap() {
    }
}
