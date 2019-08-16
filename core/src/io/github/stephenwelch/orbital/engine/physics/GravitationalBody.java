package io.github.stephenwelch.orbital.engine.physics;

import com.badlogic.gdx.physics.box2d.Body;

public interface GravitationalBody {

    float getMass();
    Body getBody();

}
