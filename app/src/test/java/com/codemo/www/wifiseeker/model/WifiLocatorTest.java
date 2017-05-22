package com.codemo.www.wifiseeker.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by root on 5/16/17.
 */
public class WifiLocatorTest {
    @Test
    public void setActivity() throws Exception {

    }

    @Test
    public void setRange() throws Exception {

    }

    @Test
    public void getWifi() throws Exception {

    }

    @Test
    public void getInRangeList() throws Exception {

    }

    @Test
    public void getScoredList() throws Exception {

    }

    @Test
    public void getSortedList() throws Exception {

        ArrayList<Double[]> input =new ArrayList<>();
        Double[] a= {1.0,25.0};
        Double[] b= {2.0,50.0};
        Double[] c= {3.0,40.0};

        input.add(a);
        input.add(b);
        input.add(c);

        ArrayList<Double[]> expected =new ArrayList<>();
        Double[] p= {2.0,50.0};
        Double[] q= {3.0,40.0};
        Double[] r= {1.0,25.0};

        expected.add(a);
        expected.add(b);
        expected.add(c);

        ArrayList<Double[]> output;

        output =new WifiLocator().getSortedList(input);
        assertEquals(expected,output);





    }

    @Test
    public void getResult() throws Exception {

    }

    @Test
    public void getFullList() throws Exception {

    }

    @Test
    public void setFullList() throws Exception {

    }

}