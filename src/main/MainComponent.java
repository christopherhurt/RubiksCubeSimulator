package main;

import java.awt.Color;
import java.io.File;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import colors.BasicColors;
import core.DisplayManager;
import core.Handler;
import core.ShaderProgram;
import cube.RubiksCube;
import input.Camera;
import input.Console;

/**
 * This application is a Rubik's Cube Simulator that uses a command line
 * interface for user input, and uses the LWJGL wrapper for OpenGL to render the
 * cube. The user can perform turns on the cube, solve the cube, scramble the
 * cube, and customize the cube. These are all features supported in version 1.1
 * of the program, and future versions plan to implement a search algorithm that
 * can find a solution for the cube in any given state.
 * 
 * The MainComponent class contains the program's main method and manages its
 * execution.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class MainComponent {

    /**
     * The background color of the window
     */
    private static final Color BACKGROUND_COLOR = new Color(126, 192, 238);

    /**
     * Keeps track of whether or not the program is still being executed
     */
    private static boolean running = false;


    /**
     * Main method of the program, launches and initializes the program, manages
     * its execution, and frees memory and closes the program upon termination
     * 
     * @param args
     *            Unused launch arguments
     */
    public static void main(String args[]) {
        // Allowing natives in separate folder after exporting, changes their
        // file path
        System.setProperty("org.lwjgl.librarypath", new File("libs/natives")
            .getAbsolutePath());

        // Printing console header on launch
        System.out.println("-----------------------------------");
        System.out.println("Welcome to Rubik's Cube Simulator!");
        System.out.println("Type \"help\" for a list of available commands.");
        System.out.println("-----------------------------------");

        // Initializing the display, shader program, camera, and rubik's cube
        DisplayManager.create();
        ShaderProgram shader = new ShaderProgram(Handler.PROJ_MAT);
        Camera camera = new Camera();
        RubiksCube cube = new RubiksCube(new BasicColors(), 1, 1, 1, -30, 45, 0,
            0, 0, 2);

        // Launches the console in a new thread
        new Thread("Console") {
            @Override
            public void run() {
                Console console = new Console(cube);
                running = true;
                while (running) {
                    console.doCommand();
                }
            }
        }.start();

        // Enabling the OpenGL depth test to avoid incorrect overlapping while
        // rendering
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Main loop
        while (!Display.isCloseRequested()) {
            // Clearing the display before each render
            GL11.glClearColor(BACKGROUND_COLOR.getRed() / 255.0f,
                BACKGROUND_COLOR.getGreen() / 255.0f, BACKGROUND_COLOR.getBlue()
                    / 255.0f, 1.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Updating and rendering the Rubik's cube
            shader.start();
            Handler.cycle(shader, camera);
            shader.stop();

            // Drawing everything to the screen
            Display.update();

            System.gc(); // Running the garbage collector to prevent memory
                         // leaking
        }
        running = false;

        // Deleting memory
        Handler.deleteAll();
        shader.freeMemory();
        DisplayManager.close();

        System.exit(0); // Ensures that all threads are stopped
    }

}
