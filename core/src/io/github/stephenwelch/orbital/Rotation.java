package io.github.stephenwelch.orbital;

public class Rotation {

    public final double sin, cos;

    public Rotation(double sin, double cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public static Rotation fromDegrees(double degrees) {
        return Rotation.fromRadians(Math.toRadians(degrees));
    }

    public static Rotation fromRadians(double radians) {
        return new Rotation(Math.sin(radians), Math.cos(radians));
    }

    public double radians() {
        return Math.atan2(sin, cos);
    }

    public double degrees() {
        return Math.toDegrees(this.radians());
    }

    public Rotation rotateBy(Rotation other) {
        return new Rotation(
                (cos * other.sin) + (sin * other.cos),
                (cos * other.cos) - (sin * other.sin)
        );
    }

}
