package core;

import java.awt.Color;

/**
 * This is a wrapper class containing information about an object model.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class Model {

    /**
     * Mesh used to specify the shape and position/location of the model
     */
    private Mesh mesh;
    /**
     * Single color used as the model's texture color
     */
    private Color color;


    /**
     * Creates a new Model object
     * 
     * @param mesh
     *            Mesh used by the model
     * @param color
     *            Color used for the model's texture
     */
    public Model(Mesh mesh, Color color) {
        this.mesh = mesh;
        this.color = color;
    }


    /**
     * Gets the model's mesh
     * 
     * @return Model's mesh
     */
    public Mesh getMesh() {
        return mesh;
    }


    /**
     * Gets the model's texture color
     * 
     * @return Model's texture color
     */
    public Color getColor() {
        return color;
    }


    /**
     * Sets the model's texture color, used when changing the cube's color
     * scheme
     * 
     * @param color
     *            The new texture color to be used by the model
     */
    public void setColor(Color color) {
        this.color = color;
    }

}
