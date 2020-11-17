/************************************************
 * Assignment 8
 * Name: Austin Preston
 * E-mail: apreston3@um.edu
 * Course: CS 152 - Section 006
 * Data submitted: 9 Nov. 2020
 *
 * This program displays two kinds of CA's
 ************************************************/

import javax.swing.*;
import java.awt.*;

public class CellularAutomata extends Canvas {


    final int ALIVE = 1;
    final int DEAD = 0;
    static int screenSize = 700;
    static int cellSize = 10;
    static int arraySize = screenSize / cellSize;
    int[][] currentStates = new int[arraySize][arraySize];
    int[][] nextStates = new int[arraySize][arraySize];
    int delayTime = 100;


    public static void main(String[] args) {
        //Creates the screen for your CA
        CellularAutomata canvas = new CellularAutomata();
        setupScreen(canvas);
        canvas.myMethod();  //This calls the method myMethod
    }

    /**
     * This method sets up the screen for our CA
     *
     * @param canvas this is the Cellular Automaton/Canvas that the CA is drawn on
     */
    public static void setupScreen(CellularAutomata canvas) {
        JFrame frame = new JFrame("Cellular Automata"); //give screen a name
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sets the size of the screen
        // See https://docs.oracle.com/javase/9/docs/api/javafx/scene/canvas/Canvas.html
        canvas.setSize(screenSize, screenSize);
        // Sets the background color
        // See https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html

        //This makes the background a grid when set to BLACK/GRAY
        canvas.setBackground(Color.GRAY);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);


    }

    /**
     * This method draws things on the screen.
     * This is where you will write
     * code that displays your CA. You do not
     * call this method. It is called automatically.
     * A few sample drawing features are demonstrated below.
     */
    public void paint(Graphics g) {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                Color aliveColor = new Color(135, 67, 206);
                Color deadColor = Color.WHITE;
                if (currentStates[i][j] == ALIVE) {
                    g.setColor(aliveColor);
                } else {
                    g.setColor(deadColor);
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize - 1, cellSize - 1);
            }
        }
    }

    /**
     * This method includes some useful
     * functionality that you will want
     * to include in your code. Feel free
     * to rename or delete this method
     */
    public void myMethod() {

        //Initialization Method
        randomInitialization();

        while (true) {
            //computes rule for all cells
            for (int i = 1; i < arraySize - 1; i++) {
                for (int j = 1; j < arraySize - 1; j++) {

                    //This is where to change the game rules and thus the changing the current displaying CA
                    //The options are gameOfLifeRule or customRule with the same parameters
                    nextStates[i][j] = gameOfLifeRule(i, j);
                }
            }

            //update the currentStates array
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {
                    currentStates[i][j] = nextStates[i][j];
                }
            }

            //delays the program so that we can see each step of the animation
            delay(delayTime);

            // The repaint() method redraws your screen.
            // You can use it to refresh your screen after
            // you've updated your CA to its next state
            repaint();
        }
    }

    /**
     * This method pauses your program for delayTime
     *
     * @param delayTime the time to pause the program
     */

    void delay(int delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (
                Exception exc) {
        }

    }

    void randomInitialization() {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                currentStates[i][j] = (int) (Math.random() * 2);
            }
        }
    }

    void blinker() {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                currentStates[i][j] = DEAD;
            }
        }
        currentStates[arraySize / 2 - 1][arraySize / 2] = ALIVE;
        currentStates[arraySize / 2][arraySize / 2] = ALIVE;
        currentStates[arraySize / 2 + 1][arraySize / 2] = ALIVE;
    }

    int gameOfLifeRule(int row, int column) {
        int neighborSum =
                currentStates[row - 1][column - 1] +
                        currentStates[row - 1][column] +
                        currentStates[row - 1][column + 1] +
                        currentStates[row][column - 1] +
                        currentStates[row][column + 1] +
                        currentStates[row + 1][column - 1] +
                        currentStates[row + 1][column] +
                        currentStates[row + 1][column + 1];

        if (currentStates[row][column] == ALIVE && (neighborSum == 2 || neighborSum == 3)) {

            return ALIVE;
        } else if (currentStates[row][column] == DEAD && neighborSum == 3) {
            return ALIVE;
        } else {
            return DEAD;
        }
    }

    /**
     * This is the custom rule which can be seen as a simulation of rain
     *
     * @param row    boxes going horizontal
     * @param column boxes going vertical
     * @return returns either ALIVE or DEAD
     */
    int customRule(int row, int column) {
        int neighborSum =
                currentStates[row - 1][column] +
                        currentStates[row + 1][column] +
                        currentStates[row][column - 1] +
                        currentStates[row - 1][column] + 1;

        if (currentStates[row][column] == ALIVE && (neighborSum < 4)) {
            currentStates[row - 1][column] = ALIVE;
            currentStates[row + 1][column] = ALIVE;
            currentStates[row][column - 1] = ALIVE;
            currentStates[row - 1][column] = ALIVE;
            return ALIVE;
        } else {
            return DEAD;
        }
    }

    /**
     * This method reduces flickering of the display
     * Don't change it.
     */
    public void update(Graphics g) {
        paint(g);
    }
}