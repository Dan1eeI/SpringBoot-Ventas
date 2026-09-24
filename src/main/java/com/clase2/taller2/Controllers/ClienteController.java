package com.clase2.taller2.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clase2.taller2.Modelos.DAO.ClienteDAO_Interface;
import com.clase2.taller2.Modelos.Entity.Cliente;
import com.clase2.taller2.Modelos.Enums.Rol;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Cliente")
public class ClienteController {

    private final ClienteDAO_Interface clienteDAO;

    ClienteController(ClienteDAO_Interface clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @GetMapping("/listar")
    public String listar(Model model, HttpSession session) {
        Rol rol = (Rol) session.getAttribute("rol");
        List<Cliente> clientes;

        if (rol == Rol.ADMIN) {
            clientes = clienteDAO.findAll();
            model.addAttribute("titulo", "Listado de Clientes");
        } else {
            Long idCliente = (Long) session.getAttribute("idCliente");
            Cliente propio = clienteDAO.findOne(idCliente);
            clientes = new ArrayList<>();
            if (propio != null) {
                clientes.add(propio);
            }
            model.addAttribute("titulo", "Mi perfil");
        }

        model.addAttribute("clientes", clientes);
        return "listar";
    }

    @GetMapping("/form")
    public String crear(Model model, HttpSession session) {
        Cliente cliente = new Cliente();
        cliente.setEmail((String) session.getAttribute("correo"));
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Formulario de Cliente");
        return "form";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
        Rol rol = (Rol) session.getAttribute("rol");
        Long idPermitido = id;

        if (rol != Rol.ADMIN) {
            idPermitido = (Long) session.getAttribute("idCliente");
        }

        if (idPermitido == null || idPermitido <= 0) {
            return "redirect:/Cliente/listar";
        }

        Cliente cliente = clienteDAO.findOne(idPermitido);
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "form";
    }

    @PostMapping("/form")
    public String guardar(Cliente cliente, HttpSession session) {
        Rol rol = (Rol) session.getAttribute("rol");
        String correoSesion = (String) session.getAttribute("correo");

        if (rol != Rol.ADMIN) {
            Long idPropio = (Long) session.getAttribute("idCliente");
            cliente.setId(idPropio);
            cliente.setEmail(correoSesion);
        }

        if (cliente.getCreateAt() == null) {
            cliente.setCreateAt(new Date());
        }

        clienteDAO.save(cliente);

        if (rol != Rol.ADMIN) {
            Cliente actualizado = clienteDAO.findByEmail(correoSesion);
            session.setAttribute("idCliente", actualizado.getId());
        }

        return "redirect:/Cliente/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, HttpSession session) {
        Rol rol = (Rol) session.getAttribute("rol");
        if (rol == Rol.ADMIN && id > 0) {
            clienteDAO.delete(id);
        }
        return "redirect:/Cliente/listar";
    }
}