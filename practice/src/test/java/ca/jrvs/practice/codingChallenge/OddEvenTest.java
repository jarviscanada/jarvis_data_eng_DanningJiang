package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class OddEvenTest {

    @Test
    public void oddEvenModTest() {
        OddEven oddEven = new OddEven();
        //System.out.println(obj.oddEvenMod(1));
        String result = oddEven.oddEvenMod(1);
        assertEquals("odd",result);

    }
}