package me.adamcraftmaster;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test for checking if the checkValid function works with both functional and non functional values.
 */
public class AppTest 
{
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
}
