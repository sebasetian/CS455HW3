// Name: SHIH-YAO LIN
// USC NetID: shihyaol
// CSCI455 PA3
// Spring 2018

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * MazeComponent class
 *
 * A component that displays the maze and path through it if one has been found.
 *
    MazeComponent class has the following named constant:
     START_X : top left of corner of maze in frame
     START_Y
     BOX_WIDTH : width and height of one maze "location"
     BOX_HEIGHT
     INSET : how much smaller on each side to make entry/exit inner box
     START_COLOR : colors of startloc, endloc, wall, road(place that isn't a wall), and path
     EXIT_COLOR
     WALL_COLOR
     ROAD_COLOR
     LINE_COLOR


    MazeComponent class has the following instance variable:
         maze : the maze to search

    MazeComponent class supports the following methods:
     public method:
        paintComponent

     private method:
        drawPath
 */
public class MazeComponent extends JComponent {

    private static final int START_X = 10; // top left of corner of maze in frame
    private static final int START_Y = 10;
    private static final int BOX_WIDTH = 20;  // width and height of one maze "location"
    private static final int BOX_HEIGHT = 20;
    private static final int INSET = 2;
    // how much smaller on each side to make entry/exit inner box
    private static final Color START_COLOR = Color.YELLOW;
    private static final Color EXIT_COLOR = Color.GREEN;
    private static final Color WALL_COLOR = Color.BLACK;
    private static final Color ROAD_COLOR = Color.WHITE;
    private static final Color LINE_COLOR = Color.blue;
    private Maze maze;


    /**
     * Constructs the component.
     *
     * @param maze the maze to display
     */
    public MazeComponent(Maze maze) {
        this.maze = maze;
    }


    /**
     * Draws the current state of maze including the path(with drawpath method) through it if one has
     * been found.
     *
        How it works :
             1. set the location of a mazeblock
             2. determine the state(wall, road, startloc, or endloc) of the mazeblock and set color
             3. call drawpath method to draw path

     * @param g the graphics context
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle oneMaze = new Rectangle(START_X,START_Y,BOX_WIDTH,BOX_HEIGHT);
        MazeCoord currMazeLoc;

        for(int i = 0; i < maze.numRows();i++){
            oneMaze.setLocation(START_X,START_Y + i * BOX_HEIGHT);

            for(int j = 0; j < maze.numCols(); j++){
                currMazeLoc = new MazeCoord(i,j);

                if(maze.hasWallAt(currMazeLoc)) {

                    g.setColor(WALL_COLOR);

                }else if(currMazeLoc.equals(maze.getEntryLoc())){

                    g.setColor(START_COLOR);
                    oneMaze.setSize(BOX_WIDTH - INSET,BOX_HEIGHT - INSET);

                }else if(currMazeLoc.equals(maze.getExitLoc())){

                    g.setColor(EXIT_COLOR);
                    oneMaze.setSize(BOX_WIDTH - INSET,BOX_HEIGHT - INSET);

                }else{

                    g.setColor(ROAD_COLOR);

                }

                g2.fill(oneMaze);
                g2.draw(oneMaze);
                oneMaze.setSize(BOX_WIDTH,BOX_HEIGHT);
                oneMaze.translate(BOX_WIDTH,0);
            }
        }
        drawPath(g2);
    }

    /**
     * Draws the the path through it if one has been found.
     * if it has been not found yet then do nothing.
     *
     *
        How it works :
         1. draw a line, starting from the center of previous block to the center of current block
         2. save the location of current block as previous block and go to next block
         3. do 1) and 2) repeatedly until endloc is reached
     *
     * @param g2 the graphics context
     */
    private void drawPath(Graphics2D g2){
            LinkedList<MazeCoord> path = maze.getPath();
            int prevRow = 0;
            int prevCol = 0;
            int lineOffsetX = START_X + BOX_WIDTH/2;
            int lineOffsetY = START_Y + BOX_HEIGHT/2;
            if(path.isEmpty()){
                return;
            }
            for (MazeCoord point: path) {
                if (point.equals(maze.getEntryLoc())) {
                    prevRow = point.getRow();
                    prevCol = point.getCol();
                    g2.setColor(LINE_COLOR);
                } else {
                    g2.drawLine(lineOffsetX + prevCol * BOX_WIDTH,
                            lineOffsetY + prevRow * BOX_HEIGHT,
                            lineOffsetX + point.getCol() * BOX_WIDTH,
                            lineOffsetY + point.getRow() * BOX_HEIGHT);
                    prevRow = point.getRow();
                    prevCol = point.getCol();
                }
            }
    }
}



