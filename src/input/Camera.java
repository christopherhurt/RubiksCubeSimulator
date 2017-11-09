package input;

import java.awt.MouseInfo;
import java.awt.Point;
import org.lwjgl.input.Mouse;
import math.Vector2f;

/**
 * This class represents a camera used to simulate rotation of the cube. Unlike
 * typical cameras used in graphical simulations, this class simply calculates
 * the amount of rotation of the cube's model, rather than doing a translation
 * the cube's coordinates into view space. This is okay because all of the cube
 * and quad models are positioned relative to the world space origin, and all of
 * their rotations are done relative to the world space origin.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class Camera {

    /**
     * The speed of the cube's rotation relative to computer processing speeds
     */
    private static final float ROTATION_SPEED = 50f;

    /**
     * Stores the most recent location of the mouse, used to keep the cube from
     * moving after the mouse is released
     */
    private Point basePoint;
    /**
     * Keeps track of the total amount of cube rotation since the mouse was last
     * pressed
     */
    private Vector2f totalRotation;
    /**
     * Checks whether the mouse is currently being held down
     */
    private boolean mouseLocked;


    /**
     * Initializes a new Camera object with no rotation
     */
    public Camera() {
        this.basePoint = new Point(0, 0);
        this.totalRotation = new Vector2f(0, 0);
        this.mouseLocked = false;
    }


    /**
     * Calculates the x and y rotations of the cube based on mouse input
     * 
     * @return A two-dimensional vector containing the x and y rotation of the
     *         cube (no specific units)
     */
    public Vector2f calcRotation() {
        // Getting location of mouse and whether left mouse button is being
        // pressed
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        float mX = (float)mousePos.getX();
        float mY = (float)mousePos.getY();
        boolean mousePressed = Mouse.isButtonDown(0);

        if (!mousePressed) { // Case where mouse is not being pressed
            mouseLocked = false;
            totalRotation.set(0, 0);
        }
        else if (!mouseLocked) { // Case where mouse has just been pressed
            basePoint.setLocation(mX, mY); // Setting base point to mouse
                                           // location
            mouseLocked = true;
            totalRotation.set(0, 0); // Resetting rotation
        }
        else {
            // Calculating amount of mouse movement
            float dX = (float)((mX - basePoint.getX()) * ROTATION_SPEED);
            float dY = (float)((mY - basePoint.getY()) * ROTATION_SPEED);

            totalRotation.set(-dY, -dX); // Rotating in direction opposite to
                                         // mouse movement with a speed
                                         // proportional to the amount of mouse
                                         // movement
            basePoint.setLocation(mX, mY); // Updating base pointer after
                                           // rotation is updated to keep cube
                                           // from moving after releasing the
                                           // mouse
        }

        return totalRotation;
    }

}
