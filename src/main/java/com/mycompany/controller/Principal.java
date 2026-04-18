package com.mycompany.controller;

import com.mycompany.conexion.ConexionDB;
import com.mycompany.dao.ProductoDAO;
import com.mycompany.dao.impl.ProductoDAOImpl;
import com.mycompany.model.Producto;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        ConexionDB.inicializarBaseDatos();
        ProductoDAO productoDAO = new ProductoDAOImpl();

        String menu = "=== MENÚ PRINCIPAL - BASE DE DATOS DE VENTAS ===\n" +
                "1. Listar productos\n" +
                "2. Insertar producto\n" +
                "3. Actualizar producto\n" +
                "4. Eliminar producto\n" +
                "5. Salir\n\n" +
                "Seleccione una opción:";

        int opcion = 0;
        while (opcion != 5) {
            String input = JOptionPane.showInputDialog(null, menu, "Aplicación de Ventas",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                break; // El usuario presionó Cancelar o cerró la ventana
            }

            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opción inválida. Ingrese un número.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                switch (opcion) {
                    case 1:
                        listarProductos(productoDAO);
                        break;
                    case 2:
                        insertarProducto(productoDAO);
                        break;
                    case 3:
                        actualizarProducto(productoDAO);
                        break;
                    case 4:
                        eliminarProducto(productoDAO);
                        break;
                    case 5:
                        JOptionPane.showMessageDialog(null, "Saliendo de la aplicación...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida. Seleccione entre 1 y 5.", "Error",
                                JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error en la operación: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void listarProductos(ProductoDAO productoDAO) {
        List<Producto> productos = productoDAO.listarProductos();
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados en el inventario.", "Inventario",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Inventario de Tienda ---\n\n");
        for (Producto p : productos) {
            sb.append("ID: ").append(p.getId())
                    .append(" | Nombre: ").append(p.getNombre())
                    .append(" | Precio: $").append(p.getPrecio())
                    .append(" | Cantidad: ").append(p.getCantidad()).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Lista de Productos", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void insertarProducto(ProductoDAO productoDAO) {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del nuevo producto:");
        if (nombre == null || nombre.trim().isEmpty())
            return;

        String precioStr = JOptionPane.showInputDialog("Ingrese el precio:");
        if (precioStr == null)
            return;
        double precio = Double.parseDouble(precioStr);

        String cantidadStr = JOptionPane.showInputDialog("Ingrese la cantidad en inventario:");
        if (cantidadStr == null)
            return;
        int cantidad = Integer.parseInt(cantidadStr);

        Producto producto = new Producto(nombre, precio, cantidad);
        productoDAO.insertarProducto(producto);
        JOptionPane.showMessageDialog(null, "Producto insertado con éxito!");
    }

    private static void actualizarProducto(ProductoDAO productoDAO) {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del producto que desea actualizar:");
        if (idStr == null)
            return;
        int id = Integer.parseInt(idStr);

        String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del producto:");
        if (nombre == null || nombre.trim().isEmpty())
            return;

        String precioStr = JOptionPane.showInputDialog("Ingrese el nuevo precio:");
        if (precioStr == null)
            return;
        double precio = Double.parseDouble(precioStr);

        String cantidadStr = JOptionPane.showInputDialog("Ingrese la nueva cantidad:");
        if (cantidadStr == null)
            return;
        int cantidad = Integer.parseInt(cantidadStr);

        Producto producto = new Producto(nombre, precio, cantidad);
        producto.setId(id);
        productoDAO.actualizarProducto(producto);
        JOptionPane.showMessageDialog(null, "Producto actualizado con éxito!");
    }

    private static void eliminarProducto(ProductoDAO productoDAO) {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del producto que desea eliminar:");
        if (idStr == null)
            return;
        int id = Integer.parseInt(idStr);

        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar el producto con ID " + id + "?", "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            productoDAO.eliminarProducto(id);
            JOptionPane.showMessageDialog(null, "Producto eliminado con éxito!");
        }
    }
}
