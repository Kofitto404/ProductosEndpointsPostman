package com.ejemplo.productos.repository;

import com.ejemplo.productos.model.Producto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/* 
Implementar el repositorio en el paquete repository.
Usando la plantilla de la sección anterior.
Es la única clase que manipula la List.
 */

@Repository
public class ProductoRepository {

    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public List<Producto> findAll() {
        return new ArrayList<>(productos);
    }

    public Optional<Producto> findById(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Producto save(Producto p) {
        if (p.getId() == null) {

            p.setId(contadorId.getAndIncrement());
            productos.add(p);
        } else {
            this.findById(p.getId()).ifPresent(existente -> {
                existente.setNombre(p.getNombre());
                existente.setPrecio(p.getPrecio());
                existente.setStock(p.getStock());
            });
        }
        return p;
    }

    public boolean deleteById(Long id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}