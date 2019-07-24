package io.github.stephenwelch.orbital;

public class Transform {

    public final Translation translation;
    public final Rotation rotation;

    public Transform(Translation translation, Rotation rotation) {
        this.translation = translation;
        this.rotation = rotation;
    }

    public Transform(double x, double y, Rotation theta) {
        this.translation = new Translation(x, y);
        this.rotation = theta;
    }

}
