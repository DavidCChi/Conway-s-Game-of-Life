import java.util.Random;

/**
 * A GameOfLife that prints out a grid of Cells and modifies them based
 * on the rules of Conway's Game Of Life.
 * 
 * @author David Chi 3 
 * @version 1.0 2015-04-27
 */
public class GameOfLife
{
    // class fields
    /**
     * The symbol that is displayed when a cell is alive.
     */
    public static final String ALIVE_CELL = "\u25CF";
    /**
     * <code>true</code> if all cells in this GameOfLife are dead, else <code>false</code>.
     */
    public static boolean areCellsDead;
    /**
     * The amount of cells around a single cell
     * except for when there's a wall beside the cell.
     */
    public static final int CELLS_AROUND_ONE = 8;
    /**
     * The symbol that is displayed when a cell is dead.
     */
    public static final String DEAD_CELL = "\u25CB";
    /**
     * The default amount of alive cells in this GameOfLife grid.
     */
    public static final int DEFAULT_ALIVE_CELLS = 1;
    /**
     * The default height of this GameOfLife grid.
     */
    public static final int DEFAULT_HEIGHT = 5;
    /**
     * The default width of this GameOfLife grid.
     */
    public static final int DEFAULT_WIDTH = 20;
    /**
     * The delay in which the GameOfLife waits until it paints the grid (in milliseconds).
     */
    public static final int DELAY = 2000;
    /**
     * The GameOfLife created when the main method of this class is caleld.
     */
    public static GameOfLife game;
    /**
     * A object of the Random class.
     */
    public static final Random generator = new Random();

    // instance fields
    private int aliveCells;
    private Cell[][] currentGeneration;
    private int deadCellCount;
    private Cell[][] futureGeneration;
    private int generationCount;
    private int heightOfGrid;
    private int widthOfGrid;

    /**
     * Creates a GameOfLife object.
     * 
     * @param arguments the amount of live cells in this GameOfLife
     * @param arguments the height of this GameOfLife
     * @param arguments the width of this GameOfLife
     */
    public static void main(String[] arguments)
    {
        int height = Integer.parseInt(arguments[1]);
        int width = Integer.parseInt(arguments[2]);
        game = new GameOfLife(Integer.parseInt(arguments[0]), height, width);
        while (!areCellsDead)
        {
            game.printGrid(height, width);
            game.algorithm(height, width);
        } // end of while (!areCellsDead)
    } // end of method main(String[] args)

    /*
     * constructors
     */

    /**
     * Constructor for objects of class GameOfLife.
     */
    public GameOfLife()
    {
        heightOfGrid = DEFAULT_HEIGHT;
        widthOfGrid = DEFAULT_WIDTH;
        currentGeneration = new Cell[heightOfGrid][widthOfGrid];
        futureGeneration = new Cell[heightOfGrid][widthOfGrid];
        aliveCells = 0;
        areCellsDead = false;
        generationCount = 1;
        for (int y = 0; y < heightOfGrid; y++)
        {
            for (int x = 0; x < widthOfGrid; x++)
            {
                currentGeneration[y][x] = new Cell();
                futureGeneration[y][x] = new Cell();
            } // end of for (int x = 0; x < widthOfGrid; x++)
        } // end of for (int y = 0; y < heightOfGrid; y++)
        randomize(DEFAULT_ALIVE_CELLS, heightOfGrid, widthOfGrid);
    } // end of constructor GameOfLife()

    /**
     * Constructor for objects of class GameOfLife.
     * 
     * @param amount the amount of alive cells in this GameOfLife
     * @param height the height of this GameOfLife
     * @param width the width of this GameOfLife
     */
    public GameOfLife(int amount, int height, int width)
    {
        if (height > 0 && width > 0)
        {
            heightOfGrid = height;
            widthOfGrid = width;
        } 
        else
        {
            heightOfGrid = DEFAULT_HEIGHT;
            widthOfGrid = DEFAULT_WIDTH;
        } // end of if (heightOfGrid > 0 && widthOfGrid > 0)
        currentGeneration = new Cell[heightOfGrid][widthOfGrid];
        futureGeneration = new Cell[heightOfGrid][widthOfGrid];
        aliveCells = 0;
        areCellsDead = false;
        generationCount = 1;
        for (int y = 0; y < heightOfGrid; y++)
        {
            for (int x = 0; x < widthOfGrid; x++)
            {
                currentGeneration[y][x] = new Cell();
                futureGeneration[y][x] = new Cell();
            } // end of for (int x = 0; x < widthOfGrid; x++)
        } // end of for (int y = 0; y < heightOfGrid; y++)
        if (amount >= 0 && amount <= heightOfGrid * widthOfGrid)
        {
            randomize(amount, heightOfGrid, widthOfGrid);
        } 
        else
        {
            randomize(DEFAULT_ALIVE_CELLS, heightOfGrid, widthOfGrid);
        } // end of if (amount > 0)
    } // end of constructor GameOfLife(int amount, int height, int width)

    /*
     * accessors
     */

    /**
     * Returns <code>true</code> if all the cells in this GameOfLife are dead, else <code>false</code>.
     * 
     * @param height the height of this GameOfLife
     * @param width the width of this GameOfLife
     * @return <code>true</code> if all the cells in this GameOfLife are dead, else <code>false</code>
     */
    public boolean checkCells(int height, int width)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (currentGeneration[y][x].getState())
                {
                    deadCellCount = 0;
                } 
                else
                {
                    deadCellCount++;
                } // end of if (futureGeneration[y][x].getState())
            } // end of for (int x = 0; x < width; x++)
        } // end of for (int y = 0; y < height; y++)

        if (deadCellCount < height * width)
        {
            generationCount++;
            return false;
        }
        else
        {
            return true;
        } // end of if (deadCellCount == height * width)
    } // end of method checkCells()
    
    /**
     * Checks if the cells in this GameOfLife is stable.
     */

    /**
     * Returns the value of current generation of cells.
     * 
     * @return the current generation
     */
    public String getCurrentGeneration()
    {
        String returnString = "";
        for (int y = 0; y < heightOfGrid; y++)
        {
            for (int x = 0; x < widthOfGrid; x++)
            {
                returnString = returnString + currentGeneration[y][x].toString();
            } // end of for (int x = 0; x < widthOfGrid; x++)
        } // end of for (int y = 0; y < heightOfGrid; y++)
        return returnString;
    } // end of method getCurrentGeneration()

    /**
     * Returns the value of a specific index of current generation of cells.
     * 
     * @param x the x-coordinate of the cell, must be less than the width of current generation, must be greater than or equal to 0
     * @param y the y-coordinate of the cell, must be less than the height of current generation, must be greater than or equal to 0
     * @return the state of the cell
     */
    public boolean getCurrentGenerationCell(int x, int y)
    {
        return currentGeneration[y][x].getState();
    } // end of method getCurrentGenerationCell(int x, int y)

    /**
     * Returns the value of future generation of cells.
     * 
     * @return the future generation
     */
    public String getFutureGeneration()
    {
        String returnString = "";
        for (int y = 0; y < heightOfGrid; y++)
        {
            for (int x = 0; x < widthOfGrid; x++)
            {
                returnString = returnString + futureGeneration[y][x].toString();
            } // end of for (int x = 0; x < widthOfGrid; x++)
        } // end of for (int y = 0; y < heightOfGrid; y++)
        return returnString;
    } // end of method getFutureGeneration()

    /**
     * Returns the value of a specific index of current generation of cells.
     * 
     * @param x the x-coordinate of the cell, must be less than the width of future generation, must be greater than or equal to 0
     * @param y the y-coordinate of the cell, must be less than the height of future generation, must be greater than or equal to 0
     * @return the state of the cell
     */
    public boolean getFutureGenerationCell(int x, int y)
    {
        return futureGeneration[y][x].getState();
    } // end of method getFutureGenerationCell(int x, int y)

    /**
     * Returns the generation this GameOfLife is on.
     * 
     * @return the generation 
     */
    public int getGeneration()
    {
        return generationCount;
    } // end of method getGeneration

    /**
     * Returns all the values of this GameOfLife.
     * 
     * @return all the values of the instance variables in this GameOfLife
     */
    public String toString()
    {
        return getClass().getName()
        + "["
        + "Current Generation: " + getCurrentGeneration()
        + ", Future Generation: " + getFutureGeneration()
        + ", Generation Count: " + generationCount
        + ", Height of Grid: " + heightOfGrid
        + ", Width Of Grid: " + widthOfGrid
        + "]";
    } // end of method toString()

    /*
     * mutators
     */

    /**
     * Sets the state of the current generation of cells.
     * 
     * @param state the new state of the cell
     * @param x the x-coordinate of the cell, must be less than the width of the current generation, must be greater than or equal to 0
     * @param y the y-coordinate of the cell, must be less than the height of the current generation, must be greater than or equal to 0
     */
    public void setCurrentGenerationCell(boolean state, int x, int y)
    {
        currentGeneration[y][x].setState(state);
    } // end of method setCurrentGeneration(boolean state, int x, int y)

    /**
     * Sets the state of the future generation of cells.
     * 
     * @param state the new state of the cell
     * @param x the x-coordinate of the cell, must be less than the width of the future generation, must be greater than or equal to 0
     * @param y the y-coordinate of the cell, must be less than the height of the future generation, must be greater than or equal to 0
     */
    public void setFutureGenerationCell(boolean state, int x, int y)
    {
        futureGeneration[y][x].setState(state);
    } // end of method setfutureGeneration(boolean state, int x, int y)

    /*
     * methods
     */

    /**
     * Checks the cells and executes the appropriate methods according to the GameOfLife rules.
     * 
     * @param height the height of this GameOfLife
     * @param width the width of this GameOfLife
     */
    public void algorithm(int height, int width)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                checkCellsAround(x, y);
                if (aliveCells < 2)
                {
                    futureGeneration[y][x].setState(false);
                } // end of if (aliveCells < 2)
                else if (aliveCells == 2 && aliveCells == 3)
                {
                    futureGeneration[y][x].setState(true);
                } // end of if (aliveCells == 2 && aliveCells == 3)
                else if (aliveCells > 3)
                {
                    futureGeneration[y][x].setState(false);
                } // end of if (aliveCells > 3)
                else if (aliveCells == 3 && currentGeneration[y][x].getState() == false)
                {
                    futureGeneration[y][x].setState(true);
                } // end of if (aliveCells == 3 && currentGeneration[y][x].getState == false)
            } // end of for (int x = 0; x < height; x++)
        } // end of for (int y = 0; y < width; y++) 
    } // end of method algorithm(int height, int width)

    /**
     * Checks the state of the cells around a cell.
     * 
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     */
    public void checkCellsAround(int x, int y)
    {
        boolean state;
        aliveCells = 0;
        try
        {
            state = currentGeneration[y - 1][x - 1].getState();
            if (state)aliveCells++;
        } // end of try
        catch (IndexOutOfBoundsException e)
        {
            // do nothing
        } // end of catch (IndexOutOfBounds e)
        try
        {
            state = currentGeneration[y][x - 1].getState();
            if (state)aliveCells++;
        } // end of try
        catch (IndexOutOfBoundsException e)
        {
            // do nothing
        } // end of catch (IndexOutOfBounds e)
        try
        {
            state = currentGeneration[y + 1][x - 1].getState();
            if (state)aliveCells++;
        } // end of try
        catch (IndexOutOfBoundsException e)
        {
            // do nothing
        } // end of catch (IndexOutOfBounds e)
        try
        {
            state = currentGeneration[y + 1][x].getState();
            if (state)aliveCells++;
        } // end of try
        catch (IndexOutOfBoundsException e)
        {
            // do nothing
        } // end of catch (IndexOutOfBounds e)
        try
        {
            state = currentGeneration[y + 1][x + 1].getState();
            if (state)aliveCells++;
        } // end of try
        catch (IndexOutOfBoundsException e)
        {
            // do nothing
        } // end of catch (IndexOutOfBounds e)
        try
        {
            state = currentGeneration[y][x + 1].getState();
            if (state)aliveCells++;
        } // end of try
        catch (IndexOutOfBoundsException e)
        {
            // do nothing
        } // end of catch (IndexOutOfBounds e)
        try
        {
            state = currentGeneration[y - 1][x + 1].getState();
            if (state)aliveCells++;
        } // end of try
        catch (IndexOutOfBoundsException e)
        {
            // do nothing
        } // end of catch (IndexOutOfBounds e)
        try
        {
            state = currentGeneration[y - 1][x].getState();
            if (state)aliveCells++;
        } // end of try
        catch (IndexOutOfBoundsException e)
        {
            // do nothing
        } // end of catch (IndexOutOfBounds e)
    } // end of method checkCellsAround(int x, int y)

    /**
     * Prints the grid of cells to the console.
     * 
     * @param height the height of this GameOfLife
     * @param width the width of this GameOfLife
     */
    public void printGrid(int height, int width)
    {
        boolean state;
        System.out.println("\fGeneration: " + generationCount);
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                currentGeneration[y][x].setState(futureGeneration[y][x].getState());
                state = currentGeneration[y][x].getState();
                if (!state)
                {
                    System.out.print(DEAD_CELL);
                }
                else
                {
                    System.out.print(ALIVE_CELL);
                } // end of if (!state)
                if (x == widthOfGrid - 1)
                {
                    System.out.print("\n");
                } // end of if (x == width - 1)
            } // end of for (int x = 0; x < height; x++)
        } // end of for (int y = 0; y < height; y++)
        areCellsDead = game.checkCells(height, width);
        if (!areCellsDead)
        {
            try
            {
                Thread.sleep(DELAY);
            } // end of try
            catch (InterruptedException e)
            {
                System.out.println("Something went wrong.");
            } // end of catch (InterruptedException e)
        } 
        else
        {
            System.out.println("All cells are dead.\nGame Over");
        } // end of if (!checkCells(height, width))
    } // end of method printGrid(int height, int width)

    /**
     * Randomizes the state of the cells in this GameOfLife.
     * 
     * @param amount amount of live cells in this GameOfLife
     */
    public void randomize(int amount, int height, int width)
    {
        int count = 0;
        int x = 0;
        int y = 0;
        while (count < amount)
        {
            x = generator.nextInt(width);
            y = generator.nextInt(height);
            if (!futureGeneration[y][x].getState())
            {
                futureGeneration[y][x].setState(true);
                count++;
            } // end of if (!futureGeneration[y][x].getState())
        } // end of for (int i = 0; i < amount; i++)
    } // end of method randomize(int amount)
} // end of class GameOfLife
