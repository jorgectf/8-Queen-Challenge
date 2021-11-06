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
        if(size == 0) {
            System.out.println("Warning: this tests every solution for boards from 3x3 to 20x20, please stop program if your computer heats up!");
            for(int i = 3; i <= 20; i++) {
                startboard = new int[i];
                solutions = checkLine(startboard,0);
                System.out.println("Board Size: " + i + " Number of Solutions: " + solutions);
            }
        }
        else {
            startboard = new int[size];
            solutions = checkLine(startboard,0);
            System.out.println("Found " + solutions + " solutions!");
        }
    }

    public static void debug(String message, int debugLevel) {
        if (debugLevel == debug || debug == 3) {
            System.out.println(message);
        }
    }

    public static void printBoard(int[] board){
/*
        System.out.println("board:");
        for(int i = 0; i < board.length; i++){
            System.out.print(board[i] + " ");
        }
        System.out.println();
*/
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
/*        System.out.println("board:");
        for(int y = 0; y < board.length; y++){
            for(int x = 1; x <= board.length; x++){
                if(board[y] == x) {
                    System.out.print("X ");
                }
                else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
*/
        //┌───┬───┬───┬───┬───┬───┬───┬───┐
        System.out.print("┌");
        for(int i = 0; i < board.length-1; i++){
            System.out.print("───┬");
        }
        System.out.println("───┐");
        //│ x │ . │ x │ . │   │   │   │   │
        for(int y = 0; y < board.length; y++){
            System.out.print("│");
            for(int x = 1; x <= board.length; x++){
                if(board[y] == x) {
                    System.out.print(" X ");
                }
                else {
                    System.out.print(" . ");
                }
                System.out.print("│");
            }
            System.out.println();
            if(y != board.length-1){
                //├───┼───┼───┼───┼───┼───┼───┼───┤
                System.out.print("├");
                for (int i = 0; i < board.length-1; i++) {
                    System.out.print("───┼");
                }
                System.out.println("───┤");
            }
            else {
                //└───┴───┴───┴───┴───┴───┴───┴───┘
                System.out.print("└");
                for (int i = 0; i < board.length-1; i++) {
                    System.out.print("───┴");
                }
                System.out.println("───┘");
            }
        }

    }

    public static boolean checkValid(int x1, int y1, int x2, int y2) {
        debug("Checking validity of " + x1 + " " + y1 + " " + x2 + " " + y2, 1);
        //check the validity between 2 pieces and see if they attack eachother (directly above/below or diagonal)
        if (x1 == x2) {
            debug("same line", 1);
            return false;
        }
        else if ((y2-y1)==(x2-x1) || (y2-y1)==-(x2-x1)) {
            debug("diagonal", 1);
            return false;
        }
        else {
            debug("valid move", 1);
            return true;
        }
    }

    public static int checkLine(int board[], int y) {
        int solutionCount = 0;
        debug("Checking line " + (y+1), 2);
        if (debug==2 || debug==3) printBoard(board);
        for (int x = 1; x <= board.length; x++) { 
            debug("x: " + x + " y: " + (y+1), 2);
            boolean invalidEver = false;
            for (int yTest = 0; yTest < y; yTest++) {
                if (!checkValid(x, y+1, board[yTest], yTest+1)) {
                    invalidEver = true;
                }
            }
            if (!invalidEver) {
                board[y] = x;
                if (y == board.length-1) {
                    solutionCount++;
                    if(debug!=4) printBoard(board);
                    if(printOne) System.exit(0);
                }
                else {
                   solutionCount+=checkLine(board, y+1);
                }
            }
        }
        board[y] = 0;
        debug("Finished line " + (y+1), 2);
        return solutionCount;
    }

}
