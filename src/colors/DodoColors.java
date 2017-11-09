package colors;

import java.awt.Color;

/**
 * This class represents a Dodo cube color scheme. The Dodo cube is a gag in
 * which all sides of the cube are same color, making the cube always solved. Or
 * perhaps....never solved?
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class DodoColors extends ColorScheme {

    /**
     * Black plastic color
     */
    private static final Color plasticColor = Color.BLACK;

    /**
     * Mustard yellow Dodo cube color
     */
    private static final Color fCol = new Color(255, 215, 0);
    /**
     * Same as front face color
     */
    private static final Color tCol = fCol;
    /**
     * Same as front face color
     */
    private static final Color rCol = fCol;
    /**
     * Same as front face color
     */
    private static final Color dCol = fCol;
    /**
     * Same as front face color
     */
    private static final Color lCol = fCol;
    /**
     * Same as front face color
     */
    private static final Color bCol = fCol;


    /**
     * Constructing a ColorScheme object using the dodo scheme colors
     */
    public DodoColors() {
        super(plasticColor, fCol, tCol, rCol, dCol, lCol, bCol);
    }

}
