package com.clase2.taller2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.clase2.taller2.Modelos.DAO.LoginDAO_Interface;
import com.clase2.taller2.Modelos.Entity.Login;
import com.clase2.taller2.Modelos.Enums.EstadoLogin;

@SpringBootTest
class Taller2ApplicationTests {

	@Autowired
	private LoginDAO_Interface loginDAO;

	@Test
	void contextLoads() {
	}

	@Test
	void loginShouldStoreCorreoAndContrasena() {
		Login login = new Login();
		login.setCorreo("usuario@ejemplo.com");
		login.setContrasena("123456");

		assertEquals("usuario@ejemplo.com", login.getCorreo());
		assertEquals("123456", login.getContrasena());
	}

	@Test
	void loginRepositoryShouldFindByCorreo() {
		Login login = new Login();
		login.setCorreo("repo@ejemplo.com");
		login.setContrasena("123456");
		login.setEstado(EstadoLogin.PENDIENTE);

		loginDAO.save(login);
		Login encontrado = loginDAO.findByCorreo("repo@ejemplo.com");

		assertNotNull(encontrado);
		assertEquals("repo@ejemplo.com", encontrado.getCorreo());
	}

}
