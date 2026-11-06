package com.ejemplo.productos.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/* 
Definir el modelo en el paquete model:
atributos, constructor vacío, getters y setters.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
}
