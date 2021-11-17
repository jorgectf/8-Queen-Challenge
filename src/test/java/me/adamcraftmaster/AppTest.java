package me.adamcraftmaster;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest 
{
    /**
     * Unit test for checking if the checkValid function works with both functional and non functional values.
    */
    @Test
    public void checkValidTest() {
        App tester = new App();
        //tester.debug = 1;
        for(int x = 1; x <= 8; x++) { //for each of the 8 possible values
            //System.out.println("Testing " + x);
            if(x==2) {
                //System.out.println(tester.checkValid(2,1, x,8));
                assertFalse(tester.checkValid(2,1, x,8)); //if the value is 2, it should be invalid
            }
            else {
                //System.out.println(tester.checkValid(2,1, x,8));
                assertTrue(tester.checkValid(2,1, x,8)); //if the value is not 2, it should be valid
            }
        }
    }
    /**
     * Unit test for checking if the checkLine function works with different board sizes.
    */
    @Test
    public void checkLineSolutions() {
        //https://en.wikipedia.org/wiki/Eight_queens_puzzle#Counting_solutions
        //! WARNING: the following test will be simulating all boards from a size of 3x3 to a size of 9x9, if you experience lag, please end the test.
        System.out.println("WARNING: the following test will be simulating all boards from a size of 3x3 to a size of 9x9, if you experience lag, please end the test.");
        App tester = new App();

        tester.debug = 4;

        int[] startboard3 = {0,0,0}; 
        assertTrue(tester.checkLine(startboard3,0) == 0); //3x3 has no solutions
        int[] startboard4 = {0,0,0,0};
        assertTrue(tester.checkLine(startboard4,0) == 2); //4x4 has 2 solutions
        int[] startboard5 = {0,0,0,0,0};
        assertTrue(tester.checkLine(startboard5,0) == 10); //5x5 has 10 solutions
        int[] startboard6 = {0,0,0,0,0,0};
        assertTrue(tester.checkLine(startboard6,0) == 4); //6x6 has 4 solutions
        int[] startboard7 = {0,0,0,0,0,0,0};
        assertTrue(tester.checkLine(startboard7,0) == 40); //7x7 has 40 solutions
        int[] startboard8 = {0,0,0,0,0,0,0,0};
        assertTrue(tester.checkLine(startboard8,0) == 92); //8x8 has 92 solutions
        int[] startboard9 = {0,0,0,0,0,0,0,0,0};
        assertTrue(tester.checkLine(startboard9,0) == 352); //9x9 has 352 solutions
    }
}
