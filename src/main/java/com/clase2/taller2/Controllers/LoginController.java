package com.clase2.taller2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.clase2.taller2.Modelos.DAO.LoginDAO_Interface;
import com.clase2.taller2.Modelos.Entity.Login;
import com.clase2.taller2.Modelos.Enums.EstadoLogin;

@Controller
@SuppressWarnings ("all")
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

    // Validacion 1: formato de correo con regex (por fin el viejo gildardo aparece)
    if (login.getCorreo() == null || login.getCorreo().trim().isEmpty()) {
    model.addAttribute("error", "El campo de correo no puede estar vacío.");
    model.addAttribute("titulo", "Registro de nuevo usuario");
    return "registro";
    }

    String correoLimpio = login.getCorreo().trim();

    // Regex estándar RFC 5322 para correos electrónicos
    if (!correoLimpio.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
    model.addAttribute("error", "El correo no tiene un formato valido.");
    model.addAttribute("titulo", "Registro de nuevo usuario");
    return "registro";
    }

login.setCorreo(correoLimpio);

        // Validacion 2: contrasena minima
        if (login.getContrasena() == null || login.getContrasena().length() < 6) {
            model.addAttribute("error", "La contrasena debe tener al menos 6 caracteres.");
            model.addAttribute("titulo", "Registro de nuevo usuario");
            return "registro";
        }

        // Validacion 3: correo no debe existir ya
        if (loginDAO.findByCorreo(login.getCorreo()) != null) {
            model.addAttribute("error", "Ya existe una cuenta registrada con ese correo.");
            model.addAttribute("titulo", "Registro de nuevo usuario");
            return "registro";
        }

        // Todo valido: ciframos la contrasena antes de guardar
        login.setContrasena(passwordEncoder.encode(login.getContrasena()));
        login.setEstado(EstadoLogin.PENDIENTE);
        login.setRol(null); // el rol lo asigna el admin, todavia no existe

        loginDAO.save(login);

        model.addAttribute("titulo", "Registro exitoso");
        return "registroExitoso";
    }
}