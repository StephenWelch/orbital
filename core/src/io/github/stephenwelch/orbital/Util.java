package io.github.stephenwelch.orbital;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Util {

    public double normalizeAngleDegrees(double angle) {
        while(angle < 0) angle += 180;
        while(angle > 360) angle -= 180;
        return angle;
    }

    public static Vector2 truncateVector(Vector3 vector) {
        return new Vector2(vector.x, vector.y);
    }

}
