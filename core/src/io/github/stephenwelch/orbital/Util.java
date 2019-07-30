package io.github.stephenwelch.orbital;

public class Util {

    public double normalizeAngleDegrees(double angle) {
        while(angle < 0) angle += 180;
        while(angle > 360) angle -= 180;
        return angle;
    }

}
