package core;

import math.Matrix4f;

/**
 * This class contains all of the data for every matrix needed to construct an
 * object's transformation matrix
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class MatrixList {

    /**
     * Matrices needed to construct the transformation matrix
     */
    private Matrix4f scaleMatrix, rotXMatrix, rotYMatrix, rotZMatrix,
        translationMatrix;
    /**
     * Transformation matrix used to store the product of all of the
     * sub-matrices
     */
    private Matrix4f transformMatrix;


    /**
     * Initializes all of the matrices in the MatrixList object
     * 
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
    public MatrixList(
        float scaleX,
        float scaleY,
        float scaleZ,
        float rotX,
        float rotY,
        float rotZ,
        float transX,
        float transY,
        float transZ) {

        scaleMatrix = new Matrix4f(true).setScale(scaleX, scaleY, scaleZ);

        rotXMatrix = new Matrix4f(true).setRotationX(rotX);
        rotYMatrix = new Matrix4f(true).setRotationY(rotY);
        rotZMatrix = new Matrix4f(true).setRotationZ(rotZ);

        translationMatrix = new Matrix4f(true).setTranslation(transX, transY,
            transZ);

        transformMatrix = new Matrix4f(true);
        updateTransform(); // Multiplies all component matrices together to
                           // generate initial transformMatrix
    }


    /**
     * Scales the object relative to its previous size
     * 
     * @param scaleX
     *            Scale in x-direction
     * @param scaleY
     *            Scale in y-direction
     * @param scaleZ
     *            Scale in z-direction
     */
    public void scale(float scaleX, float scaleY, float scaleZ) {
        scaleMatrix.setScale(scaleMatrix.getScaleX() * scaleX, scaleMatrix
            .getScaleY() * scaleY, scaleMatrix.getScaleZ() * scaleZ);
    }


    /**
     * Rotates the object relative to its previous rotation
     * 
     * @param rotX
     *            Rotation around x-axis in degrees
     * @param rotY
     *            Rotation around y-axis in degrees
     * @param rotZ
     *            Rotation around z-axis in degrees
     */
    public void rotate(float rotX, float rotY, float rotZ) {
        rotXMatrix.setRotationX(rotXMatrix.getRotX() + rotX);
        rotYMatrix.setRotationY(rotYMatrix.getRotY() + rotY);
        rotZMatrix.setRotationZ(rotZMatrix.getRotZ() + rotZ);
    }


    /**
     * Translates the object relative to its previous position
     * 
     * @param transX
     *            Translation in x-direction in world space units
     * @param transY
     *            Translation in y-direction in world space units
     * @param transZ
     *            Translation in z-direction in world space units
     */
    public void translate(float transX, float transY, float transZ) {
        translationMatrix.setTranslation(translationMatrix.getTransX() + transX,
            translationMatrix.getTransY() + transY, translationMatrix
                .getTransZ() + transZ);
    }


    /**
     * Updates the object's transformation matrix using all component matrices,
     * with transformations applied in the following order: scale, rotation Z,
     * rotation Y, rotation X, translation
     */
    public void updateTransform() {
        transformMatrix = translationMatrix.mul(rotXMatrix).mul(rotYMatrix).mul(
            rotZMatrix).mul(scaleMatrix);
    }


    /**
     * Gets the object's scale matrix
     * 
     * @return Object's scale matrix
     */
    public Matrix4f getScaleMatrix() {
        return scaleMatrix;
    }


    /**
     * Gets the object's x-rotation matrix
     * 
     * @return Object's x-rotation matrix
     */
    public Matrix4f getRotXMatrix() {
        return rotXMatrix;
    }


    /**
     * Gets the object's y-rotation matrix
     * 
     * @return Object's y-rotation matrix
     */
    public Matrix4f getRotYMatrix() {
        return rotYMatrix;
    }


    /**
     * Gets the object's z-rotation matrix
     * 
     * @return Object's z-rotation matrix
     */
    public Matrix4f getRotZMatrix() {
        return rotZMatrix;
    }


    /**
     * Gets the object's translation matrix
     * 
     * @return Object's translation matrix
     */
    public Matrix4f getTranslationMatrix() {
        return translationMatrix;
    }


    /**
     * Gets the object's overall transformation matrix
     * 
     * @return Object's transformation matrix
     */
    public Matrix4f getTransformMatrix() {
        return transformMatrix;
    }

}
