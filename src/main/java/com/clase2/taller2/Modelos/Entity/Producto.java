package com.clase2.taller2.Modelos.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String Nombre, Descripcion;
    private Double Precio;
    private Integer Stock;

    @Column(name = "create_at")
    private Date CreateAt;

    public Producto() {
    }

    public Producto(Long id, String nombre, String descripcion, Double precio, Integer stock, Date createAt) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        Precio = precio;
        Stock = stock;
        CreateAt = createAt;
    }

    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
    public Double getPrecio() {
        return Precio;
    }
    public void setPrecio(Double precio) {
        Precio = precio;
    }
    public Integer getStock() {
        return Stock;
    }
    public void setStock(Integer stock) {
        Stock = stock;
    }
    public Date getCreateAt() {
        return CreateAt;
    }
    public void setCreateAt(Date createAt) {
        CreateAt = createAt;
    }
}
