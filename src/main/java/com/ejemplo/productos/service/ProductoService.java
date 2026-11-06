package com.ejemplo.productos.service;

import com.ejemplo.productos.model.Producto;
import com.ejemplo.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
Implementar el Service:
Aquí se aplican las reglas de negocio (validaciones, excepciones, filtros).
El Service llama al repositorio, nunca al revés.

Reglas de negocio (en el Service)

    El precio debe ser mayor que 0.
    El stock no puede ser negativo.
    El nombre no puede estar vacío.
    Si el id no existe, se debe devolver un error controlado (por ejemplo, con @ControllerAdvice).

*/

@Service
public class ProductoService {

    public static class ProductoNoEncontradoException extends RuntimeException {
        public ProductoNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository productoRepository) {
        this.repo = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return repo.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado."));
    }

    public Producto guardar(Producto producto) {
        validarProducto(producto);

        if (producto.getId() != null) {
            repo.findById(producto.getId())
                    .orElseThrow(() -> new ProductoNoEncontradoException(
                            "No se puede actualizar. El producto con ID " + producto.getId() + " no existe."));
        }

        return repo.save(producto);
    }

    public void eliminarPorId(Long id) {
        boolean eliminado = repo.deleteById(id);
        if (!eliminado) {
            throw new ProductoNoEncontradoException("No se puede eliminar. Producto con ID " + id + " no encontrado.");
        }
    }

    private void validarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0.");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
    }
}
