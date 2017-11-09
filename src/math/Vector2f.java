package math;

/**
 * This class represents a two-dimensional vector containing float values.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class Vector2f {

    /**
     * The x and y components of the vector
     */
    private float x, y;


    /**
     * Initializes a new two-dimensional vector
     * 
     * @param x
     *            The x component of the vector
     * @param y
     *            The y component of the vector
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Sets the components of this Vector2f object to the given values
     * 
     * @param x
     *            The new x component of the vector
     * @param y
     *            The new y component of the vector
     * @return A reference to this Vector2f object
     */
    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }


    /**
     * Gets the x component of the vector
     * 
     * @return The x component of the vector
     */
    public float getX() {
        return x;
    }


    /**
     * Sets the x component of the vector
     * 
     * @param x
     *            The new x component of the vector
     */
    public void setX(float x) {
        this.x = x;
    }


    /**
     * Gets the y component of the vector
     * 
     * @return The y component of the vector
     */
    public float getY() {
        return y;
    }


    /**
     * Sets the y component of the vector
     * 
     * @param y
     *            The new y component of the vector
     */
    public void setY(float y) {
        this.y = y;
    }

}
