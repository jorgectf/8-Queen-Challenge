package me.adamcraftmaster;

import java.util.Scanner;
public class App 
{
    static int debug = 0;
    static boolean printOne = false;
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose DEBUG level, 0 for none, 1 for checkValid verbose, 2 for checkLine verbose, 3 for all verbose, 4 for only number of solutions");
        debug = scanner.nextInt();
        if(debug > 4) debug = 0;
        System.out.println("Choose if you want to print all combos or just one, 1 for one, 2 for all");
        switch (scanner.nextInt()) {
            case 1:
                printOne = true;
                break;
            case 2:
                printOne = false;
                break;
            default:
                printOne = false;
                break;
        }
        
        //Initialize the starting positions of all pieces
        System.out.println("Enter the number of rows and columns");
        int size = scanner.nextInt();
        int[] startboard;
        int solutions;
        scanner.close();
        if(size == 0) { //secret mode to test many board sizes
            System.out.println("Warning: this tests every solution for boards from 3x3 to 20x20, please stop program if your computer heats up!");
            for(int i = 3; i <= 20; i++) {
                startboard = new int[i];
                solutions = checkLine(startboard,0);
                System.out.println("Board Size: " + i + " Number of Solutions: " + solutions);
            }
        }
        else { //normal mode
            startboard = new int[size]; //initialize the board with the size given by the user
            solutions = checkLine(startboard,0); //check the board for solutions
            System.out.println("Found " + solutions + " solutions!"); //print the number of solutions found
        }
    }

    
    /** 
     * A more advanced print function that only sends the board to the console if the debug level is high enough
     * @param message the message to print
     * @param debugLevel the debug level to check against
     */
    public static void debug(String message, int debugLevel) {
        if (debugLevel == debug || debug == 3) { //debug level 3 is all debug
            System.out.println(message);
        }
    }

    
    /** 
     * A function that prints out a pretty board
     * @param board the board to print
     */
    public static void printBoard(int[] board){
// └┘┼─┴├┤┬┌┐│
/*
┌───┬───┬───┬───┬───┬───┬───┬───┐
│ x │ . │ x │ . │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┼
│ x │ . │ x │ . │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┼
│ x │ . │ x │ . │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┼
│ x │ . │ x │ . │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┼
│ x │ . │ x │ . │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┼
│ x │ . │ x │ . │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┼
│ x │ . │ x │ . │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┼
│ x │ . │ x │ . │   │   │   │   │
└───┴───┴───┴───┴───┴───┴───┴───┘
beautiful OwO
*/

        //┌───┬───┬───┬───┬───┬───┬───┬───┐
        System.out.print("┌"); //top left corner
        for(int i = 0; i < board.length-1; i++){
            System.out.print("───┬"); //top row
        }
        System.out.println("───┐"); //top right corner
        //│ x │ . │ x │ . │   │   │   │   │
        for(int y = 0; y < board.length; y++){
            System.out.print("│"); //left side
            for(int x = 1; x <= board.length; x++){
                if(board[y] == x) {
                    System.out.print(" X "); //queen
                }
                else {
                    System.out.print(" . "); //empty
                }
                System.out.print("│"); //right side
            }
            System.out.println();
            if(y != board.length-1){
                //├───┼───┼───┼───┼───┼───┼───┼───┤
                System.out.print("├"); //middle left
                for (int i = 0; i < board.length-1; i++) {
                    System.out.print("───┼"); //middle row
                }
                System.out.println("───┤"); //middle right
            }
            else {
                //└───┴───┴───┴───┴───┴───┴───┴───┘
                System.out.print("└"); //bottom left
                for (int i = 0; i < board.length-1; i++) {
                    System.out.print("───┴"); //bottom row
                }
                System.out.println("───┘"); //bottom right
            }
        }

    }

    
    /** 
     * A function that checks two pieces against each other to see if they can be placed on the board
     * @param x1 the x of the first piece
     * @param y1 the y of the first piece
     * @param x2 the x of the second piece
     * @param y2 the y of the second piece
     * @return boolean: true if the pieces can be placed on the board, false if they can't
     */
    public static boolean checkValid(int x1, int y1, int x2, int y2) {
        debug("Checking validity of " + x1 + " " + y1 + " " + x2 + " " + y2, 1);
        //check the validity between 2 pieces and see if they attack eachother (directly above/below or diagonal)
        if (x1 == x2) { //same row
            debug("same line", 1);
            return false;
        }
        else if ((y2-y1)==(x2-x1) || (y2-y1)==-(x2-x1)) { //diagonal because slope of 1 or -1
            debug("diagonal", 1);
            return false;
        }
        else {
            debug("valid move", 1); //valid move
            return true;
        }
    }

    
    /** 
     * A recursive function that checks the board for solutions
     * @param board the board to check
     * @param y the line to check
     * @return int: the number of solutions found
     */
    public static int checkLine(int board[], int y) {
        int solutionCount = 0; 
        debug("Checking line " + (y+1), 2); 
        if (debug==2 || debug==3) printBoard(board); //print board if debug level is 2 or 3
        for (int x = 1; x <= board.length; x++) {  //for each piece
            debug("x: " + x + " y: " + (y+1), 2);
            boolean invalidEver = false; //if any piece is invalid, then the whole line is invalid
            for (int yTest = 0; yTest < y; yTest++) {  //for each piece that has been placed
                if (!checkValid(x, y+1, board[yTest], yTest+1)) {
                    invalidEver = true;
                }
            }
            if (!invalidEver) {
                board[y] = x; //place piece
                if (y == board.length-1) {
                    solutionCount++; //found a solution
                    if(debug!=4) printBoard(board); 
                    if(printOne) System.exit(0);
                }
                else {
                   solutionCount+=checkLine(board, y+1); //check if future pieces can be placed
                }
            }
        }
        board[y] = 0; //undo placement
        debug("Finished line " + (y+1), 2);
        return solutionCount; //return number of solutions
    }

}
