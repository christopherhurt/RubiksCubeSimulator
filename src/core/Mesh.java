package core;

/**
 * Wrapper class containing data about an object's mesh.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class Mesh {

    /**
     * Mesh's vertex positions
     */
    private float[] vertices;
    /**
     * Indices used to specify the order in which vertices should be rendered
     */
    private int[] indices;


    /**
     * Creates a new Mesh object
     * 
     * @param vertices
     *            A float array of vertex positions, where each vertex uses
     *            three consecutive values in the array: an x-position,
     *            y-position, and z-position in model space
     * @param indices
     *            An int array of indices used to specify the order in which
     *            vertices should be rendered
     */
    public Mesh(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }


    /**
     * Gets the mesh's vertices array
     * 
     * @return Mesh's vertices array
     */
    public float[] getVertices() {
        return vertices;
    }


    /**
     * Gets the mesh's indices array
     * 
     * @return Mesh's indices array
     */
    public int[] getIndices() {
        return indices;
    }

}
