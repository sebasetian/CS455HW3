// Name: SHIH-YAO LIN
// USC NetID: shihyaol
// CSCI455 PA3
// Spring 2018


import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 * MazeViewer class
 *
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 *
 * How to call it from the command line:
 *
 * java MazeViewer mazeFile
 *
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start location,
 * and ending with the exit location. Each maze location is
 * either a wall (1) or free (0). Here is an example of contents of a file for
 * a 3x4 maze, with start location as the top left, and exit location as the bottom right
 * (we count locations from 0, similar to Java arrays):
 *
 * 3 4
 * 0111
 * 0000
 * 1110
 * 0 0
 * 2 3
 */

public class MazeViewer {

    private static final char WALL_CHAR = '1';
    private static final char FREE_CHAR = '0';

    public static void main(String[] args) {

        String fileName = "";

        try {

            if (args.length < 1) {
                System.out.println("ERROR: missing file name command line argument");
            } else {
                fileName = args[0];

                JFrame frame = readMazeFile(fileName);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setVisible(true);
            }

        } catch (FileNotFoundException exc) {
            System.out.println("ERROR: File not found: " + fileName);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * readMazeFile reads in maze from the file whose name is given and
     * returns a MazeFrame created from it.
     *
       How it works :
         1. read the file with Scanner, if the element is WALL_CHAR then set it as WALL
            if the element is FREE_CHAR then set it as FREE
     *
     * @param fileName the name of a file to read from (file format shown in class comments, above)
     * @throws FileNotFoundException if there's no such file (subclass of IOException)
     * @throws IOException           (hook given in case you want to do more error-checking --
     *                               that would also involve changing main to catch other exceptions)
     * @return a MazeFrame containing the data from the file.
     */
    private static MazeFrame readMazeFile(String fileName) throws IOException {
        Scanner file = new Scanner(new File(fileName));
        int row = file.nextInt();
        int col = file.nextInt();
        boolean[][] mazeData = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            char[] nextLine = file.next().toCharArray();
            for (int j = 0; j < col; j++) {
                if (nextLine[j] == WALL_CHAR) {
                    mazeData[i][j] = Maze.WALL;
                } else if(nextLine[j] == FREE_CHAR) {
                    mazeData[i][j] = Maze.FREE;
                }
            }
        }
        int startRow = file.nextInt(), startCol = file.nextInt();
        int endRow = file.nextInt(), endCol = file.nextInt();
        return new MazeFrame(mazeData, new MazeCoord(startRow,startCol),
                new MazeCoord(endRow, endCol));

    }

}
