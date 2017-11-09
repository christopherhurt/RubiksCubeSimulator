package colors;

import java.awt.Color;

/**
 * This class serves as a wrapper that defines all colors seen on the cube,
 * including the color of the plastic and the sticker color on each face.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public abstract class ColorScheme {

    /**
     * Color of the cube's plastic
     */
    private Color plasticColor;
    /**
     * Color of each of the face's stickers
     */
    private Color fCol, tCol, rCol, dCol, lCol, bCol;


    /**
     * Creates a new ColorScheme object defined by the color of each face and
     * the plastic color.
     * 
     * @param plasticColor
     *            Plastic color
     * @param fCol
     *            Front face color
     * @param tCol
     *            Top face color
     * @param rCol
     *            Right face color
     * @param dCol
     *            Down face color
     * @param lCol
     *            Left face color
     * @param bCol
     *            Back face color
     */
    public ColorScheme(
        Color plasticColor,
        Color fCol,
        Color tCol,
        Color rCol,
        Color dCol,
        Color lCol,
        Color bCol) {
        this.plasticColor = plasticColor;
        this.fCol = fCol;
        this.tCol = tCol;
        this.rCol = rCol;
        this.dCol = dCol;
        this.lCol = lCol;
        this.bCol = bCol;
    }


    /**
     * Gets the cube's plastic color
     * 
     * @return The cube's plastic color
     */
    public Color getPlasticColor() {
        return plasticColor;
    }


    /**
     * Gets the cube's front face color
     * 
     * @return The cube's front face color
     */
    public Color getfCol() {
        return fCol;
    }


    /**
     * Gets the cube's top face color
     * 
     * @return The cube's top face color
     */
    public Color gettCol() {
        return tCol;
    }


    /**
     * Gets the cube's right face color
     * 
     * @return The cube's right face color
     */
    public Color getrCol() {
        return rCol;
    }


    /**
     * Gets the cube's down face color
     * 
     * @return The cube's down face color
     */
    public Color getdCol() {
        return dCol;
    }


    /**
     * Gets the cube's left face color
     * 
     * @return The cube's left face color
     */
    public Color getlCol() {
        return lCol;
    }


    /**
     * Gets the cube's back face color
     * 
     * @return The cube's back face color
     */
    public Color getbCol() {
        return bCol;
    }

}
