package com.codemo.www.wifiseeker.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by root on 5/16/17.
 */
public class WifiNetworkTest {
    @Test
    public void setData() throws Exception {

    }

    @Test
    public void setOpenData() throws Exception {

    }

    @Test
    public void getData() throws Exception {

    }

    @Test
    public void getOpenData() throws Exception {

    }

    @Test
    public void getAuth() throws Exception {

    }

    @Test
    public void getSignal() throws Exception {
        Integer input = -50;
        String output;
        String expected = "Excellent";

        output =WifiNetwork.getSignal(input);
        assertEquals(expected,output);

    }

    @Test
    public void autoConnection() throws Exception {

    }

    @Test
    public void getName() throws Exception {

    }

    @Test
    public void getOpenName() throws Exception {

    }

    @Test
    public void getCapabilities() throws Exception {

    }

    @Test
    public void getOpenCapabilities() throws Exception {

    }

    @Test
    public void getOpenLevel() throws Exception {

    }

    @Test
    public void getLevel() throws Exception {

    }

    @Test
    public void setActivity() throws Exception {

    }

    @Test
    public void getPassword() throws Exception {

    }

    @Test
    public void setPassword() throws Exception {

    }

}