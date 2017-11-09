package input;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import colors.BasicColors;
import colors.DodoColors;
import colors.WhiteColors;
import cube.CubeBuffer;
import cube.RubiksCube;

/**
 * Manages console commands inputted by the user. Console input is run in a
 * separate thread as not to interfere with the rendering process.
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class Console {

    /**
     * The help menu displayed when entering the "help" command
     */
    private static final String HELP_DISPLAY =
        "-----------------------------------\n"
            + "SCRAMBLE - Randomly scrambles the cube\n"
            + "SOLVE - Solves the cube\n"
            + "RESET - Resets the cube to solved state\n"
            + "COLOR scheme - Changes the cube's color scheme\n"
            + "PATTERN - Display a cool pattern on the cube\n"
            + "CLEAR - Clear all text from the screen\n"
            + "QUIT - Quit the program\n" + "HELP - Displays this help menu\n"
            + "-----------------------------------\n"
            + "R - Right face clockwise turn\n"
            + "R' - Right face counterclockwise turn\n"
            + "R2 - Two right face turns\n" + "L - Left face clockwise turn\n"
            + "L' - Left face counterclockwise turn\n"
            + "L2 - Two left face turns\n" + "U - Upper face clockwise turn\n"
            + "U' - Upper face counterclockwise turn\n"
            + "U2 - Two upper face turns\n" + "D - Down face clockwise turn\n"
            + "D' - Down face counterclockwise turn\n"
            + "D2 - Two down face turns\n" + "F - Front face clockwise turn\n"
            + "F' - Front face counterclockwise turn\n"
            + "F2 - Two front face turns\n" + "B - Back face clockwise turn\n"
            + "B' - Back face counterclockwise turn\n"
            + "B2 - Two back face turns\n"
            + "-----------------------------------";
    /**
     * All of the allowed turn commands, which are case sensitive
     */
    private static final String[] ALLOWED_BASIC_TURNS = { "R", "R'", "R2", "L",
        "L'", "L2", "U", "U'", "U2", "D", "D'", "D2", "F", "F'", "F2", "B",
        "B'", "B2" };
    /**
     * Input turns needed to create a checker pattern on the cube
     */
    private static final String checkerPatternAlgorithm = "R2 L2 U2 D2 F2 B2";

    /**
     * A reference to the RubiksCube object represented in the program
     */
    private RubiksCube cube;
    /**
     * A reference to the cub'e CubeBuffer object, declared as a field so it
     * doesn't need to be referenced from the RubiksCube object every time it is
     * used
     */
    private CubeBuffer cubeBuffer;
    /**
     * Scanner used to retrieve the user's command inputs
     */
    private Scanner sc;


    /**
     * Initializes a new Console object
     * 
     * @param cube
     *            A pre-existing RubiksCube object used when creating the
     *            Console object
     */
    public Console(RubiksCube cube) {
        this.cube = cube;
        this.cubeBuffer = cube.getCubeBuffer();
        this.sc = new Scanner(System.in);
    }


    /**
     * Reads and parses each of the user's input commands
     */
    public void doCommand() {
        // Reading a command from the command line
        String command = sc.nextLine();
        String lowerCaseCommand = command.toLowerCase();

        if (lowerCaseCommand.equals("reset")) {             // RESET
            cubeBuffer.genSolved();
            System.out.println("Cube reset.");
        }
        else if (lowerCaseCommand.equals("solve")) {        // SOLVE
            System.out.println("Feature not yet implemented.");
        }
        else if (lowerCaseCommand.equals("clear")) {        // CLEAR
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start()
                    .waitFor();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if (lowerCaseCommand.equals("help")) {         // HELP
            System.out.println(HELP_DISPLAY);
        }
        else if (lowerCaseCommand.equals("quit")) {         // QUIT
            System.out.println("Program terminated.");
            System.out.println("-----------------------------------");
            System.exit(0);
        }
        else if (lowerCaseCommand.equals("scramble")) {     // SCRAMBLE
            genScramble();
        }
        else if (lowerCaseCommand.equals("pattern")) {      // PATTERN
            genCheckerPattern();
        }
        else if (lowerCaseCommand.startsWith("color ") ||   // COLOR
            lowerCaseCommand.equals("color")) {
            processColor(command);
        }
        else {                                              // Turn or Error
            parseCommands(command);
        }

        System.out.println("-----------------------------------");
        cube.updateTilesAndCube();
    }


    /**
     * Processes a "color" command entered by the user
     * 
     * @precondition The command passed to this method is a "color" command
     * @param command
     *            The "color" command entered by the user
     */
    private void processColor(String command) {
        String[] commandSet = command.toLowerCase().split(" ");

        if (commandSet.length == 2) {
            String scheme = commandSet[1];

            // Changing the color scheme
            if (scheme.equals("basic")) {               // BASIC
                cube.setColorScheme(new BasicColors());
            }
            else if (scheme.equals("white")) {          // WHITE
                cube.setColorScheme(new WhiteColors());
            }
            else if (scheme.equals("dodo")) {           // DODO
                cube.setColorScheme(new DodoColors());
            }
            else {                                      // Error
                printWrongColor();
            }
        }
        else {                                          // Error
            printWrongColor();
        }
    }


    /**
     * Prints an error message telling the user they entered an invalid "color"
     * command
     */
    private void printWrongColor() {
        System.out.println(
            "Please enter a valid color scheme! Possible options include:");
        System.out.println("basic\twhite\tdodo");
    }


    /**
     * Parses a command that is either a set of turns or an invalid command
     * 
     * @precondition The command passed to this method is either a turn or
     *               invalid
     * @param commandString
     *            The turn command or invalid command being parsed
     */
    private void parseCommands(String commandString) {
        // Splitting up the command by spaces
        String[] commands = commandString.split(" ");

        // Checking that every specified turn is valid
        for (int i = 0; i < commands.length; i++) {
            boolean found = false;
            for (int j = 0; j < ALLOWED_BASIC_TURNS.length; j++) {
                if (ALLOWED_BASIC_TURNS[j].equals(commands[i])) {
                    found = true;
                }
            }

            // Telling the user that they entered an invalid command
            if (!found) {
                printInvalidCommand();
                return;
            }
        }

        // Executing each turn specified in the command one at a time
        for (int i = 0; i < commands.length; i++) {
            boolean success = executeCommand(commands[i]);
            if (!success) { // Checking to make sure the turn was executed
                            // correctly
                break;
            }
        }
    }


    /**
     * Executes a user-entered turn command on the cube buffer
     * 
     * @param command
     *            A single turn command entered by the user
     * @return True if the command was a valid turn command, false otherwise
     */
    private boolean executeCommand(String command) {
        if (command.equals("R")) {          // R
            cubeBuffer.right();
        }
        else if (command.equals("R'")) {    // R'
            cubeBuffer.rightPrime();
        }
        else if (command.equals("R2")) {    // R2
            cubeBuffer.rightTwo();
        }
        else if (command.equals("L")) {     // L
            cubeBuffer.left();
        }
        else if (command.equals("L'")) {    // L'
            cubeBuffer.leftPrime();
        }
        else if (command.equals("L2")) {    // L2
            cubeBuffer.leftTwo();
        }
        else if (command.equals("U")) {     // U
            cubeBuffer.up();
        }
        else if (command.equals("U'")) {    // U'
            cubeBuffer.upPrime();
        }
        else if (command.equals("U2")) {    // U2
            cubeBuffer.upTwo();
        }
        else if (command.equals("D")) {     // D
            cubeBuffer.down();
        }
        else if (command.equals("D'")) {    // D'
            cubeBuffer.downPrime();
        }
        else if (command.equals("D2")) {    // D2
            cubeBuffer.downTwo();
        }
        else if (command.equals("F")) {     // F
            cubeBuffer.front();
        }
        else if (command.equals("F'")) {    // F'
            cubeBuffer.frontPrime();
        }
        else if (command.equals("F2")) {    // F2
            cubeBuffer.frontTwo();
        }
        else if (command.equals("B")) {     // B
            cubeBuffer.back();
        }
        else if (command.equals("B'")) {    // B'
            cubeBuffer.backPrime();
        }
        else if (command.equals("B2")) {    // B2
            cubeBuffer.backTwo();
        }
        else {                              // Error
            printInvalidCommand();
            return false;
        }

        // Checking if cube is solved after executing the given turn command
        if (cubeBuffer.isSolved()) {
            System.out.println("Cube solved!");
        }

        // Updating the stickers' colors after turning
        cube.updateTilesAndCube();
        try {
            // Pausing between turns
            final int PAUSE_TIME = 450;
            Thread.sleep(PAUSE_TIME);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }


    /**
     * Generates and executes a scramble command that consists of 25 random
     * turns
     */
    private void genScramble() {
        Random rand = new Random();
        String scramble = "";

        // Generating the scramble
        final int TURN_COUNT = 25;
        for (int i = 0; i < TURN_COUNT; i++) {
            int index = rand.nextInt(ALLOWED_BASIC_TURNS.length);
            scramble += ALLOWED_BASIC_TURNS[index] + " ";
        }

        // Executing the scramble
        parseCommands(scramble);
    }


    /**
     * Creates a checker pattern on the cube if it is already solved, warns the
     * user that it cannot create the pattern if the cube is not solved
     */
    private void genCheckerPattern() {
        if (cubeBuffer.isSolved()) {
            parseCommands(checkerPatternAlgorithm);
        }
        else {
            System.out.println(
                "Please make sure the cube is solved before attemping to make the pattern.");
        }
    }


    /**
     * Tells the user that they entered an invalid command
     */
    private void printInvalidCommand() {
        System.out.println("Command not recognized!");
        System.out.println(
            "Type \"help\" to display a list of valid commands.");
    }

}
