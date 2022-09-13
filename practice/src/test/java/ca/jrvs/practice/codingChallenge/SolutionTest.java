package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void compareMapsTest() {
        Map<Integer,String> m1 = new HashMap<>();
        Map<Integer,String> m2 = new HashMap<>();
        Map<Integer,String> m3 = new HashMap<>();
        m1.put(1,"a");
        m1.put(2,"b");
        m2.put(1,"a");
        m2.put(2,"b");
        m3.put(1,"a");
        m3.put(3,"b");
        assertTrue("compare two same maps",Solution.compareMaps(m1,m2));
        assertFalse("compare two different maps",Solution.compareMaps(m1,m3));

    }
    @Test
    public void fibonacciTest(){
        assertEquals(8,Solution.fibonacci(6));
    }
}