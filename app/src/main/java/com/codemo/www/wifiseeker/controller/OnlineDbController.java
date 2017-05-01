package com.codemo.www.wifiseeker.controller;

/**
 * Created by root on 5/1/17.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
public class OnlineDbController {


    String hostName = "your_server.database.windows.net";
    String dbName = "your_database";
    String user = "your_username";
    String password = "your_password";
    String url = String.format("jdbc:sqlserver://%s.database.windows.net:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
    Connection connection = null;




}
