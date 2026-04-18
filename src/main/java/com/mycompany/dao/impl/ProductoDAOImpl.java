package com.mycompany.dao.impl;

import com.mycompany.conexion.ConexionDB;
import com.mycompany.dao.ProductoDAO;
import com.mycompany.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {
    private static final String SQL_INSERT = "INSERT INTO `productos` (`nombre`, `precio`, `cantidad`) VALUES (?, ?, ?)";
    private static final String SQL_SELECT = "SELECT `id`, `nombre`, `precio`, `cantidad` FROM `productos`";
    private static final String SQL_UPDATE = "UPDATE `productos` SET `nombre` = ?, `precio` = ?, `cantidad` = ? WHERE `id` = ?";
    private static final String SQL_DELETE = "DELETE FROM `productos` WHERE `id` = ?";

    @Override
    public boolean insertarProducto(Producto producto) {
        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERT)) {
            sentencia.setString(1, producto.getNombre());
            sentencia.setDouble(2, producto.getPrecio());
            sentencia.setInt(3, producto.getCantidad());

            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                System.out.println("[INSERT] Producto insertado correctamente.");
                return true;
            }
            System.out.println("[INSERT] No se insertó ningún producto.");
        } catch (SQLException ex) {
            System.err.println("[INSERT] Error al insertar producto: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        try (Connection conexion = ConexionDB.getConnection();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(SQL_SELECT)) {

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                double precio = resultado.getDouble("precio");
                int cantidad = resultado.getInt("cantidad");
                productos.add(new Producto(id, nombre, precio, cantidad));
            }
            System.out.println("[SELECT] Consulta finalizada. Productos encontrados: " + productos.size());
        } catch (SQLException ex) {
            System.err.println("[SELECT] Error al consultar productos: " + ex.getMessage());
        }
        return productos;
    }

    @Override
    public boolean actualizarProducto(Producto producto) {
        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_UPDATE)) {
            sentencia.setString(1, producto.getNombre());
            sentencia.setDouble(2, producto.getPrecio());
            sentencia.setInt(3, producto.getCantidad());
            sentencia.setInt(4, producto.getId());

            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                System.out.println("[UPDATE] Producto actualizado correctamente.");
                return true;
            }
            System.out.println("[UPDATE] No se encontró el producto para actualizar.");
        } catch (SQLException ex) {
            System.err.println("[UPDATE] Error al actualizar producto: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean eliminarProducto(int id) {
        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_DELETE)) {
            sentencia.setInt(1, id);

            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                System.out.println("[DELETE] Producto eliminado correctamente.");
                return true;
            }
            System.out.println("[DELETE] No se encontró el producto para eliminar.");
        } catch (SQLException ex) {
            System.err.println("[DELETE] Error al eliminar producto: " + ex.getMessage());
        }
        return false;
    }
}
