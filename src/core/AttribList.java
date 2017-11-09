package core;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * Wrapper class for every object's vertex array object and vertex buffer
 * objects
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class AttribList {

    /**
     * Pointer for the object's vao
     */
    private int vao;

    /**
     * Pointer for the object's position vbo
     */
    private int vertVbo;
    /**
     * Pointer for the object's indices vbo
     */
    private int indexVbo;
    /**
     * Pointer for the object's texture coordinates vbo
     */
    private int texVbo;


    /**
     * Creates a new AttribList, allocates memory for the vao and each vbo
     */
    public AttribList() {
        vao = GL30.glGenVertexArrays();

        vertVbo = GL15.glGenBuffers();
        indexVbo = GL15.glGenBuffers();
        texVbo = GL15.glGenBuffers();
    }


    /**
     * Gets the object's vao
     * 
     * @return Object's vao pointer
     */
    public int getVao() {
        return vao;
    }


    /**
     * Gets the object's position vbo
     * 
     * @return Object's position vbo pointer
     */
    public int getVertVbo() {
        return vertVbo;
    }


    /**
     * Gets the object's indices vbo
     * 
     * @return Object's indices vbo pointer
     */
    public int getIndexVbo() {
        return indexVbo;
    }


    /**
     * Gets the object's texture coordinates vbo
     * 
     * @return Object's texture coordinates vbo pointer
     */
    public int getTexVbo() {
        return texVbo;
    }


    /**
     * Deletes memory allocated to the vao and every vbo
     */
    public void deleteAll() {
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(vertVbo);
        GL15.glDeleteBuffers(indexVbo);
        GL15.glDeleteBuffers(texVbo);
    }

}
