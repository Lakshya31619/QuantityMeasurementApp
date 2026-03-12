package com.app.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool() {

        try {

            int size = Integer.parseInt(
                ApplicationConfig.getProperty("db.pool.size"));

            for(int i=0;i<size;i++){

                Connection conn = DriverManager.getConnection(
                    ApplicationConfig.getProperty("db.url"),
                    ApplicationConfig.getProperty("db.username"),
                    ApplicationConfig.getProperty("db.password"));

                pool.add(conn);
            }

        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public synchronized Connection getConnection(){
        return pool.removeFirst();
    }

    public synchronized void releaseConnection(Connection conn){
        pool.addLast(conn);
    }
}