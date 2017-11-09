package colors;

import java.awt.Color;

/**
 * This class represents a white color scheme. This color scheme is often seen
 * in puzzles with white-colored plastic, which are sometimes offered as
 * alternatives to their black plastic counterparts.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class WhiteColors extends ColorScheme {

    /**
     * White plastic color
     */
    private static final Color plasticColor = Color.WHITE;

    /**
     * Black front face color
     */
    private static final Color fCol = Color.BLACK;
    /**
     * Blue top face color
     */
    private static final Color tCol = Color.BLUE;
    /**
     * Red right face color
     */
    private static final Color rCol = Color.RED;
    /**
     * Dark green down face color
     */
    private static final Color dCol = new Color(0, 128, 0);
    /**
     * Orange left face color
     */
    private static final Color lCol = new Color(255, 140, 0);
    /**
     * Dark yellow back face color
     */
    private static final Color bCol = new Color(210, 210, 0);


    /**
     * Constructing a ColorScheme object using the white scheme colors
     */
    public WhiteColors() {
        super(plasticColor, fCol, tCol, rCol, dCol, lCol, bCol);
    }

}
