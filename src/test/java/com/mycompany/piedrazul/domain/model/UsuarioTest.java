package com.mycompany.piedrazul.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

   //Prueba1
    @Test
    public void deberiaAsignarYObtenerUsernameCorrectamente() {
        Usuario usuario = new Usuario();

        usuario.setUsername("almagozo");

        assertEquals("almagozo", usuario.getUsername());
    }

   //Prueba2
    @Test
    public void deberiaAsignarYObtenerPasswordCorrectamente() {
        Usuario usuario = new Usuario();

        usuario.setPasswordHash("Clave123");

        assertEquals("Clave123", usuario.getPasswordHash());
    }

   //Prueba3
    @Test
    public void deberiaAsignarYObtenerEstadoActivoCorrectamente() {
        Usuario usuario = new Usuario();

        usuario.setActivo(true);

        assertTrue(usuario.isActivo());
    }

   //Prueba4
    @Test
    public void deberiaAsignarYObtenerRolCorrectamente() {
        Usuario usuario = new Usuario();

        usuario.setRol(Rol.PACIENTE);

        assertEquals(Rol.PACIENTE, usuario.getRol());
    }

   //Prueba5
    @Test
    public void deberiaAsignarYObtenerPersonaIdCorrectamente() {
        Usuario usuario = new Usuario();

        usuario.setPersonaId(15);

        assertEquals(15, usuario.getPersonaId());
    }

   //Prueba6
    @Test
    public void deberiaAsignarYObtenerIntentosFallidosCorrectamente() {
        Usuario usuario = new Usuario();

        usuario.setIntentosFallidos(3);

        assertEquals(3, usuario.getIntentosFallidos());
    }
}