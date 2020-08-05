package com.amos.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Amos
 * @date 8/5/2020 6:39 PM
 */
public class DBUtil {
    private static final String host = "localhost";
    private static final  int port = 3306;
    private static final String database = "test";
    private static final String username = "root";
    private static final String password = "root";
    private static final String encoding = "utf-8";
    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s",host,port,database,encoding);
        return DriverManager.getConnection(url,username,password);
    }
}
