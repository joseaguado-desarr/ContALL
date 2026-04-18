package com.mycompany.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_2026?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    private ConexionDB() {
        // Clase de utilidad, no se instancia
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("[CONEXION] Driver MySQL no encontrado: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    public static void inicializarBaseDatos() {
        try (Connection conexion = getConnection()) {
            crearTablaProductos(conexion);
        } catch (SQLException ex) {
            System.err.println("[CONEXION] Error al inicializar la base de datos: " + ex.getMessage());
        }
    }

    private static void crearTablaProductos(Connection conexion) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS `productos` ("
                + "`id` INT AUTO_INCREMENT PRIMARY KEY, "
                + "`nombre` VARCHAR(255) NOT NULL, "
                + "`precio` DOUBLE NOT NULL, "
                + "`cantidad` INT NOT NULL"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.execute();
            System.out.println("[CONEXION] Tabla 'productos' verificada/creada exitosamente.");
        }
    }
}
