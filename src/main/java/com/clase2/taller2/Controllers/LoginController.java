package com.clase2.taller2.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clase2.taller2.Modelos.DAO.LoginDAO_Interface;
import com.clase2.taller2.Modelos.Entity.Login;
import com.clase2.taller2.Modelos.Enums.EstadoLogin;
import com.clase2.taller2.Modelos.Enums.Rol;

@Controller
public class LoginController {
    
    @Autowired
    private LoginDAO_Interface loginDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("login", new Login());
        model.addAttribute("titulo", "Registro de nuevo usuario");
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(Login login, Model model) {

        if (login.getCorreo() == null || login.getCorreo().trim().isEmpty()) {
            model.addAttribute("error", "El campo de correo no puede estar vacio.");
            model.addAttribute("titulo", "Registro de nuevo usuario");
            return "registro";
        }

        String correoLimpio = login.getCorreo().trim();

        if (!correoLimpio.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            model.addAttribute("error", "El correo no tiene un formato valido.");
            model.addAttribute("titulo", "Registro de nuevo usuario");
            return "registro";
        }

        login.setCorreo(correoLimpio);

        if (login.getContrasena() == null || login.getContrasena().length() < 6) {
            model.addAttribute("error", "La contrasena debe tener al menos 6 caracteres.");
            model.addAttribute("titulo", "Registro de nuevo usuario");
            return "registro";
        }

        if (loginDAO.findByCorreo(login.getCorreo()) != null) {
            model.addAttribute("error", "Ya existe una cuenta registrada con ese correo.");
            model.addAttribute("titulo", "Registro de nuevo usuario");
            return "registro";
        }

        login.setContrasena(passwordEncoder.encode(login.getContrasena()));
        login.setEstado(EstadoLogin.PENDIENTE);
        login.setRol(null);

        loginDAO.save(login);

        model.addAttribute("titulo", "Registro exitoso");
        return "registroExitoso";
    }

    @GetMapping("/Login/pendientes")
    public String pendientes(Model model) {
        List<Login> logins = loginDAO.findPendientes();
        model.addAttribute("logins", logins);
        model.addAttribute("titulo", "Cuentas pendientes de aprobacion");
        return "pendientes";
    }

    @PostMapping("/Login/activar/{id}")
    public String activar(@PathVariable Long id, @RequestParam String rol) {
        Login login = loginDAO.findOne(id);
        if (login != null) {
            login.setRol(Rol.valueOf(rol));
            login.setEstado(EstadoLogin.ACTIVO);
            loginDAO.save(login);
        }
        return "redirect:/Login/pendientes";
    }

    @GetMapping("/Login/listar")
    public String listar(Model model) {
        List<Login> logins = loginDAO.findAll();
        model.addAttribute("logins", logins);
        model.addAttribute("titulo", "Gestion de usuarios");
        return "listarUsuarios";
    }

    @PostMapping("/Login/cambiarRol/{id}")
    public String cambiarRol(@PathVariable Long id, @RequestParam String rol) {
        Login login = loginDAO.findOne(id);
        if (login != null) {
            login.setRol(Rol.valueOf(rol));
            loginDAO.save(login);
        }
        return "redirect:/Login/listar";
    }
}