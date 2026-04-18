package com.mycompany.dao;

import com.mycompany.model.Producto;
import java.util.List;

public interface ProductoDAO {
    boolean insertarProducto(Producto producto);
    List<Producto> listarProductos();
    boolean actualizarProducto(Producto producto);
    boolean eliminarProducto(int id);
}
