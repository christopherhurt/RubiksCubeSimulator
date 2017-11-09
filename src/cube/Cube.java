package cube;

import java.awt.Color;
import core.Mesh;
import core.Model;
import core.RenderObject;
import core.ShaderProgram;
import input.Camera;
import math.Vector2f;
import utilities.Constants;

/**
 * Represents the Rubik's cube object, not including its stickers
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class Cube extends RenderObject {

    /**
     * Data about the position of the cube's vertices
     */
    private static final float[] vertices = {
        -0.5f,  0.5f, -0.5f, // V0
         0.5f,  0.5f, -0.5f, // V1
        -0.5f, -0.5f, -0.5f, // V2
         0.5f, -0.5f, -0.5f, // V3
        -0.5f,  0.5f,  0.5f, // V4
         0.5f,  0.5f,  0.5f, // V5
        -0.5f, -0.5f,  0.5f, // V6
         0.5f, -0.5f,  0.5f, // V7
    };

    /**
     * Data about the order in which the cube's vertices should be rendered
     */
    private static final int[] indices = {
        0, 2, 1, 2, 3, 1, // F
        1, 3, 5, 3, 7, 5, // R
        5, 7, 6, 4, 5, 6, // B
        0, 4, 6, 0, 6, 2, // L
        1, 5, 4, 4, 0, 1, // U
        6, 7, 3, 6, 3, 2  // D
    };


    /**
     * Creates a new Rubik's cube object and cube model
     * 
     * @param color
     *            The color of the cube's plastic
     * @param scaleX
     *            Scale of object in x-direction relative to model size
     * @param scaleY
     *            Scale of object in y-direction relative to model size
     * @param scaleZ
     *            Scale of object in z-direction relative to model size
     * @param rotX
     *            Rotation of object around x-axis in degrees
     * @param rotY
     *            Rotation of object around y-axis in degrees
     * @param rotZ
     *            Rotation of object around z-axis in degrees
     * @param transX
     *            Translation of object in x-direction relative to model space
     *            origin in world space units
     * @param transY
     *            Translation of object in y-direction relative to model space
     *            origin in world space units
     * @param transZ
     *            Translation of object in z-direction relative to model space
     *            origin in world space units
     */
    public Cube(
        Color color,
        float scaleX,
        float scaleY,
        float scaleZ,
        float rotX,
        float rotY,
        float rotZ,
        float transX,
        float transY,
        float transZ) {

        super(genCubeModel(color), scaleX, scaleY, scaleZ, rotX, rotY, rotZ,
            transX, transY, transZ);
    }


    /**
     * Generates the cube model used by the Rubik's cube object
     * 
     * @param color
     *            The color of the cube's plastic
     * @return Model object representing the Rubik's cube, not including its
     *         stickers
     */
    private static Model genCubeModel(Color color) {
        return new Model(new Mesh(vertices, indices), color);
    }


    /**
     * Updates the Rubik's cube object before rendering
     */
    public void update(ShaderProgram shader, Camera camera) {
        // Calculating the change in the camera's rotation
        Vector2f rotChange = camera.calcRotation();

        // Used to adjust the final amount of rotation
        final float divisor = 100;

        // Updating the rotation of the cube
        matrixList.rotate(rotChange.getX() / divisor, rotChange.getY()
            / divisor, 0);
        matrixList.updateTransform();

        // Loading the cube's current transformation and plastic color to the
        // shader's corresponding uniform variables
        shader.loadMatrix(Constants.MAT_TRANSFORM, matrixList
            .getTransformMatrix().toBuffer());
        shader.loadColor(model.getColor());
    }

}
