import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * Write a description of class frame here.
 * 
 * @author David Chi 3 
 * @version 1.0 2015-04-27
 * @version 1.1 2015-04-28
 */
public class Frame
{
    // class fields
    /**
     * The colour of a alive cell.
     */
    public static final Color ALIVE_CELL = Color.GREEN;
    /**
     * The colour of a dead cell.
     */
    public static final Color DEAD_CELL = Color.LIGHT_GRAY;
    /**
     * The delay in which this frame waits to be repainted, in milliseconds.
     */
    public static final int DELAY = 100;
    /**
     * The eigth row of the content pane.
     */
    public static final int EIGTH_ROW = 7;
    /**
     * The fifth row of the content pane.
     */
    public static final int FIFTH_ROW = 4;
    /**
     * The first column of the content pane.
     */
    public static final int FIRST_COLUMN = 0;
    /**
     * The first row of the content pane.
     */
    public static final int FIRST_ROW = 0;
    /**
     * The fourth row of the content pane.
     */
    public static final int FOURTH_ROW = 3;
    /**
     * The second column of the content pane.
     */
    public static final int SECOND_COLUMN = 1;
    /**
     * The second row of the content pane.
     */
    public static final int SECOND_ROW = 1;
    /**
     * The seventh row of the content pane.
     */
    public static final int SEVENTH_ROW = 6;
    /**
     * The sixth row of this content pane.
     */
    public static final int SIXTH_ROW = 5;
    /**
     * The third row of the content pane.
     */
    public static final int THIRD_ROW = 2;
    /**
     * How the height and width of a component will change when the frame is resized.
     */
    public static final double WEIGHTX = 0.5;
    /**
     * The height and width of a component will not change when the frame is resized.
     */
    public static final double WEIGHTX_NONE = 0.0;
    /**
     * The width of a component (in columns)
     */
    public static final int WIDTH = 2;

    // instance fields
    private boolean areCellsDead;
    private JButton createGrid;
    private JFrame frame;
    private GameOfLife game;
    private JLabel gameStatus;
    private JLabel generation;
    private JButton[][] grid;
    private JPanel gridContentPane;
    private int height;
    private JLabel heightLabel;
    private JTextField heightField;
    private boolean isForceExit;
    private boolean isGameRunning;
    private Container mainContentPane;
    private int randomize;
    private JLabel randomizeLabel;
    private JTextField randomizeField;
    private JButton start;
    private JPanel selectionContentPane;
    private Timer timer;
    private JButton userForceExit;
    private boolean userSelectCells;
    private int width;
    private JLabel widthLabel;
    private JTextField widthField;

    /**
     * Initializes this frame.
     */
    public Frame()
    {
        paintFrame();
    } // end of method init()

    /*
     * private utility methods
     */

    /**
     * Creates a grid with specified values
     */
    public void createGrid()
    {
        setGridDimensions();

        isForceExit = false;
        areCellsDead = false;

        if (height > 0 && width > 0 && randomize >= 0 && randomize < height * width)
        {
            if (randomize == 0)
            {
                userSelectCells = true;
            }
            else
            {
                userSelectCells = false;
            } // end of if (randomize == 0)
            createGrid.setEnabled(false);
            start.setEnabled(true);
            paintGrid(height, width);
        } // end of if (height != 0 && width != 0)
    } // end of method createGrid()

    /**
     * Sets the button of the grid not clickable.
     * 
     * @param height the height of the grid
     * @param width the width of the grid
     */
    public void disableButton(int height, int width)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                grid[y][x].setEnabled(false);
            } // end of for (int x = 0; x < width; x++)
        } // end of for (int y = 0; y < height; y++)
    } // end of method disableButton()

    /**
     * Exits the loop that applies the GameOfLife rules.
     */
    private void forceExit()
    {
        isForceExit = true;  
        gameStatus.setText("Game Status: Forced Exit");
        heightField.setEditable(true);
        widthField.setEditable(true);
        randomizeField.setEditable(true);
        createGrid.setEnabled(true);
        userForceExit.setEnabled(false);
    } // end of forceExit() 

    /**
     * Paints the frame.
     * 
     * @param graphics the frame of the web browser that stores this frame
     */
    private void paintFrame()
    {
        GridBagConstraints constraints = new GridBagConstraints();
        timer = new Timer(DELAY, new Start());
        frame = new JFrame("Conway's Game Of Life");

        selectionContentPane = new JPanel();
        selectionContentPane.setLayout(new GridBagLayout());

        heightLabel = new JLabel("Height: ");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX_NONE;        
        constraints.gridx = FIRST_COLUMN;
        constraints.gridy = FIRST_ROW;
        selectionContentPane.add(heightLabel, constraints);

        heightField = new JTextField();
        heightField.setPreferredSize(new Dimension(50, 25));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX; 
        constraints.gridx = SECOND_COLUMN;
        constraints.gridy = FIRST_ROW;
        selectionContentPane.add(heightField, constraints);

        widthLabel = new JLabel("Width: ");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX_NONE; 
        constraints.gridx = FIRST_COLUMN;
        constraints.gridy = SECOND_ROW;
        selectionContentPane.add(widthLabel, constraints);

        widthField = new JTextField();
        widthField.setPreferredSize(new Dimension(50, 25));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX; 
        constraints.gridx = SECOND_COLUMN;
        constraints.gridy = SECOND_ROW;
        selectionContentPane.add(widthField, constraints);

        randomizeLabel = new JLabel("Randomize: (enter 0 if you want to select specific alive cells.)");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX_NONE; 
        constraints.gridx = FIRST_COLUMN;
        constraints.gridy = THIRD_ROW;
        selectionContentPane.add(randomizeLabel, constraints);

        randomizeField = new JTextField();
        randomizeField.setPreferredSize(new Dimension(50, 25));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX; 
        constraints.gridx = SECOND_COLUMN;
        constraints.gridy = THIRD_ROW;
        selectionContentPane.add(randomizeField, constraints);

        createGrid = new JButton("Create Grid");
        createGrid.addActionListener(new CreateGrid());
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX;
        constraints.gridwidth = WIDTH;
        constraints.gridx = FIRST_COLUMN;
        constraints.gridy = FOURTH_ROW;
        selectionContentPane.add(createGrid, constraints);

        start = new JButton("Start");
        start.addActionListener(new Start());
        start.setEnabled(false);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX;
        constraints.gridwidth = WIDTH;
        constraints.gridx = FIRST_COLUMN;
        constraints.gridy = FIFTH_ROW;
        selectionContentPane.add(start, constraints);

        userForceExit = new JButton("Force Exit");
        userForceExit.addActionListener(new ForceExit());
        userForceExit.setEnabled(false);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX;
        constraints.gridwidth = WIDTH;
        constraints.gridx = FIRST_COLUMN;
        constraints.gridy = SIXTH_ROW;
        selectionContentPane.add(userForceExit, constraints);

        generation = new JLabel("Generation: 0");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX;
        constraints.gridwidth = WIDTH;
        constraints.gridx = FIRST_COLUMN;
        constraints.gridy = SEVENTH_ROW;
        selectionContentPane.add(generation, constraints);

        gameStatus = new JLabel ("Game Status: Not Started");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = WEIGHTX;
        constraints.gridwidth = WIDTH;
        constraints.gridx = FIRST_COLUMN;
        constraints.gridy = EIGTH_ROW;
        selectionContentPane.add(gameStatus, constraints);

        mainContentPane = frame.getContentPane();
        mainContentPane.setLayout(new BorderLayout());
        mainContentPane.add(selectionContentPane, BorderLayout.CENTER);

        frame.setContentPane(mainContentPane);
        frame.pack();
        frame.setVisible(true);
    } // end of method paintframe()

    /**
     * Paints the grid of this frame.
     * 
     * @param height the height of the grid being made
     * @param width the width of the grid being made
     */
    private void paintGrid(int height, int width)
    {
        removeGrid();

        gridContentPane = new JPanel();
        gridContentPane.setLayout(new GridLayout(height, width));

        game = new GameOfLife(randomize, height, width);

        grid = new JButton[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                grid[y][x] = new JButton();
                grid[y][x].setPreferredSize(new Dimension(50, 50));
                grid[y][x].setBackground(DEAD_CELL);
                grid[y][x].setForeground(DEAD_CELL);
                grid[y][x].addActionListener(new SelectCells());
                if (!userSelectCells)grid[y][x].setEnabled(false);
                gridContentPane.add(grid[y][x]);
            } // end of for (int x = 0; x < widthOfGrid; x++)
        } // end of for (int y = 0; y < heightOfGrid; y++)
        heightField.setEditable(false);
        widthField.setEditable(false);
        randomizeField.setEditable(false);

        mainContentPane.add(gridContentPane, BorderLayout.CENTER);
        mainContentPane.add(selectionContentPane, BorderLayout.WEST);

        frame.setContentPane(mainContentPane);
        frame.pack();
    } // end of method paintGrid()

    /**
     * Resets the values of the current generation and future generation of cells in the GameOfLife
     */
    public void resetGeneration(int height, int width)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                game.setCurrentGenerationCell(false, x, y);
                game.setFutureGenerationCell(false, x, y);
            } // end of for (int x = 0; x < width; x++)
        } // end of for (int y = 0; y < height; y++)
    } // end of method resetGeneration(int height, int width)

    /**
     * Repaints the grid after applying GameOfLife rules.
     * 
     * @param height the height of the grid
     * @param width the width of the grid
     */
    private void repaintGrid(int height, int width)
    {
        boolean state;
        int generationCount;
        timer.start();
        if (!areCellsDead && !isForceExit)
        {
            isGameRunning = true;
            start.setEnabled(false);
            userForceExit.setEnabled(true);
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    game.setCurrentGenerationCell(game.getFutureGenerationCell(x, y), x, y);
                    state = game.getCurrentGenerationCell(x, y);
                    if (!state)
                    {
                        grid[y][x].setBackground(DEAD_CELL);
                        grid[y][x].setForeground(DEAD_CELL);
                    } 
                    else
                    {
                        grid[y][x].setForeground(ALIVE_CELL);
                        grid[y][x].setBackground(ALIVE_CELL);
                    } // end of if (!state)

                    grid[y][x].setEnabled(true);
                } // end of for (int x = 0; x < width; x++)
            } // end of for (int y = 0; y < height; y++)

            generationCount = game.getGeneration();
            generation.setText("Generation: " + generationCount);

            areCellsDead = game.checkCells(height, width);

            game.algorithm(height, width);
            gameStatus.setText("Game Status: Alive");
            if (areCellsDead) // checks if areCellsDead before this method runs again, or else there will be a 1 second delay.
            {
                timer.stop();
                gameStatus.setText("Game Status: Game Over");
                resetGeneration(height, width);
                heightField.setEditable(true);
                widthField.setEditable(true);
                randomizeField.setEditable(true);
                createGrid.setEnabled(true);
                userForceExit.setEnabled(false);
                isGameRunning = false;
                disableButton(height, width);
            } // end of if (!areCellsDead)
        } // end of if (!areCellsDead && !isForceExit)
        frame.repaint();
    } // end of method repaintGrid()

    /**
     * Removes the old grid if it exists.
     */
    private void removeGrid()
    {
        if (gridContentPane != null) 
        {
            mainContentPane.remove(gridContentPane);
            generation.setText("Generation: 0");
            gameStatus.setText("Game Status: Not Started");
        } // end of if (gridContentPane != null) 
    } // end of method removeGrid()

    /**
     * Allows user to select the specific alive cells in this frame.
     */
    private void selectCells(ActionEvent event)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (grid[y][x].equals(event.getSource()))
                {
                    if (!game.getFutureGenerationCell(x, y))
                    {
                        grid[y][x].setForeground(ALIVE_CELL);
                        grid[y][x].setBackground(ALIVE_CELL);
                        game.setFutureGenerationCell(true, x, y);
                    }
                    else
                    {
                        grid[y][x].setForeground(DEAD_CELL);
                        grid[y][x].setBackground(DEAD_CELL);
                        game.setFutureGenerationCell(false, x, y);
                    } // end of if (grid[y][x].equals(event.getSource()))
                } // end of if (grid[y][x].equals(event.getSource()))
            } // end of for (JButton column: row)
        } // end of for (JButton[] button: grid)
    } // end of method selectCells()

    /**
     * Sets the grid dimensions of the grid.
     */
    private void setGridDimensions()
    {
        height = 0; 
        int tempHeight = Integer.parseInt(heightField.getText());
        if (tempHeight > 0) height = tempHeight;

        width = 0;
        int tempWidth = Integer.parseInt(widthField.getText());
        if (tempWidth > 0) width = tempWidth;

        randomize = 0;
        int tempRandomize = Integer.parseInt(randomizeField.getText());
        if (tempRandomize >= 0) randomize = tempRandomize;
    } // end of method setGridDimensions()

    /*
     * private inner classes
     */
    private class CreateGrid implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            createGrid();
        } // end of method actionPerformed(ActionEvent event)
    } // end of class CreateGrid implements ActionListener

    private class ForceExit implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            forceExit();
            resetGeneration(height, width);
        } // end of method actionPerformed(ActionEvent event)
    } // end of class ForceExit implements ActionListener

    private class Start implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            repaintGrid(height, width);
        } // end of method actionPerformed(ActionEvent event)
    } // end of class Start implements ActionListener

    private class SelectCells implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            selectCells(event);
        } // end of method public void actionPerformed(ActionEvent event)
    } // end of class SelectCells implements ActionListener
} // end of class Frame
