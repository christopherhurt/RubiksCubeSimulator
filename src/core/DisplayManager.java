package core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * This class manages the OpenGL display used as the cube interface.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class DisplayManager {

    /**
     * The width of the window in pixels
     */
    public static final int WIDTH = 1280;
    /**
     * The height of the window in pixels
     */
    public static final int HEIGHT = 720;
    /**
     * The maximum frame rate of the display
     */
    public static final int FPS_CAP = 120;
    /**
     * The title displayed on the window
     */
    public static final String TITLE = "Rubik's Cube Simulator";


    /**
     * Creates a new display
     */
    public static void create() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
            Display.setTitle(TITLE);
        }
        catch (LWJGLException e) {
            System.err.println("Display failed to open");
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT); // Specifying the whole display to
                                              // be used for rendering
    }


    /**
     * Updates and synchronizes the display with the specified frame rate
     */
    public static void update() {
        Display.sync(FPS_CAP);
        Display.update();
    }


    /**
     * Closes the display when the program is terminated
     */
    public static void close() {
        Display.destroy();
    }

}
