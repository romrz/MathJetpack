package mathjetpack;

/**
 * Created by rom on 13/10/14.
 */
public class Vector2 {

    public double x;
    public double y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds a vector to this vector
     *
     * @param vector
     */
    public void addVector(final Vector2 vector) {
        x += vector.x;
        y += vector.y;
    }

    /**
     * Adds a vector multiplied by a scale factor
     *
     * @param vector
     * @param scale
     */
    public void addScaledVector(final Vector2 vector, double scale) {
        x += vector.x * scale;
        y += vector.y * scale;
    }

    /**
     * Multiplies this vector by a scalar.
     * It doesn't modifies the vector
     *
     * @param n The scalar
     * @return
     */
    public Vector2 multiply(double n) {
        return new Vector2(x * n, y * n);
    }

}
