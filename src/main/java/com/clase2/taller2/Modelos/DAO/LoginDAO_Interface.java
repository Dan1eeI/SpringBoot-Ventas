package com.clase2.taller2.Modelos.DAO;

import java.util.List;
import com.clase2.taller2.Modelos.Entity.Login;

public interface LoginDAO_Interface {
    List<Login> findAll();

    List<Login> findPendientes();

    Login FindByCorreo(String correo);

    void save(Login login);

    Login findOne(Long id);

    void delete(Long id);

    Login findByCorreo(String correo);


}
