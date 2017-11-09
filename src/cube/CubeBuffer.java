package cube;

import java.util.Arrays;

/**
 * Represents a buffer containing information about the color of every sticker
 * on every face of the cube
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class CubeBuffer {

    /**
     * A two-dimensional integer array, where the outer dimension value maps to
     * a particular face on the cube, the inner dimension value maps to a
     * particular sticker, and the value at a given face and sticker maps to the
     * color of that sticker; see README for the specific values used for
     * mapping faces, stickers, and colors
     */
    private int[][] colorMap;


    /**
     * Creates a new CubeBuffer object where colormap is initialized to the
     * solved state of the cube
     */
    public CubeBuffer() {
        colorMap = new int[6][9];
        genSolved();
    }


    /**
     * Creates a new CubeBuffer object and copies the colormap data of another
     * CubeBuffer object
     * 
     * @param copy
     *            The CubeBuffer object whose data is being copied
     */
    public CubeBuffer(CubeBuffer copy) {
        colorMap = new int[6][0];

        for (int s = 0; s < 6; s++) {
            colorMap[s] = Arrays.copyOf(copy.getSide(s), 9);
        }
    }


    /**
     * Used to initialize/reset colormap to the cube's solved state
     */
    public void genSolved() {
        for (int s = 0; s < 6; s++) {
            for (int c = 0; c < 9; c++) {
                colorMap[s][c] = s; // The color value of every sticker on each
                                    // face is set to the index of the face
            }
        }
    }


    /**
     * Checks to see if the cube is in its solved state
     * 
     * @return True if the cube is solved, false otherwise
     */
    public boolean isSolved() {
        for (int s = 0; s < 6; s++) {
            int centerTile = colorMap[s][4]; // The color value of the current
                                             // face's center tile
            for (int c = 0; c < 9; c++) {
                int thisTile = colorMap[s][c];
                if (thisTile != centerTile) { // Checks to see if every tile on
                                              // the given face has the same
                                              // color as its center tile,
                                              // returns false if one doesn't
                                              // match
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Performs a clockwise right-face turn on the color map
     */
    public void right() {
        // Rotates the stickers on the front, up, back, and down faces that are
        // adjacent to the right face
        int FtempTR = colorMap[0][8];
        int FtempMR = colorMap[0][5];
        int FtempBR = colorMap[0][2];

        colorMap[0][8] = colorMap[3][8];
        colorMap[0][5] = colorMap[3][5];
        colorMap[0][2] = colorMap[3][2];

        colorMap[3][8] = colorMap[5][0];
        colorMap[3][5] = colorMap[5][3];
        colorMap[3][2] = colorMap[5][6];

        colorMap[5][0] = colorMap[1][8];
        colorMap[5][3] = colorMap[1][5];
        colorMap[5][6] = colorMap[1][2];

        colorMap[1][8] = FtempTR;
        colorMap[1][5] = FtempMR;
        colorMap[1][2] = FtempBR;

        // Rotating corner stickers on right face
        int RtempTL = colorMap[2][6];
        colorMap[2][6] = colorMap[2][0];
        colorMap[2][0] = colorMap[2][2];
        colorMap[2][2] = colorMap[2][8];
        colorMap[2][8] = RtempTL;

        // Rotating edge stickers on right face
        int RtempTM = colorMap[2][7];
        colorMap[2][7] = colorMap[2][3];
        colorMap[2][3] = colorMap[2][1];
        colorMap[2][1] = colorMap[2][5];
        colorMap[2][5] = RtempTM;
    }


    /**
     * Performs a counterclockwise right-face turn on the color map
     */
    public void rightPrime() {
        // Rotates the stickers on the front, up, back, and down faces that are
        // adjacent to the right face
        int FtempTR = colorMap[0][8];
        int FtempMR = colorMap[0][5];
        int FtempBR = colorMap[0][2];

        colorMap[0][8] = colorMap[1][8];
        colorMap[0][5] = colorMap[1][5];
        colorMap[0][2] = colorMap[1][2];

        colorMap[1][8] = colorMap[5][0];
        colorMap[1][5] = colorMap[5][3];
        colorMap[1][2] = colorMap[5][6];

        colorMap[5][0] = colorMap[3][8];
        colorMap[5][3] = colorMap[3][5];
        colorMap[5][6] = colorMap[3][2];

        colorMap[3][8] = FtempTR;
        colorMap[3][5] = FtempMR;
        colorMap[3][2] = FtempBR;

        // Rotating corner stickers on right face
        int RtempTL = colorMap[2][6];
        colorMap[2][6] = colorMap[2][8];
        colorMap[2][8] = colorMap[2][2];
        colorMap[2][2] = colorMap[2][0];
        colorMap[2][0] = RtempTL;

        // Rotating edge stickers on right face
        int RtempTM = colorMap[2][7];
        colorMap[2][7] = colorMap[2][5];
        colorMap[2][5] = colorMap[2][1];
        colorMap[2][1] = colorMap[2][3];
        colorMap[2][3] = RtempTM;
    }


    /**
     * Performs a double right face turn on the color map
     * This method could be simplified by calling right() or rightPrime() twice,
     * but a direct swap was implemented for efficiency's sake
     */
    public void rightTwo() {
        // Swapping the stickers on the front and back faces that are adjacent
        // to the right face
        int FtempTR = colorMap[0][8];
        int FtempMR = colorMap[0][5];
        int FtempBR = colorMap[0][2];

        colorMap[0][8] = colorMap[5][0];
        colorMap[0][5] = colorMap[5][3];
        colorMap[0][2] = colorMap[5][6];

        colorMap[5][0] = FtempTR;
        colorMap[5][3] = FtempMR;
        colorMap[5][6] = FtempBR;

        // Swapping the stickers on the up and down faces that are adjacent to
        // the right face
        int TtempTR = colorMap[1][8];
        int TtempMR = colorMap[1][5];
        int TtempBR = colorMap[1][2];

        colorMap[1][8] = colorMap[3][8];
        colorMap[1][5] = colorMap[3][5];
        colorMap[1][2] = colorMap[3][2];

        colorMap[3][8] = TtempTR;
        colorMap[3][5] = TtempMR;
        colorMap[3][2] = TtempBR;

        // Swapping the corner stickers on the right face
        int RtempTL = colorMap[2][6];
        colorMap[2][6] = colorMap[2][2];
        colorMap[2][2] = RtempTL;
        int RtempTR = colorMap[2][8];
        colorMap[2][8] = colorMap[2][0];
        colorMap[2][0] = RtempTR;

        // Swapping the edge stickers on the right face
        int RtempTM = colorMap[2][7];
        colorMap[2][7] = colorMap[2][1];
        colorMap[2][1] = RtempTM;
        int RtempMR = colorMap[2][5];
        colorMap[2][5] = colorMap[2][3];
        colorMap[2][3] = RtempMR;
    }


    /**
     * Performs a clockwise left face turn on the color map
     */
    public void left() {
        // Rotating the front, up, back, and down face stickers that are
        // adjacent to the left face
        int FtempTL = colorMap[0][6];
        int FtempML = colorMap[0][3];
        int FtempBL = colorMap[0][0];

        colorMap[0][6] = colorMap[1][6];
        colorMap[0][3] = colorMap[1][3];
        colorMap[0][0] = colorMap[1][0];

        colorMap[1][6] = colorMap[5][2];
        colorMap[1][3] = colorMap[5][5];
        colorMap[1][0] = colorMap[5][8];

        colorMap[5][2] = colorMap[3][6];
        colorMap[5][5] = colorMap[3][3];
        colorMap[5][8] = colorMap[3][0];

        colorMap[3][6] = FtempTL;
        colorMap[3][3] = FtempML;
        colorMap[3][0] = FtempBL;

        // Rotating the corner stickers on the left face
        int LtempTL = colorMap[4][6];
        colorMap[4][6] = colorMap[4][0];
        colorMap[4][0] = colorMap[4][2];
        colorMap[4][2] = colorMap[4][8];
        colorMap[4][8] = LtempTL;

        // Rotating the edge stickers on the left face
        int LtempTM = colorMap[4][7];
        colorMap[4][7] = colorMap[4][3];
        colorMap[4][3] = colorMap[4][1];
        colorMap[4][1] = colorMap[4][5];
        colorMap[4][5] = LtempTM;
    }


    /**
     * Performs a counterclockwise left face turn on the color map
     */
    public void leftPrime() {
        // Rotates the front, up, back, and down face stickers that are adjacent
        // to the left face
        int FtempTL = colorMap[0][6];
        int FtempML = colorMap[0][3];
        int FtempBL = colorMap[0][0];

        colorMap[0][6] = colorMap[3][6];
        colorMap[0][3] = colorMap[3][3];
        colorMap[0][0] = colorMap[3][0];

        colorMap[3][6] = colorMap[5][2];
        colorMap[3][3] = colorMap[5][5];
        colorMap[3][0] = colorMap[5][8];

        colorMap[5][2] = colorMap[1][6];
        colorMap[5][5] = colorMap[1][3];
        colorMap[5][8] = colorMap[1][0];

        colorMap[1][6] = FtempTL;
        colorMap[1][3] = FtempML;
        colorMap[1][0] = FtempBL;

        // Rotates the corner stickers on the left face
        int LtempTL = colorMap[4][6];
        colorMap[4][6] = colorMap[4][8];
        colorMap[4][8] = colorMap[4][2];
        colorMap[4][2] = colorMap[4][0];
        colorMap[4][0] = LtempTL;

        // Rotates the edge stickers on the left face
        int LtempTM = colorMap[4][7];
        colorMap[4][7] = colorMap[4][5];
        colorMap[4][5] = colorMap[4][1];
        colorMap[4][1] = colorMap[4][3];
        colorMap[4][3] = LtempTM;
    }


    /**
     * Performs a double left face turn on the color map
     * This method could be simplified by calling left() or leftPrime() twice,
     * but a direct swap was implemented for efficiency's sake
     */
    public void leftTwo() {
        // Swaps the front and back face stickers adjacent to the left face
        int FtempTL = colorMap[0][6];
        int FtempML = colorMap[0][3];
        int FtempBL = colorMap[0][0];

        colorMap[0][6] = colorMap[5][2];
        colorMap[0][3] = colorMap[5][5];
        colorMap[0][0] = colorMap[5][8];

        colorMap[5][2] = FtempTL;
        colorMap[5][5] = FtempML;
        colorMap[5][8] = FtempBL;

        // Swaps the up and down face stickers adjacent to the left face
        int TtempTL = colorMap[1][6];
        int TtempML = colorMap[1][3];
        int TtempBL = colorMap[1][0];

        colorMap[1][6] = colorMap[3][6];
        colorMap[1][3] = colorMap[3][3];
        colorMap[1][0] = colorMap[3][0];

        colorMap[3][6] = TtempTL;
        colorMap[3][3] = TtempML;
        colorMap[3][0] = TtempBL;

        // Swaps the corner stickers on the left face
        int LtempTL = colorMap[4][6];
        colorMap[4][6] = colorMap[4][2];
        colorMap[4][2] = LtempTL;
        int LtempTR = colorMap[4][8];
        colorMap[4][8] = colorMap[4][0];
        colorMap[4][0] = LtempTR;

        // Swaps the edge stickers on the left face
        int LtempTM = colorMap[4][7];
        colorMap[4][7] = colorMap[4][1];
        colorMap[4][1] = LtempTM;
        int LtempMR = colorMap[4][5];
        colorMap[4][5] = colorMap[4][3];
        colorMap[4][3] = LtempMR;
    }


    /**
     * Performs a clockwise up face turn on the color map
     */
    public void up() {
        // Rotates the front, left, back, and right face stickers adjacent to
        // the up face
        int FtempTL = colorMap[0][6];
        int FtempTM = colorMap[0][7];
        int FtempTR = colorMap[0][8];

        colorMap[0][6] = colorMap[2][6];
        colorMap[0][7] = colorMap[2][7];
        colorMap[0][8] = colorMap[2][8];

        colorMap[2][6] = colorMap[5][6];
        colorMap[2][7] = colorMap[5][7];
        colorMap[2][8] = colorMap[5][8];

        colorMap[5][6] = colorMap[4][6];
        colorMap[5][7] = colorMap[4][7];
        colorMap[5][8] = colorMap[4][8];

        colorMap[4][6] = FtempTL;
        colorMap[4][7] = FtempTM;
        colorMap[4][8] = FtempTR;

        // Rotates the corner stickers on the up face
        int TtempTL = colorMap[1][6];
        colorMap[1][6] = colorMap[1][0];
        colorMap[1][0] = colorMap[1][2];
        colorMap[1][2] = colorMap[1][8];
        colorMap[1][8] = TtempTL;

        // Rotates the edge stickers on the up face
        int TtempTM = colorMap[1][7];
        colorMap[1][7] = colorMap[1][3];
        colorMap[1][3] = colorMap[1][1];
        colorMap[1][1] = colorMap[1][5];
        colorMap[1][5] = TtempTM;
    }


    /**
     * Performs a counterclockwise up face turn on the color map
     */
    public void upPrime() {
        // Rotates the front, left, back, and right face stickers adjacent to
        // the up face
        int FtempTL = colorMap[0][6];
        int FtempTM = colorMap[0][7];
        int FtempTR = colorMap[0][8];

        colorMap[0][6] = colorMap[4][6];
        colorMap[0][7] = colorMap[4][7];
        colorMap[0][8] = colorMap[4][8];

        colorMap[4][6] = colorMap[5][6];
        colorMap[4][7] = colorMap[5][7];
        colorMap[4][8] = colorMap[5][8];

        colorMap[5][6] = colorMap[2][6];
        colorMap[5][7] = colorMap[2][7];
        colorMap[5][8] = colorMap[2][8];

        colorMap[2][6] = FtempTL;
        colorMap[2][7] = FtempTM;
        colorMap[2][8] = FtempTR;

        // Rotates the corner stickers on the up face
        int TtempTL = colorMap[1][6];
        colorMap[1][6] = colorMap[1][8];
        colorMap[1][8] = colorMap[1][2];
        colorMap[1][2] = colorMap[1][0];
        colorMap[1][0] = TtempTL;

        // Rotates the edge stickers on the up face
        int TtempTM = colorMap[1][7];
        colorMap[1][7] = colorMap[1][5];
        colorMap[1][5] = colorMap[1][1];
        colorMap[1][1] = colorMap[1][3];
        colorMap[1][3] = TtempTM;
    }


    /**
     * Performs a double up face turn on the color map
     * This method could be simplified by calling up() or upPrime() twice, but a
     * direct swap was implemented for efficiency's sake
     */
    public void upTwo() {
        // Swapping the front and back stickers adjacent to the up face
        int FtempTL = colorMap[0][6];
        int FtempTM = colorMap[0][7];
        int FtempTR = colorMap[0][8];

        colorMap[0][6] = colorMap[5][6];
        colorMap[0][7] = colorMap[5][7];
        colorMap[0][8] = colorMap[5][8];

        colorMap[5][6] = FtempTL;
        colorMap[5][7] = FtempTM;
        colorMap[5][8] = FtempTR;

        // Swapping the left and right stickers adjacent to the up face
        int RtempTL = colorMap[2][6];
        int RtempTM = colorMap[2][7];
        int RtempTR = colorMap[2][8];

        colorMap[2][6] = colorMap[4][6];
        colorMap[2][7] = colorMap[4][7];
        colorMap[2][8] = colorMap[4][8];

        colorMap[4][6] = RtempTL;
        colorMap[4][7] = RtempTM;
        colorMap[4][8] = RtempTR;

        // Swapping the corner stickers on the up face
        int TtempTL = colorMap[1][6];
        colorMap[1][6] = colorMap[1][2];
        colorMap[1][2] = TtempTL;
        int TtempTR = colorMap[1][8];
        colorMap[1][8] = colorMap[1][0];
        colorMap[1][0] = TtempTR;

        // Swapping the edge stickers on the up face
        int TtempTM = colorMap[1][7];
        colorMap[1][7] = colorMap[1][1];
        colorMap[1][1] = TtempTM;
        int TtempMR = colorMap[1][5];
        colorMap[1][5] = colorMap[1][3];
        colorMap[1][3] = TtempMR;
    }


    /**
     * Performs a clockwise down face turn on the color map
     */
    public void down() {
        // Rotates the front, left, back, and right face stickers adjacent to
        // the down face
        int FtempBL = colorMap[0][0];
        int FtempBM = colorMap[0][1];
        int FtempBR = colorMap[0][2];

        colorMap[0][0] = colorMap[4][0];
        colorMap[0][1] = colorMap[4][1];
        colorMap[0][2] = colorMap[4][2];

        colorMap[4][0] = colorMap[5][0];
        colorMap[4][1] = colorMap[5][1];
        colorMap[4][2] = colorMap[5][2];

        colorMap[5][0] = colorMap[2][0];
        colorMap[5][1] = colorMap[2][1];
        colorMap[5][2] = colorMap[2][2];

        colorMap[2][0] = FtempBL;
        colorMap[2][1] = FtempBM;
        colorMap[2][2] = FtempBR;

        // Rotates the corner stickers on the down face
        int DtempTL = colorMap[3][6];
        colorMap[3][6] = colorMap[3][0];
        colorMap[3][0] = colorMap[3][2];
        colorMap[3][2] = colorMap[3][8];
        colorMap[3][8] = DtempTL;

        // Rotates the edge stickers on the down face
        int DtempTM = colorMap[3][7];
        colorMap[3][7] = colorMap[3][3];
        colorMap[3][3] = colorMap[3][1];
        colorMap[3][1] = colorMap[3][5];
        colorMap[3][5] = DtempTM;
    }


    /**
     * Performs a counterclockwise down face turn on the color map
     */
    public void downPrime() {
        // Rotates the front, left, back, and right face stickers adjacent to
        // the down face
        int FtempBL = colorMap[0][0];
        int FtempBM = colorMap[0][1];
        int FtempBR = colorMap[0][2];

        colorMap[0][0] = colorMap[2][0];
        colorMap[0][1] = colorMap[2][1];
        colorMap[0][2] = colorMap[2][2];

        colorMap[2][0] = colorMap[5][0];
        colorMap[2][1] = colorMap[5][1];
        colorMap[2][2] = colorMap[5][2];

        colorMap[5][0] = colorMap[4][0];
        colorMap[5][1] = colorMap[4][1];
        colorMap[5][2] = colorMap[4][2];

        colorMap[4][0] = FtempBL;
        colorMap[4][1] = FtempBM;
        colorMap[4][2] = FtempBR;

        // Rotates the corner stickers on the down face
        int DtempTL = colorMap[3][6];
        colorMap[3][6] = colorMap[3][8];
        colorMap[3][8] = colorMap[3][2];
        colorMap[3][2] = colorMap[3][0];
        colorMap[3][0] = DtempTL;

        // Rotates the edge stickers on the down face
        int DtempTM = colorMap[3][7];
        colorMap[3][7] = colorMap[3][5];
        colorMap[3][5] = colorMap[3][1];
        colorMap[3][1] = colorMap[3][3];
        colorMap[3][3] = DtempTM;
    }


    /**
     * Performs a double down face turn on the color map
     * This method could be simplified by calling down() or downPrime() twice,
     * but a direct swap was implemented for efficiency's sake
     */
    public void downTwo() {
        // Swapping the front and back face stickers adjacent to the down face
        int FtempBL = colorMap[0][0];
        int FtempBM = colorMap[0][1];
        int FtempBR = colorMap[0][2];

        colorMap[0][0] = colorMap[5][0];
        colorMap[0][1] = colorMap[5][1];
        colorMap[0][2] = colorMap[5][2];

        colorMap[5][0] = FtempBL;
        colorMap[5][1] = FtempBM;
        colorMap[5][2] = FtempBR;

        // Swapping the left and right face stickers adjacent to the down face
        int RtempBL = colorMap[2][0];
        int RtempBM = colorMap[2][1];
        int RtempBR = colorMap[2][2];

        colorMap[2][0] = colorMap[4][0];
        colorMap[2][1] = colorMap[4][1];
        colorMap[2][2] = colorMap[4][2];

        colorMap[4][0] = RtempBL;
        colorMap[4][1] = RtempBM;
        colorMap[4][2] = RtempBR;

        // Swaps the corner stickers on the down face
        int DtempTL = colorMap[3][6];
        colorMap[3][6] = colorMap[3][2];
        colorMap[3][2] = DtempTL;
        int DtempTR = colorMap[3][8];
        colorMap[3][8] = colorMap[3][0];
        colorMap[3][0] = DtempTR;

        // Swaps the edge stickers on the down face
        int DtempTM = colorMap[3][7];
        colorMap[3][7] = colorMap[3][1];
        colorMap[3][1] = DtempTM;
        int DtempMR = colorMap[3][5];
        colorMap[3][5] = colorMap[3][3];
        colorMap[3][3] = DtempMR;
    }


    /**
     * Performs a clockwise front face turn on the color map
     */
    public void front() {
        // Rotates the up, right, down, and left face stickers adjacent to the
        // front face
        int TtempBL = colorMap[1][0];
        int TtempBM = colorMap[1][1];
        int TtempBR = colorMap[1][2];

        colorMap[1][0] = colorMap[4][2];
        colorMap[1][1] = colorMap[4][5];
        colorMap[1][2] = colorMap[4][8];

        colorMap[4][2] = colorMap[3][8];
        colorMap[4][5] = colorMap[3][7];
        colorMap[4][8] = colorMap[3][6];

        colorMap[3][8] = colorMap[2][6];
        colorMap[3][7] = colorMap[2][3];
        colorMap[3][6] = colorMap[2][0];

        colorMap[2][6] = TtempBL;
        colorMap[2][3] = TtempBM;
        colorMap[2][0] = TtempBR;

        // Rotates the corner stickers on the front face
        int FtempTL = colorMap[0][6];
        colorMap[0][6] = colorMap[0][0];
        colorMap[0][0] = colorMap[0][2];
        colorMap[0][2] = colorMap[0][8];
        colorMap[0][8] = FtempTL;

        // Rotates the edge stickers on the front face
        int FtempTM = colorMap[0][7];
        colorMap[0][7] = colorMap[0][3];
        colorMap[0][3] = colorMap[0][1];
        colorMap[0][1] = colorMap[0][5];
        colorMap[0][5] = FtempTM;
    }


    /**
     * Performs a counterclockwise front face turn on the color map
     */
    public void frontPrime() {
        // Rotates the up, right, down, and left face stickers adjacent to the
        // front face
        int TtempBL = colorMap[1][0];
        int TtempBM = colorMap[1][1];
        int TtempBR = colorMap[1][2];

        colorMap[1][0] = colorMap[2][6];
        colorMap[1][1] = colorMap[2][3];
        colorMap[1][2] = colorMap[2][0];

        colorMap[2][6] = colorMap[3][8];
        colorMap[2][3] = colorMap[3][7];
        colorMap[2][0] = colorMap[3][6];

        colorMap[3][8] = colorMap[4][2];
        colorMap[3][7] = colorMap[4][5];
        colorMap[3][6] = colorMap[4][8];

        colorMap[4][2] = TtempBL;
        colorMap[4][5] = TtempBM;
        colorMap[4][8] = TtempBR;

        // Rotates the corner stickers on the front face
        int FtempTL = colorMap[0][6];
        colorMap[0][6] = colorMap[0][8];
        colorMap[0][8] = colorMap[0][2];
        colorMap[0][2] = colorMap[0][0];
        colorMap[0][0] = FtempTL;

        // Rotates the edge stickers on the front face
        int FtempTM = colorMap[0][7];
        colorMap[0][7] = colorMap[0][5];
        colorMap[0][5] = colorMap[0][1];
        colorMap[0][1] = colorMap[0][3];
        colorMap[0][3] = FtempTM;
    }


    /**
     * Performs a double front face turn on the color map
     * This method could be simplified by calling front() or frontPrime() twice,
     * but a direct swap was implemented for efficiency's sake
     */
    public void frontTwo() {
        // Swaps the up and down face stickers adjacent to the front face
        int TtempBL = colorMap[1][0];
        int TtempBM = colorMap[1][1];
        int TtempBR = colorMap[1][2];

        colorMap[1][0] = colorMap[3][8];
        colorMap[1][1] = colorMap[3][7];
        colorMap[1][2] = colorMap[3][6];

        colorMap[3][8] = TtempBL;
        colorMap[3][7] = TtempBM;
        colorMap[3][6] = TtempBR;

        // Swaps the left and right face stickers adjacent to the front face
        int RtempTL = colorMap[2][6];
        int RtempLM = colorMap[2][3];
        int RtempBL = colorMap[2][0];

        colorMap[2][6] = colorMap[4][2];
        colorMap[2][3] = colorMap[4][5];
        colorMap[2][0] = colorMap[4][8];

        colorMap[4][2] = RtempTL;
        colorMap[4][5] = RtempLM;
        colorMap[4][8] = RtempBL;

        // Swaps the corner stickers on the front face
        int FtempTL = colorMap[0][6];
        colorMap[0][6] = colorMap[0][2];
        colorMap[0][2] = FtempTL;
        int FtempTR = colorMap[0][8];
        colorMap[0][8] = colorMap[0][0];
        colorMap[0][0] = FtempTR;

        // Swaps the edge stickers on the front face
        int FtempTM = colorMap[0][7];
        colorMap[0][7] = colorMap[0][1];
        colorMap[0][1] = FtempTM;
        int FtempMR = colorMap[0][5];
        colorMap[0][5] = colorMap[0][3];
        colorMap[0][3] = FtempMR;
    }


    /**
     * Performs a clockwise back face turn on the color map
     */
    public void back() {
        // Rotates the up, right, down, and left face stickers adjacent to the
        // back face
        int TtempTL = colorMap[1][6];
        int TtempTM = colorMap[1][7];
        int TtempTR = colorMap[1][8];

        colorMap[1][6] = colorMap[2][8];
        colorMap[1][7] = colorMap[2][5];
        colorMap[1][8] = colorMap[2][2];

        colorMap[2][8] = colorMap[3][2];
        colorMap[2][5] = colorMap[3][1];
        colorMap[2][2] = colorMap[3][0];

        colorMap[3][2] = colorMap[4][0];
        colorMap[3][1] = colorMap[4][3];
        colorMap[3][0] = colorMap[4][6];

        colorMap[4][0] = TtempTL;
        colorMap[4][3] = TtempTM;
        colorMap[4][6] = TtempTR;

        // Rotates the corner stickers on the back face
        int BtempTL = colorMap[5][6];
        colorMap[5][6] = colorMap[5][0];
        colorMap[5][0] = colorMap[5][2];
        colorMap[5][2] = colorMap[5][8];
        colorMap[5][8] = BtempTL;

        // Rotates the edge stickers on the back face
        int BtempTM = colorMap[5][7];
        colorMap[5][7] = colorMap[5][3];
        colorMap[5][3] = colorMap[5][1];
        colorMap[5][1] = colorMap[5][5];
        colorMap[5][5] = BtempTM;
    }


    /**
     * Performs a counterclockwise back face turn on the color map
     */
    public void backPrime() {
        // Rotates the up, right, down, and left face stickers adjacent to the
        // back face
        int TtempTL = colorMap[1][6];
        int TtempTM = colorMap[1][7];
        int TtempTR = colorMap[1][8];

        colorMap[1][6] = colorMap[4][0];
        colorMap[1][7] = colorMap[4][3];
        colorMap[1][8] = colorMap[4][6];

        colorMap[4][0] = colorMap[3][2];
        colorMap[4][3] = colorMap[3][1];
        colorMap[4][6] = colorMap[3][0];

        colorMap[3][2] = colorMap[2][8];
        colorMap[3][1] = colorMap[2][5];
        colorMap[3][0] = colorMap[2][2];

        colorMap[2][8] = TtempTL;
        colorMap[2][5] = TtempTM;
        colorMap[2][2] = TtempTR;

        // Rotates the corner stickers on the back face
        int BtempTL = colorMap[5][6];
        colorMap[5][6] = colorMap[5][8];
        colorMap[5][8] = colorMap[5][2];
        colorMap[5][2] = colorMap[5][0];
        colorMap[5][0] = BtempTL;

        // Rotates the edge stickers on the back face
        int BtempTM = colorMap[5][7];
        colorMap[5][7] = colorMap[5][5];
        colorMap[5][5] = colorMap[5][1];
        colorMap[5][1] = colorMap[5][3];
        colorMap[5][3] = BtempTM;
    }


    /**
     * Performs a double back face turn on the color map
     * This method could be simplified by calling back() or backPrime() twice,
     * but a direct swap was implemented for efficiency's sake
     */
    public void backTwo() {
        // Swaps the up and down face stickers adjacent to the back face
        int TtempTL = colorMap[1][6];
        int TtempTM = colorMap[1][7];
        int TtempTR = colorMap[1][8];

        colorMap[1][6] = colorMap[3][2];
        colorMap[1][7] = colorMap[3][1];
        colorMap[1][8] = colorMap[3][0];

        colorMap[3][2] = TtempTL;
        colorMap[3][1] = TtempTM;
        colorMap[3][0] = TtempTR;

        // Swaps the left and right face stickers adjacent to the back face
        int RtempTR = colorMap[2][8];
        int RtempMR = colorMap[2][5];
        int RtempBR = colorMap[2][2];

        colorMap[2][8] = colorMap[4][0];
        colorMap[2][5] = colorMap[4][3];
        colorMap[2][2] = colorMap[4][6];

        colorMap[4][0] = RtempTR;
        colorMap[4][3] = RtempMR;
        colorMap[4][6] = RtempBR;

        // Swaps the corner stickers on the back face
        int BtempTL = colorMap[5][6];
        colorMap[5][6] = colorMap[5][2];
        colorMap[5][2] = BtempTL;
        int BtempTR = colorMap[5][8];
        colorMap[5][8] = colorMap[5][0];
        colorMap[5][0] = BtempTR;

        // Swaps the edge stickers on the back face
        int BtempTM = colorMap[5][7];
        colorMap[5][7] = colorMap[5][1];
        colorMap[5][1] = BtempTM;
        int BtempMR = colorMap[5][5];
        colorMap[5][5] = colorMap[5][3];
        colorMap[5][3] = BtempMR;
    }


    /**
     * Gets the buffer's color map
     * 
     * @return Buffer's color map
     */
    public int[][] getColorMap() {
        return colorMap;
    }


    /**
     * Gets the color map information about one side
     * 
     * @param s
     *            The integer value that maps to the side being retrieved
     * @return The array of sticker colors on the given side
     */
    public int[] getSide(int s) {
        return colorMap[s];
    }


    /**
     * Gets the color information about one sticker on a particular face
     * 
     * @param s
     *            The face of the sticker being retrieved
     * @param c
     *            The position of the sticker whose color is being retrieved
     * @return Integer value mapped to the color of the specified sticker
     */
    public int getTileColor(int s, int c) {
        return colorMap[s][c];
    }

}
