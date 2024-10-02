package com.example.webbshop.db;

import com.example.webbshop.db.DatabaseConnectionManager;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        boolean isConnected = DatabaseConnectionManager.testConnection();
        if (isConnected) {
            System.out.println("Connection test successful!");
        } else {
            System.out.println("Connection test failed!");
        }
    }
}
