/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.piedrazul.infrastructure.persistence.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author asus
 */
public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/piedra-azul_db";
    private static final String USER = "piedra-azul";
    private static final String PASSWORD = "db_piedra-azul";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error conectando a la BD: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}