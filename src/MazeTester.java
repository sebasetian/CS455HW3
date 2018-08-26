
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class MazeTester {
    public static void main(String[] args) {

        String fileName = "";


        try {
            if (args.length < 1) {
                System.out.println("ERROR: missing file name command line argument");
            } else {
                fileName = args[0];
                Maze testMaze = readMazeFile(fileName);
                if(testMaze.search()) {
                    LinkedList<MazeCoord> path = testMaze.getPath();
                    char[][] pathArr = new char[testMaze.numRows()][testMaze.numCols()];
                    boolean start = true;
                    int prevRow = 0;
                    int prevCol = 0;
                    for (Iterator it = path.iterator(); it.hasNext(); ) {
                        MazeCoord point = (MazeCoord) it.next();
                        if (start) {
                            pathArr[point.getRow()][point.getCol()] = 's';
                            start = false;
                            prevRow = point.getRow();
                            prevCol = point.getCol();
                        } else {
                            int rowDiff = point.getRow() - prevRow;
                            int colDiff = point.getCol() - prevCol;
                            if(!it.hasNext()){
                                pathArr[point.getRow()][point.getCol()] = 'e';
                                break;
                            }
                            if(rowDiff == 1) {
                                pathArr[point.getRow()][point.getCol()] = 'd';
                            }else if(rowDiff == -1){
                                pathArr[point.getRow()][point.getCol()] = 'u';
                            }
                            if(colDiff == 1){
                                pathArr[point.getRow()][point.getCol()] = 'r';
                            }
                            else if(colDiff == -1){
                                pathArr[point.getRow()][point.getCol()] = 'l';
                            }
                            prevRow = point.getRow();
                            prevCol = point.getCol();
                        }
                    }
                    for (char[] aPathArr : pathArr) {
                        for (int j = 0; j < pathArr[0].length; j++) {
                            System.out.print(aPathArr[j]);
                        }
                        System.out.println(' ');
                    }
                }else{
                    System.out.print("There is no path!!");
                }
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
     * @param fileName the name of a file to read from (file format shown in class comments, above)
     * @throws FileNotFoundException if there's no such file (subclass of IOException)
     * @throws IOException           (hook given in case you want to do more error-checking --
     *                               that would also involve changing main to catch other exceptions)
     * @returns a MazeFrame containing the data from the file.
     */
    private static Maze readMazeFile(String fileName) throws IOException {
        Scanner file = new Scanner(new File(fileName));
        int row = file.nextInt();
        int col = file.nextInt();
        boolean[][] mazeData = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            char[] nextLine = file.next().toCharArray();
            for (int j = 0; j < col; j++){
                mazeData[i][j] = nextLine[j] == '1';
            }
        }
        return new Maze(mazeData,new MazeCoord(file.nextInt(),file.nextInt()),
                new MazeCoord(file.nextInt(),file.nextInt()));
    }
}
