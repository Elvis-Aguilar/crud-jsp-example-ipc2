package com.example1ipc2.app.aplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author elvis
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/db_crud_user?useSSL=false&serverTimezone=UTC";
    private static final String USER = "example1JSP";
    private static final String PASSWORD = "example123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC", e);
        }
    }

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
