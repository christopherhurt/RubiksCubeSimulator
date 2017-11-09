package core;

import java.util.concurrent.CopyOnWriteArrayList;
import input.Camera;
import math.Matrix4f;

/**
 * This is a static class that manages updating and rendering of all objects on
 * the screen.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class Handler {

    /**
     * Matrix responsible for perspective division and depth perception when
     * rendering the cube
     */
    public static final Matrix4f PROJ_MAT = new Matrix4f(true).genProjection();

    /**
     * List that contains all objects needed to be updated and rendered to the
     * screen, is copy on write to avoid concurrent modification errors
     */
    private static CopyOnWriteArrayList<RenderObject> objects =
        new CopyOnWriteArrayList<RenderObject>();


    /**
     * Loops through every object, first updates then renders them
     * 
     * @param shader
     *            Shader being passed each object's updated uniform variables
     * @param camera
     *            Camera used to updated rotation of cube
     */
    public static void cycle(ShaderProgram shader, Camera camera) {
        for (RenderObject object : objects) {
            object.update(shader, camera);
            object.render();
        }
    }


    /**
     * Adds an object to the list of objects needed to be updated and rendered
     * 
     * @param object
     *            Object being added to the update list
     */
    public static void add(RenderObject object) {
        objects.add(object);
    }


    /**
     * Removes an object from the list of objects needed to be updated and
     * rendered
     * 
     * @param object
     *            Object being removed from the update list
     */
    public static void remove(RenderObject object) {
        objects.remove(object);
    }


    /**
     * Deletes memory allocated for every object upon closing the program
     */
    public static void deleteAll() {
        for (RenderObject object : objects) {
            object.getAttribs().deleteAll();
        }
    }

}
