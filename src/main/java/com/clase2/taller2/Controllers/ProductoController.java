package com.clase2.taller2.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clase2.taller2.Modelos.DAO.ProductoDAO_Interface;
import com.clase2.taller2.Modelos.Entity.Producto;

@Controller
@RequestMapping("/Producto")
public class ProductoController {

    @Autowired
    private ProductoDAO_Interface productoDAO;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Productos");
        List<Producto> productos = productoDAO.findAll();
        model.addAttribute("productos", productos);
        return "listarProducto";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Formulario de Producto");
        return "formProducto";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model) {
        Producto producto = null;
        if (id > 0) {
            producto = productoDAO.findOne(id);
        } else {
            return "redirect:/Producto/listar";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        return "formProducto";
    }

    @PostMapping("/form")
    public String guardar(Producto producto) {
        if (producto.getCreateAt() == null) {
            producto.setCreateAt(new Date());
        }
        productoDAO.save(producto);
        return "redirect:/Producto/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            productoDAO.delete(id);
        }
        return "redirect:/Producto/listar";
    }
}
