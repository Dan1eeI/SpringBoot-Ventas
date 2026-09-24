package com.clase2.taller2.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.clase2.taller2.Modelos.Enums.Rol;

@Component
public class SesionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        Object idLogin = (session != null) ? session.getAttribute("idLogin") : null;

        if (idLogin == null) {
            response.sendRedirect("/login");
            return false;
        }

        String uri = request.getRequestURI();
        Rol rol = (Rol) session.getAttribute("rol");

        if (esRutaDeAdmin(uri) && rol != Rol.ADMIN) {
            response.sendRedirect("/Cliente/listar");
            return false;
        }

        if (rol == Rol.CLIENTE) {
            Object idCliente = session.getAttribute("idCliente");
            boolean rutaPermitidaSinPerfil = uri.equals("/Cliente/form") || uri.equals("/logout");

            if (idCliente == null && !rutaPermitidaSinPerfil) {
                response.sendRedirect("/Cliente/form");
                return false;
            }
        }

        return true;
    }

    private boolean esRutaDeAdmin(String uri) {
        return uri.startsWith("/Login/pendientes")
            || uri.startsWith("/Login/listar")
            || uri.startsWith("/Login/activar")
            || uri.startsWith("/Login/cambiarRol")
            || uri.startsWith("/Producto/form")
            || uri.startsWith("/Producto/eliminar")
            || uri.startsWith("/Cliente/eliminar");
    }
}