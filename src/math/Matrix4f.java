package math;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import core.DisplayManager;

/**
 * Represents a 4x4 matrix of float values, contains methods for necessary
 * matrix calculations
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class Matrix4f {

    /**
     * Field of view angle of the camera in degrees, used in perspective
     * projection calculation
     */
    public static final float FOV = 70.0f;
    /**
     * Maximum distance from the camera at which objects are rendered (measured
     * in world space), used in perspective projection calculation
     */
    public static final float FAR_PLANE = 1000.0f;
    /**
     * Minimum distance from the camera at which objects are rendered (measured
     * in world space), used in perspective projection calculation
     */
    public static final float NEAR_PLANE = 0.1f;

    /**
     * A two dimensional array containing the matrix's values, the inner arrays
     * represent column positions and the outer arrays represent row positions
     */
    private float[][] mat;
    /**
     * x, y, and z scale values used when generating a scale matrix
     */
    private float scaleX, scaleY, scaleZ;
    /**
     * x, y, and z rotation values used when generating rotation matrices
     */
    private float rotX, rotY, rotZ;
    /**
     * x, y, and z translation values used when generating a translation matrix
     */
    private float transX, transY, transZ;


    /**
     * Initializes a new Matrix4f object, either initialized to contain all 0
     * values or as the 4x4 identity matrix
     * 
     * @param setIdentity
     *            Determines whether the matrix is initialized as the 4x4
     *            identity matrix
     */
    public Matrix4f(boolean setIdentity) {
        mat = new float[4][4];

        if (setIdentity) {
            initIdentity();
        }
    }


    /**
     * Initializes this matrix as the 4x4 identity matrix
     * 
     * @return A reference to this Matrix4f object
     */
    public Matrix4f initIdentity() {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (r == c) {
                    mat[r][c] = 1;
                }
                else {
                    mat[r][c] = 0;
                }
            }
        }

        return this;
    }


    /**
     * Sets this matrix as a scale matrix
     * 
     * @param x
     *            Scale in the x-direction
     * @param y
     *            Scale in the y-direction
     * @param z
     *            Scale in the z-direction
     * @return A reference to this Matrix4f object
     */
    public Matrix4f setScale(float x, float y, float z) {
        scaleX = x;
        scaleY = y;
        scaleZ = z;

        mat[0][0] = x;
        mat[1][1] = y;
        mat[2][2] = z;

        return this;
    }


    /**
     * Sets this matrix as an x-rotation matrix
     * 
     * @param theta
     *            The amount of rotation around the x-axis in degrees
     * @return A reference to this Matrix4f object
     */
    public Matrix4f setRotationX(float theta) {
        rotX = theta;

        float rads = (float)Math.toRadians(theta);

        mat[0][0] = 1;
        mat[0][1] = 0;
        mat[0][2] = 0;
        mat[0][3] = 0;
        mat[1][0] = 0;
        mat[1][1] = (float)Math.cos(rads);
        mat[1][2] = (float)-Math.sin(rads);
        mat[1][3] = 0;
        mat[2][0] = 0;
        mat[2][1] = (float)Math.sin(rads);
        mat[2][2] = (float)Math.cos(rads);
        mat[2][3] = 0;
        mat[3][0] = 0;
        mat[3][1] = 0;
        mat[3][2] = 0;
        mat[3][3] = 1;

        return this;
    }


    /**
     * Sets this matrix as a y-rotation matrix
     * 
     * @param theta
     *            The amount of rotation around the y-axis in degrees
     * @return A reference to this Matrix4f object
     */
    public Matrix4f setRotationY(float theta) {
        rotY = theta;

        float rads = (float)Math.toRadians(theta);

        mat[0][0] = (float)Math.cos(rads);
        mat[0][1] = 0;
        mat[0][2] = (float)Math.sin(rads);
        mat[0][3] = 0;
        mat[1][0] = 0;
        mat[1][1] = 1;
        mat[1][2] = 0;
        mat[1][3] = 0;
        mat[2][0] = (float)-Math.sin(rads);
        mat[2][1] = 0;
        mat[2][2] = (float)Math.cos(rads);
        mat[2][3] = 0;
        mat[3][0] = 0;
        mat[3][1] = 0;
        mat[3][2] = 0;
        mat[3][3] = 1;

        return this;
    }


    /**
     * Sets this matrix as a z-rotation matrix
     * 
     * @param theta
     *            The amount of rotation around the z-axis in degrees
     * @return A reference to this Matrix4f object
     */
    public Matrix4f setRotationZ(float theta) {
        rotZ = theta;

        float rads = (float)Math.toRadians(theta);

        mat[0][0] = (float)Math.cos(rads);
        mat[0][1] = (float)-Math.sin(rads);
        mat[0][2] = 0;
        mat[0][3] = 0;
        mat[1][0] = (float)Math.sin(rads);
        mat[1][1] = (float)Math.cos(rads);
        mat[1][2] = 0;
        mat[1][3] = 0;
        mat[2][0] = 0;
        mat[2][1] = 0;
        mat[2][2] = 1;
        mat[2][3] = 0;
        mat[3][0] = 0;
        mat[3][1] = 0;
        mat[3][2] = 0;
        mat[3][3] = 1;

        return this;
    }


    /**
     * Sets this matrix as a translation matrix
     * 
     * @param x
     *            Translation in the x-direction
     * @param y
     *            Translation in the y-direction
     * @param z
     *            Translation in the z-direction
     * @return A reference to this Matrix4f object
     */
    public Matrix4f setTranslation(float x, float y, float z) {
        transX = x;
        transY = y;
        transZ = z;

        mat[0][3] = x;
        mat[1][3] = y;
        mat[2][3] = z;

        return this;
    }


    /**
     * Sets this matrix as a perspective projection matrix, used for depth
     * perception
     */
    public Matrix4f genProjection() {
        float aspectRatio = (float)DisplayManager.WIDTH / DisplayManager.HEIGHT;
        float denominator = (float)Math.tan(Math.toRadians(FOV / 2));

        mat[0][0] = 1.0f / (denominator * aspectRatio);
        mat[1][1] = 1.0f / denominator;
        mat[2][2] = -(NEAR_PLANE + FAR_PLANE) / (NEAR_PLANE - FAR_PLANE);
        mat[2][3] = 2 * NEAR_PLANE * FAR_PLANE / (NEAR_PLANE - FAR_PLANE);
        mat[3][2] = 1;
        mat[3][3] = 0;

        return this;
    }


    /**
     * Multiplies this matrix with another 4x4 matrix
     * 
     * @param m
     *            The Matrix4f object being multiplied with this one
     * @return A new Matrix4f object containing the product of this matrix and
     *         matrix m
     */
    public Matrix4f mul(Matrix4f m) {
        Matrix4f product = new Matrix4f(false);

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                float value = mat[r][0] * m.getValue(0, c) + mat[r][1] * m
                    .getValue(1, c) + mat[r][2] * m.getValue(2, c) + mat[r][3]
                        * m.getValue(3, c);

                product.setValue(r, c, value);
            }
        }

        return product;
    }


    /**
     * Converts this matrix to a float buffer used to load uniform matrix
     * variables to the shader program
     * 
     * @return A FloatBuffer object containing a float buffer representation of
     *         this matrix
     */
    public FloatBuffer toBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                buffer.put(mat[r][c]);
            }
        }

        buffer.flip();
        return buffer;
    }


    /**
     * Gets the matrix's value at the specified row and column
     * 
     * @param r
     *            The row of the value to be retrieved (index with a value 0-3)
     * @param c
     *            The column of the value to be retrieved (index with a value
     *            0-3)
     * @return The value at the specified row and column of this matrix
     */
    public float getValue(int r, int c) {
        return mat[r][c];
    }


    /**
     * Sets the matrix's value at the specified row and column
     * 
     * @param r
     *            The row of the value to be set (index with a value 0-3)
     * @param c
     *            The column of the value to be set (index with a value 0-3)
     * @param value
     *            The new value at the matrix's given row and column
     */
    public void setValue(int r, int c, float value) {
        mat[r][c] = value;
    }


    /**
     * Gets the matrix's scale value in the x-direction
     * 
     * @precondition This matrix has been set as a scale matrix
     * @return Scale value in the x-direction
     */
    public float getScaleX() {
        return scaleX;
    }


    /**
     * Gets the matrix's scale value in the y-direction
     * 
     * @precondition This matrix has been set as a scale matrix
     * @return Scale value in the y-direction
     */
    public float getScaleY() {
        return scaleY;
    }


    /**
     * Gets the matrix's scale value in the z-direction
     * 
     * @precondition This matrix has been set as a scale matrix
     * @return Scale value in the z-direction
     */
    public float getScaleZ() {
        return scaleZ;
    }


    /**
     * Gets the matrix's rotation value in the x-direction
     * 
     * @precondition This matrix has been set as an x-rotation matrix
     * @return Rotation value in the x-direction
     */
    public float getRotX() {
        return rotX;
    }


    /**
     * Gets the matrix's rotation value in the y-direction
     * 
     * @precondition This matrix has been set as an y-rotation matrix
     * @return Rotation value in the y-direction
     */
    public float getRotY() {
        return rotY;
    }


    /**
     * Gets the matrix's rotation value in the z-direction
     * 
     * @precondition This matrix has been set as an z-rotation matrix
     * @return Rotation value in the z-direction
     */
    public float getRotZ() {
        return rotZ;
    }


    /**
     * Gets the matrix's translation value in the x-direction
     * 
     * @precondition This matrix has been set as a translation matrix
     * @return Translation value in the x-direction
     */
    public float getTransX() {
        return transX;
    }


    /**
     * Gets the matrix's translation value in the y-direction
     * 
     * @precondition This matrix has been set as a translation matrix
     * @return Translation value in the y-direction
     */
    public float getTransY() {
        return transY;
    }


    /**
     * Gets the matrix's translation value in the z-direction
     * 
     * @precondition This matrix has been set as a translation matrix
     * @return Translation value in the z-direction
     */
    public float getTransZ() {
        return transZ;
    }

}
