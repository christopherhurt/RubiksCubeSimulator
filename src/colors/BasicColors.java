package colors;

import java.awt.Color;

/**
 * This class represents a Rubik's cube's basic color scheme. The colors seen
 * using this color scheme as the same as ones used on a standard Rubik's cube.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class BasicColors extends ColorScheme {

    /**
     * Black plastic color
     */
    private static final Color plasticColor = Color.BLACK;

    /**
     * White front face color
     */
    private static final Color fCol = Color.WHITE;
    /**
     * Blue top face color
     */
    private static final Color tCol = Color.BLUE;
    /**
     * Red right face color
     */
    private static final Color rCol = Color.RED;
    /**
     * Green down face color
     */
    private static final Color dCol = Color.GREEN;
    /**
     * Orange left face color, Color.ORANGE is not used here because its shade
     * is too similar to Color.YELLOW
     */
    private static final Color lCol = new Color(255, 140, 0);
    /**
     * Yellow back face color
     */
    private static final Color bCol = Color.YELLOW;


    /**
     * Constructing a ColorScheme object using the basic scheme colors
     */
    public BasicColors() {
        super(plasticColor, fCol, tCol, rCol, dCol, lCol, bCol);
    }

}
