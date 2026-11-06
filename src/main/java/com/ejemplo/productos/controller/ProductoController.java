package com.ejemplo.productos.controller;

import com.ejemplo.productos.model.Producto;
import com.ejemplo.productos.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
Implementar el Controller con los endpoints de la tabla.
Usar ResponseEntity para controlar los códigos de estado (200, 201, 204, 400, 404, 409).

Método  |      Ruta 	        |      Descripción
GET	    |  /api/productos	    |   Lista todos los productos
GET	    |  /api/productos/{id}  |   Busca por id (404 si no existe)
POST    |  /api/productos	    |   Crea un producto (201)
PUT	    |  /api/productos/{id}  |   Actualiza (200 / 404)
DELETE	|  /api/productos/{id}	|   Elimina (204 / 404)

*/

/*
==============

GET http://localhost:8080/api/productos

==============

GET{ID} http://localhost:8080/api/productos/1

==========

POST

{
  "nombre": "Café Capuchino",
  "precio": 2500,
  "stock": 123
}

============

PUT http://localhost:8080/api/productos/1

{
  "nombre": "Café Capuchino",
  "precio": 2100,
  "stock": 10
}

============

DELETE http://localhost:8080/api/productos/1

*/

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        producto.setId(null);
        Producto nuevoProducto = productoService.guardar(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto productoActualizado = productoService.guardar(producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
