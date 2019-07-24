package io.github.stephenwelch.orbital;

public class Translation {

    public final double x, y;

    public Translation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Translation other) {
        return this.minus(other).hypot();
    }

    public Translation translateBy(Translation other) {
        return new Translation(this.x + other.x, this.y + other.y);
    }

    public Translation minus(Translation other) {
        return this.translateBy(other.inverse());
    }

    public Translation inverse() {
        return this.scale(-1);
    }

    public Translation scale(double factor) {
        return new Translation(x * factor, y * factor);
    }

    public double hypot() {
        return Math.hypot(x, y);
    }

    public Translation rotateBy(Rotation theta) {
       return new Translation(
               (x * theta.cos) - (y * theta.sin),
               (x * theta.sin) + (y * theta.cos)
       );
    }

}
