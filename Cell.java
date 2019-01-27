/**
 * A Cell that knows its state and x and y coordinates.
 * 
 * @author David Chi 3
 * @version 1.0 2015-04-23
 */
public class Cell
{
    // instance fields
    private boolean state;
    private int xCoordinate;
    private int yCoordinate;

    /*
     * constructors
     */
    
    /**
     * Constructs a cell with default values.
     */
    public Cell()
    {
        state = false;
        xCoordinate = 0;
        yCoordinate = 0;
    } // end of constructor Cell()
    
    /**
     * Constructs a cell with specific values.
     * 
     * @param state the state of the cell, <code>true</code> if the cell is alive, else <code>false</code>
     */
    public Cell(boolean state)
    {
        this.state = state;
    } // end of constructor Cell(boolean state)
    
    /*
     * accessors
     */
    
    /**
     * Returns the state of this cell.
     * 
     * @return the state of this cell
     */
    public boolean getState()
    {
        return state;
    } // end of method getState()
    
    /**
     * Returns the x-coordinate of this cell.
     * 
     * @return the x-coordinate of this cell
     */
    public int getXCoordinate()
    {
        return xCoordinate;
    } // end of method getXCoordinate()
    
    /**
     * Returns the y-coordinate of this cell.
     * 
     * @return the y-coordinate of this cell
     */
    public int getYCoordinate()
    {
        return yCoordinate;
    } // end of method getYCoordinate()
    
    /**
     * Returns the value of this cell.
     * 
     * @return the value of this cell
     */
    public String toString()
    {
        return getClass().getName() 
        + "[" 
        + "State of this cell: " + state
        + ", x-coordinate: " + xCoordinate
        + ", y-coordinate: " + yCoordinate
        + "]";
    } // end of method toString()
    
    /*
     * mutators
     */
    
    /**
     * Sets the state of this cell.
     * 
     * @param state the state of this cell
     */
    public void setState(boolean state)
    {
        this.state = state;
    } // end of method setState(boolean state)
    
    /**
     * Sets the x-coordinate of this cell.
     * 
     * @param x the x coordinate of this cell
     */
    public void setXCoordinate(int x)
    {
        xCoordinate = x;
    } // end of method setXCoordinate(int x)
    
    /**
     * Sets the y-coordinate of this cell.
     * 
     * @param y the y coordinate of this cell
     */
    public void setYCoordinate(int y)
    {
        yCoordinate = y;
    } // end of method setYCoordinate(int y)
} // end of class Cell
