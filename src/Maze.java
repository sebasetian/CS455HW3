// Name: SHIH-YAO LIN
// USC NetID: shihyaol
// CSCI455 PA3
// Spring 2018

import java.util.LinkedList;


/**
 * Maze class
 *
 * Stores information about a maze and can find a path through the maze
 * (if there is one).
 *
 * Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
 * (parameters to constructor), and the path:
 * -- no outer walls given in mazeData -- search assumes there is a virtual
 * border around the maze (i.e., the maze path can't go outside of the maze
 * boundaries)
 * -- start location for a path is maze coordinate startLoc
 * -- exit location is maze coordinate exitLoc
 * -- mazeData input is a 2D array of booleans, where true means there is a wall
 * at that location, and false means there isn't (see public FREE / WALL
 * constants below)
 * -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
 * -- only travel in 4 compass directions (no diagonal paths)
 * -- can't travel through walls
 *
    Maze class has the following named constant:
        FREE : means that position is not wall(i.e. can be traveled through)
        WALL : means that position is not wall(i.e. can't be traveled through)


    Maze class has the following instance variable:
        mazeData : the maze to search
        startLoc : the location in maze to start the search
        exitLoc : the "exit" location of the maze


    Maze class supports the following methods:
     public method:
        numRows
        numCols
        hasWallAt
        getEntryLoc
        getExitLoc
        getEntryLoc
        getExitLoc
        getPath
        search

     private method:
        searchHelper
        isLocValid
 */

public class Maze {

    public static final boolean FREE = false;
    public static final boolean WALL = true;

    /**
     Representation invariant:

     1.the number of row of maze is mazeData.length

     2.the number of column of maze is mazeData[0].length

     3.0 < the row of any valid location < mazeData.length

     4.0 < the column of any valid location < mazeData[0].length

     5.elements stored in mazeData should be FREE if it is not wall(i.e. can be traveled through)

     6.elements stored in mazeData should be WALL if it is wall(i.e. can't be traveled through)

     7.start location for a path is maze coordinate startLoc

     8.exit location is maze coordinate exitLoc

     9.the path found by search method is LinkedList<MazeCoord> path

     10.the path only travels in 4 compass directions

     */
    private boolean[][] mazeData;
    private MazeCoord startLoc;
    private MazeCoord exitLoc;
    private LinkedList<MazeCoord> path;

    /**
     * Constructs a maze.
     *
     * @param mazeData the maze to search.  See general Maze comments above for what
     *                 goes in this array.
     * @param startLoc the location in maze to start the search (not necessarily on an edge)
     * @param exitLoc  the "exit" location of the maze (not necessarily on an edge)
     *                 PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
     *                 and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length
     */
    public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {
        this.mazeData = mazeData.clone();
        this.startLoc = startLoc;
        this.exitLoc = exitLoc;
    }


    /**
     * Returns the number of rows in the maze
     *
     * @return number of rows
     */
    public int numRows() {
        return mazeData.length;
    }


    /**
     * Returns the number of columns in the maze
     *
     * @return number of columns
     */
    public int numCols() {
        return mazeData[0].length;
    }


    /**
     * Returns true iff there is a wall at this location
     *
     * @param loc the location in maze coordinates
     * @return whether there is a wall here
     * PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
     */
    public boolean hasWallAt(MazeCoord loc) {
        return mazeData[loc.getRow()][loc.getCol()];
    }


    /**
     * Returns the entry location of this maze.
     */
    public MazeCoord getEntryLoc() {
        return startLoc;
    }


    /**
     * Returns the exit location of this maze.
     */
    public MazeCoord getExitLoc() {
        return exitLoc;
    }


    /**
     * Returns the path through the maze. First element is start location, and
     * last element is exit location.  If there was not path, or if this is called
     * before a call to search, returns empty list.
     *
     * @return the maze path
     */
    public LinkedList<MazeCoord> getPath() {
        if (path == null) {
            return new LinkedList<>();
        }
        return path;
    }


    /**
     * Find a path from start location to the exit location with searchHelper method (see Maze
     * constructor parameters, startLoc and exitLoc) if there is one.
     * Client can access the path found via getPath method.
     *
     * @return whether a path was found.
     */
    public boolean search() {
        if (path != null) {
            return true;
        }
        LinkedList<MazeCoord> TempPath = new LinkedList<>();
        boolean[][] isVisited = new boolean[numRows()][numCols()];
        return searchHelper(TempPath,startLoc,isVisited);
    }
    /**
        Recursively find a path if there is one.

        How it works : DFS algorithm
                        1. determine whether the current location is valid with isLocValid method
                        2. if valid, then set the current location 'isVisited' in a 2D array and go to all adjacent location
                        3. repeat 1) 2) until the exit location is found or all reachable location is reached
                        4. if the exit location is found then add all location into path(with addFirst method)
                            and return true
                        5. else if all reachable location is reached or the current location is WALL then return false

        @return whether a path found so far is valid.
     */
    private boolean searchHelper(LinkedList<MazeCoord> TempPath,MazeCoord loc,boolean[][] isVisited) {
        if(!isLocValid(loc, isVisited)){
            return false;
        }

        if(loc.equals(exitLoc)){
            TempPath.addFirst(loc);
            if(loc.equals(startLoc)){
                path = new LinkedList<>(TempPath);
            }
            return true;
        }

        boolean isValidPath =
                searchHelper(TempPath,new MazeCoord(loc.getRow() + 1,loc.getCol()),isVisited) ||
                searchHelper(TempPath,new MazeCoord(loc.getRow() - 1,loc.getCol()),isVisited) ||
                searchHelper(TempPath,new MazeCoord(loc.getRow(),loc.getCol() + 1),isVisited) ||
                searchHelper(TempPath,new MazeCoord(loc.getRow(),loc.getCol() - 1),isVisited);

        if(isValidPath){
            TempPath.addFirst(loc);
            if(loc.equals(startLoc)){
                path = new LinkedList<>(TempPath);
            }
        }

        return isValidPath;
    }
    /**
         Determine whether a location is valid. See Representation invariant.
             1. if a location is out of the bound then return false
             2. if a location is WALL then return false
             3. if a location is isVisited(i.e. isVisited[loc] == true) then return false
             4. else then set isVisit[loc] is true and return true

         @return whether a location is valid.
     */
    private boolean isLocValid(MazeCoord loc,boolean[][] isVisited){
        if(loc.getRow() >= numRows() || loc.getRow() < 0 ||
                loc.getCol() >= numCols() || loc.getCol() < 0){
            return false;
        }
        if(hasWallAt(loc)){
            return false;
        }
        if(isVisited[loc.getRow()][loc.getCol()]){
            return false;
        }
        isVisited[loc.getRow()][loc.getCol()] = true;
        return true;
    }
}
