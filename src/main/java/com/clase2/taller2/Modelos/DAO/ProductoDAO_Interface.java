package com.clase2.taller2.Modelos.DAO;

import java.util.List;

import com.clase2.taller2.Modelos.Entity.Producto;

// Esta interfaz define los metodos que se van a utilizar para la interacción con la base de datos
public interface ProductoDAO_Interface {

    public List<Producto> findAll();

    public void save(Producto producto);

    public Producto findOne(Long id);

    public void delete(Long id);

}
