package com.clase2.taller2.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.clase2.taller2.Modelos.DAO.LoginDAO_Interface;
import com.clase2.taller2.Modelos.Entity.Login;
import com.clase2.taller2.Modelos.Enums.EstadoLogin;
import com.clase2.taller2.Modelos.Enums.Rol;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner crearAdminInicial(LoginDAO_Interface loginDAO, PasswordEncoder passwordEncoder) {
        return args -> {
            if (loginDAO.findByCorreo("admin@taller2.com") == null) {
                Login admin = new Login();
                admin.setCorreo("admin@taller2.com");
                admin.setContrasena(passwordEncoder.encode("admin123"));
                admin.setRol(Rol.ADMIN);
                admin.setEstado(EstadoLogin.ACTIVO);
                loginDAO.save(admin);
            }
        };
    }
}