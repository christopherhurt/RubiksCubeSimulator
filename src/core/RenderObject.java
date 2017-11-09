package core;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import input.Camera;
import utilities.BufferConverter;

/**
 * Represents an object that can be updated and rendered to the screen
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public abstract class RenderObject {

    /**
     * The model being used by the object, which specifies its mesh and texture
     * color
     */
    protected Model model;
    /**
     * Contains pointers to the object's vao and vbos that OpenGL utilizes for
     * rendering
     */
    protected AttribList attribs;
    /**
     * Contains information about the object's position, rotation, and scale,
     * used to transform from the object's model space to world space
     */
    protected MatrixList matrixList;


    /**
     * Initializes a new RenderObject, creates new vao and vbos for the object,
     * loads mesh data into its vao, adds the new object to the Handler
     * 
     * @param model
     *            Model used by the object containing mesh and color data
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
    public RenderObject(
        Model model,
        float scaleX,
        float scaleY,
        float scaleZ,
        float rotX,
        float rotY,
        float rotZ,
        float transX,
        float transY,
        float transZ) {

        this.model = model;

        matrixList = new MatrixList(scaleX, scaleY, scaleZ, rotX, rotY, rotZ,
            transX, transY, transZ);

        attribs = new AttribList();

        // Translating the mesh's vertex and index data from arrays to buffers
        FloatBuffer vertices = BufferConverter.arrayToBuffer(model.getMesh()
            .getVertices());
        IntBuffer indices = BufferConverter.arrayToBuffer(model.getMesh()
            .getIndices());

        GL30.glBindVertexArray(attribs.getVao());

        // Loading the object's vertex data into its vertex position vbo
        loadVbo(0, attribs.getVertVbo(), vertices, 3);

        // Loading the object's index data into its index vbo
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, attribs.getIndexVbo());
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices,
            GL15.GL_STATIC_DRAW);

        unbindAttribs();

        Handler.add(this);
    }


    /**
     * Loads specified buffer data into the specified vbo, stores that vbo in a
     * specified attribute
     * 
     * @param attribute
     *            Index of the vao's attribute used to store the given vbo
     * @param vbo
     *            vbo used to store the given buffer data
     * @param buffer
     *            Buffer containing data to be stored in the given vbo
     * @param dataSegmentLength
     *            Number of the buffer's indices used to store information about
     *            one vertex (e.g. 3 for a vertex's x, y, and z positions)
     */
    private void loadVbo(
        int attribute,
        int vbo,
        FloatBuffer buffer,
        int dataSegmentLength) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribute, dataSegmentLength, GL11.GL_FLOAT,
            false, 0, 0);
    }


    /**
     * Unbinds any array buffer, element array buffer, and vao currently being
     * used by the object
     */
    private void unbindAttribs() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }


    /**
     * Used to update the object before rendering, specific to each object
     * 
     * @param shader
     *            Used to load updated uniform variables to the shader program
     * @param camera
     *            Used to get information about the cube's rotation
     */
    public abstract void update(ShaderProgram shader, Camera camera);


    /**
     * Renders the object to the screen
     */
    public void render() {
        // Binding the object's vao and indices vbo for OpenGL to use, enabling
        // the vao's vertex position attribute
        GL30.glBindVertexArray(attribs.getVao());
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, attribs.getIndexVbo());
        GL20.glEnableVertexAttribArray(0);

        // Drawing the object to the screen
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh()
            .getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        // Disabling the vao's vertex position attribute, unbinding the vao and
        // indices vbo
        GL20.glDisableVertexAttribArray(0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }


    /**
     * Gets the object's model
     * 
     * @return Object's model
     */
    public Model getModel() {
        return model;
    }


    /**
     * Gets the object's attribute list
     * 
     * @return Object's attribute list
     */
    public AttribList getAttribs() {
        return attribs;
    }


    /**
     * Gets the object's matrix list
     * 
     * @return Object's matrix list
     */
    public MatrixList getMatrixList() {
        return matrixList;
    }

}
