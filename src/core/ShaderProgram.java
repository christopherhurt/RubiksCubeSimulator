package core;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import math.Matrix4f;
import utilities.Constants;

/**
 * Used to generate and manage this application's GLSL shader program
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class ShaderProgram {

    /**
     * File path of the vertex shader
     */
    private final String VERTEX_FILE = "shaders/vertexShader.glsl";
    /**
     * File path of the fragment shader
     */
    private final String FRAGMENT_FILE = "shaders/fragmentShader.glsl";

    /**
     * Pointers for the vertex shader, fragment shader, and entire shader
     * program
     */
    private int programID, vertexID, fragmentID;
    /**
     * Pointers for the uniform variables used in the shader program
     */
    private int transformLoc, projectionLoc, colorLoc;


    /**
     * Initializes a new Shader object and creates a new shader program
     * 
     * @param projectionMatrix
     *            The projection matrix to be loaded to the shader program's
     *            corresponding uniform variable
     */
    public ShaderProgram(Matrix4f projectionMatrix) {
        // Creating the shader program and corresponding vertex and fragment
        // shaders
        programID = GL20.glCreateProgram();
        vertexID = loadShader(VERTEX_FILE, GL20.GL_VERTEX_SHADER);
        fragmentID = loadShader(FRAGMENT_FILE, GL20.GL_FRAGMENT_SHADER);

        // Attaching the vertex and fragment shader files to the shader program
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        // Binding the shader's position input to the first attribute location
        GL20.glBindAttribLocation(programID, 0, "position");

        // Linking and validating the program
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        // Getting the locations of the uniform variables
        transformLoc = GL20.glGetUniformLocation(programID, "transformMatrix");
        projectionLoc = GL20.glGetUniformLocation(programID,
            "projectionMatrix");
        colorLoc = GL20.glGetUniformLocation(programID, "color");

        // Loading the projection matrix into its corresponding uniform variable
        start();
        loadMatrix(Constants.MAT_PROJECTION, projectionMatrix.toBuffer());
        stop();
    }


    /**
     * Starts the shader program
     */
    public void start() {
        GL20.glUseProgram(programID);
    }


    /**
     * Loads a transformation or projection matrix into its corresponding
     * uniform variable
     * 
     * @param matrixType
     *            The type of matrix being loaded
     * @param mat
     *            The matrix being loaded as a uniform variable
     */
    public void loadMatrix(int matrixType, FloatBuffer mat) {
        if (matrixType == Constants.MAT_TRANSFORM) {
            GL20.glUniformMatrix4(transformLoc, false, mat);
        }
        else if (matrixType == Constants.MAT_PROJECTION) {
            GL20.glUniformMatrix4(projectionLoc, false, mat);
        }
        else {
            System.err.println(
                "Please load all matrices using a valid matrix type!");
            System.exit(-1);
        }
    }


    /**
     * Loads a texture color to the corresponding uniform variable after
     * normalizing its RGB components
     * 
     * @param color
     *            The color being loaded to the shader's corresponding uniform
     *            variable
     */
    public void loadColor(Color color) {
        GL20.glUniform4f(colorLoc, color.getRed() / 255.0f, color.getGreen()
            / 255.0f, color.getBlue() / 255.0f, 1.0f);
    }


    /**
     * Stops the shader program
     */
    public void stop() {
        GL20.glUseProgram(0);
    }


    /**
     * De-attaches all shader files from the shader program, deletes the shader
     * program and all corresponding files
     */
    public void freeMemory() {
        stop();
        GL20.glDetachShader(programID, vertexID);
        GL20.glDetachShader(programID, fragmentID);
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
        GL20.glDeleteProgram(programID);
    }


    /**
     * Loads the given shader file into memory
     * 
     * @param file
     *            The file path of the shader being loaded
     * @param type
     *            The type of shader being loaded, either vertex or fragment
     * @return A pointer to the newly loaded shader file
     */
    private int loadShader(String file, int type) {
        StringBuilder assembler = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                assembler.append(line + "\n"); // Loading each line of the file
            }
            reader.close();
        }
        catch (IOException e) {
            System.err.println("Failed to read shader program");
            e.printStackTrace();
            System.exit(-1);
        }

        // Creating and compiling the shader
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, assembler);
        GL20.glCompileShader(shaderID);

        // Checking if the shader compiled correctly
        if (GL20.glGetShaderi(shaderID,
            GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println(
                "Error in shader program, failed to compile shader:");
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 1000));
            System.exit(-1);
        }

        return shaderID;
    }

}
