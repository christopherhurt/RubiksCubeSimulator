package cube;

import colors.ColorScheme;
import utilities.Constants;

/**
 * Contains all data necessary for updating and rendering the Rubik's cube
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class RubiksCube {

    /**
     * The distance between stickers and space along the edges on the cube
     */
    private static final float BLANK_SPACE = 0.05f;
    /**
     * The size length of each of the cube's stickers
     */
    private static final float TILE_SIZE = (1 - 4.0f * BLANK_SPACE) / 3.0f;

    /**
     * The model used to render the plastic of the cube
     */
    private Cube cube;
    /**
     * Stores information about the state of the cube and locations of all the
     * stickers
     */
    private CubeBuffer cubeBuffer;
    /**
     * The models used to represent all of the cube' stickers
     */
    private Quad[][] tiles;
    /**
     * Stores information about the color of the cube's plastic and color of
     * each face's stickers
     */
    private ColorScheme colorScheme;


    /**
     * Constructs the models and buffer required to represent the RubiksCube
     * object
     * 
     * @param colorScheme
     *            Contains information about plastic and sticker colors
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
    public RubiksCube(
        ColorScheme colorScheme,
        float scaleX,
        float scaleY,
        float scaleZ,
        float rotX,
        float rotY,
        float rotZ,
        float transX,
        float transY,
        float transZ) {
        this.cubeBuffer = new CubeBuffer();
        this.tiles = new Quad[6][9];
        this.colorScheme = colorScheme;

        // Creating a new cube model
        cube = new Cube(colorScheme.getPlasticColor(), scaleX, scaleY, scaleZ,
            rotX, rotY, rotZ, transX, transY, transZ);

        genTiles(scaleX, scaleY, scaleZ, // Generates the models for the cube's
                                         // stickers
            rotX, rotY, rotZ, transX, transY, transZ);
    }


    /**
     * Generates the models used to render the cube's stickers
     * While tiles could reuse the same mesh with different transformation
     * matrices, giving each sticker its own model and manually setting
     * transformations was favored for simplicity
     * 
     * @param scaleX
     *            Scale of the cube in x-direction
     * @param scaleY
     *            Scale of the cube in y-direction
     * @param scaleZ
     *            Scale of the cube in z-direction
     * @param rotX
     *            Rotation of the cube about the x-axis
     * @param rotY
     *            Rotation of the cube about the y-axis
     * @param rotZ
     *            Rotation of the cube about the z-axis
     * @param transX
     *            Position of the cube in the x-direction
     * @param transY
     *            Position of the cube in the y-direction
     * @param transZ
     *            Position of the cube in the z-direction
     */
    private void genTiles(
        float scaleX,
        float scaleY,
        float scaleZ,
        float rotX,
        float rotY,
        float rotZ,
        float transX,
        float transY,
        float transZ) {
        // Front face stickers
        float depth = -0.501f;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                float botLeftX = depth + BLANK_SPACE * (c + 1) + TILE_SIZE * c;
                float botLeftY = depth + BLANK_SPACE * (r + 1) + TILE_SIZE * r;

                float[] vertices = { botLeftX, botLeftY + TILE_SIZE, depth, // V0
                    botLeftX + TILE_SIZE, botLeftY + TILE_SIZE, depth,      // V1
                    botLeftX, botLeftY, depth,                              // V2
                    botLeftX + TILE_SIZE, botLeftY, depth                   // V3
                };

                tiles[0][r * 3 + c] = new Quad(colorScheme.getfCol(), vertices,
                    cube.getMatrixList(), scaleX, scaleY, scaleZ, rotX, rotY,
                    rotZ, transX, transY, transZ);
            }
        }

        // Up face stickers
        depth = 0.501f;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                float botLeftX = -depth + BLANK_SPACE * (c + 1) + TILE_SIZE * c;
                float botLeftZ = -depth + BLANK_SPACE * (r + 1) + TILE_SIZE * r;

                float[] vertices = { botLeftX, depth, botLeftZ + TILE_SIZE, // V0
                    botLeftX + TILE_SIZE, depth, botLeftZ + TILE_SIZE,      // V1
                    botLeftX, depth, botLeftZ,                              // V2
                    botLeftX + TILE_SIZE, depth, botLeftZ                   // V3
                };

                tiles[1][r * 3 + c] = new Quad(colorScheme.gettCol(), vertices,
                    cube.getMatrixList(), scaleX, scaleY, scaleZ, rotX, rotY,
                    rotZ, transX, transY, transZ);
            }
        }

        // Right face stickers
        depth = 0.501f;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                float botLeftZ = -depth + BLANK_SPACE * (c + 1) + TILE_SIZE * c;
                float botLeftY = -depth + BLANK_SPACE * (r + 1) + TILE_SIZE * r;

                float[] vertices = { depth, botLeftY + TILE_SIZE, botLeftZ, // V0
                    depth, botLeftY + TILE_SIZE, botLeftZ + TILE_SIZE,      // V1
                    depth, botLeftY, botLeftZ,                              // V2
                    depth, botLeftY, botLeftZ + TILE_SIZE                   // V3
                };

                tiles[2][r * 3 + c] = new Quad(colorScheme.getrCol(), vertices,
                    cube.getMatrixList(), scaleX, scaleY, scaleZ, rotX, rotY,
                    rotZ, transX, transY, transZ);
            }
        }

        // Down face stickers
        depth = -0.501f;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                float topLeftX = depth + BLANK_SPACE * (c + 1) + TILE_SIZE * c;
                float topLeftZ = depth + BLANK_SPACE * (r + 1) + TILE_SIZE * r;

                float[] vertices = { topLeftX, depth, topLeftZ,             // V0
                    topLeftX + TILE_SIZE, depth, topLeftZ,                  // V1
                    topLeftX, depth, topLeftZ + TILE_SIZE,                  // V2
                    topLeftX + TILE_SIZE, depth, topLeftZ + TILE_SIZE       // V3
                };

                tiles[3][(2 - r) * 3 + c] = new Quad(colorScheme.getdCol(),
                    vertices, cube.getMatrixList(), scaleX, scaleY, scaleZ,
                    rotX, rotY, rotZ, transX, transY, transZ);
            }
        }

        // Left face stickers
        depth = -0.501f;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                float botLeftZ = -(depth + BLANK_SPACE * (c + 1) + TILE_SIZE
                    * c);
                float botLeftY = depth + BLANK_SPACE * (r + 1) + TILE_SIZE * r;

                float[] vertices = { depth, botLeftY + TILE_SIZE, botLeftZ, // V0
                    depth, botLeftY + TILE_SIZE, botLeftZ - TILE_SIZE,      // V1
                    depth, botLeftY, botLeftZ,                              // V2
                    depth, botLeftY, botLeftZ - TILE_SIZE                   // V3
                };

                tiles[4][r * 3 + c] = new Quad(colorScheme.getlCol(), vertices,
                    cube.getMatrixList(), scaleX, scaleY, scaleZ, rotX, rotY,
                    rotZ, transX, transY, transZ);
            }
        }

        // Back face stickers
        depth = 0.501f;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                float botLeftX = depth - (BLANK_SPACE * (c + 1) + TILE_SIZE
                    * c);
                float botLeftY = -depth + BLANK_SPACE * (r + 1) + TILE_SIZE * r;

                float[] vertices = { botLeftX, botLeftY + TILE_SIZE, depth, // V0
                    botLeftX - TILE_SIZE, botLeftY + TILE_SIZE, depth,      // V1
                    botLeftX, botLeftY, depth,                              // V2
                    botLeftX - TILE_SIZE, botLeftY, depth                   // V3
                };

                tiles[5][r * 3 + c] = new Quad(colorScheme.getbCol(), vertices,
                    cube.getMatrixList(), scaleX, scaleY, scaleZ, rotX, rotY,
                    rotZ, transX, transY, transZ);
            }
        }

        updateTilesAndCube(); // Sets the cube's initial plastic color and
                              // sticker colors using the cube's initial color
                              // scheme
    }


    /**
     * Synchronizes the cube's plastic color and every sticker's model color
     * with the current color scheme and cube buffer data
     */
    public void updateTilesAndCube() {
        // Updating the plastic color
        cube.getModel().setColor(colorScheme.getPlasticColor());

        // Updating every sticker's model color
        for (int s = 0; s < tiles.length; s++) {
            for (int c = 0; c < tiles[0].length; c++) {
                switch (cubeBuffer.getTileColor(s, c)) {
                    case (Constants.COL_B):
                        tiles[s][c].setColor(colorScheme.gettCol());
                        break;
                    case (Constants.COL_G):
                        tiles[s][c].setColor(colorScheme.getdCol());
                        break;
                    case (Constants.COL_O):
                        tiles[s][c].setColor(colorScheme.getlCol());
                        break;
                    case (Constants.COL_R):
                        tiles[s][c].setColor(colorScheme.getrCol());
                        break;
                    case (Constants.COL_W):
                        tiles[s][c].setColor(colorScheme.getfCol());
                        break;
                    case (Constants.COL_Y):
                        tiles[s][c].setColor(colorScheme.getbCol());
                        break;
                    default:
                        System.err.println(
                            "Incorrect tile color value in cube buffer");
                        System.exit(-1);
                }
            }
        }
    }


    /**
     * Sets the color scheme of the cube
     * 
     * @param colorScheme
     *            Contains information about the cube's plastic and sticker
     *            colors
     */
    public void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
        updateTilesAndCube(); // Updates the sticker and plastic colors based on
                              // the new color scheme colors
    }


    /**
     * Gets a reference to the cube buffer
     * 
     * @return A reference to the cube buffer object
     */
    public CubeBuffer getCubeBuffer() {
        return cubeBuffer;
    }

}
