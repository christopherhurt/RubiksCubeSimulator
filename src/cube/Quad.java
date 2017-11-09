package cube;

import java.awt.Color;
import core.MatrixList;
import core.Mesh;
import core.Model;
import core.RenderObject;
import core.ShaderProgram;
import input.Camera;
import utilities.Constants;

/**
 * Represents a quadrilateral rendered as a sticker on the Rubik's cube
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class Quad extends RenderObject {

    /**
     * Indices which specify the order in which vertices should be drawn
     */
    private static final int[] indices = {
        0, 2, 1, // Top half triangle
        1, 2, 3  // Bottom half triangle
    };


    /**
     * Creates a new Quad object
     * 
     * @param color
     *            The quad's texture color, or the color of the sticker
     * @param vertices
     *            Array containing the vertex positions of the quad
     * @param matrixList
     *            The MatrixList object used to manage the quad's
     *            transformations
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
    public Quad(
        Color color,
        float[] vertices,
        MatrixList matrixList,
        float scaleX,
        float scaleY,
        float scaleZ,
        float rotX,
        float rotY,
        float rotZ,
        float transX,
        float transY,
        float transZ) {

        super(genQuadModel(color, vertices), scaleX, scaleY, scaleZ, rotX, rotY,
            rotZ, transX, transY, transZ);
        this.matrixList = matrixList;
    }


    /**
     * Generates a new quad model
     * 
     * @param color
     *            The color of the model's texture
     * @param vertices
     *            The vertex positions of the model's mesh
     * @return A new quad model for this Quad object
     */
    private static Model genQuadModel(Color color, float[] vertices) {
        return new Model(new Mesh(vertices, indices), color);
    }


    /**
     * Sets the color of the quad, used for "moving" pieces of the cube around
     * 
     * @param color
     *            New color of the quad's texture
     */
    public void setColor(Color color) {
        model.setColor(color);
    }


    /**
     * Updates the Quad object before rendering
     * 
     * @param shader
     *            The quad's transformation matrix and color are loaded into
     *            this shader program's corresponding uniform variables
     * @param camera
     *            Reference to the Camera object used in this program, unused by
     *            this method
     */
    public void update(ShaderProgram shader, Camera camera) {
        shader.loadMatrix(Constants.MAT_TRANSFORM, matrixList
            .getTransformMatrix().toBuffer());
        shader.loadColor(model.getColor());
    }

}
