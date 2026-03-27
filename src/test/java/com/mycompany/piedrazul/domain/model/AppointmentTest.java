package com.mycompany.piedrazul.domain.model;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {

    
   //Prueba1
    @Test
    public void deberiaAsignarYObtenerPacienteCorrectamente() {
        Appointment appointment = new Appointment();
        Usuario paciente = new Usuario();
        paciente.setId(10);

        appointment.setPatient(paciente);

        assertNotNull(appointment.getPatient());
        assertEquals(10, appointment.getPatient().getId());
    }

   //Prueba2
    @Test
    public void deberiaAsignarYObtenerProfesionalCorrectamente() {
        Appointment appointment = new Appointment();
        Usuario profesional = new Usuario();
        profesional.setId(20);

        appointment.setProfessional(profesional);

        assertNotNull(appointment.getProfessional());
        assertEquals(20, appointment.getProfessional().getId());
    }

   //Prueba3
    @Test
    public void deberiaAsignarYObtenerFechaHoraCorrectamente() {
        Appointment appointment = new Appointment();
        LocalDateTime fechaHora = LocalDateTime.of(2026, 4, 10, 9, 30);

        appointment.setDateTime(fechaHora);

        assertNotNull(appointment.getDateTime());
        assertEquals(fechaHora, appointment.getDateTime());
    }

   //Prueba4
    @Test
    public void deberiaAsignarYObtenerUsuarioCreadorCorrectamente() {
        Appointment appointment = new Appointment();
        Usuario creador = new Usuario();
        creador.setId(5);

        appointment.setCreatedBy(creador);

        assertNotNull(appointment.getCreatedBy());
        assertEquals(5, appointment.getCreatedBy().getId());
    }

   //Prueba5
    @Test
    public void deberiaAsignarYObtenerEstadoCorrectamente() {
        Appointment appointment = new Appointment();

        appointment.setStatus(AppointmentStatus.PROGRAMADA);

        assertEquals(AppointmentStatus.PROGRAMADA, appointment.getStatus());
    }

   
}