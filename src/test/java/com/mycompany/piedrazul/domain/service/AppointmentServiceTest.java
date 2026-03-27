package com.mycompany.piedrazul.domain.service;

import com.mycompany.piedrazul.domain.model.Appointment;
import com.mycompany.piedrazul.domain.model.Usuario;
import com.mycompany.piedrazul.domain.repository.IAppointmentRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private IAppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;
    
    
   //Prueba1
    @Test
public void createSelfServiceAppointment_deberiaLanzarExcepcion_siUsuarioAutenticadoEsNull() {
    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createSelfServiceAppointment(null, null)
    );

    assertEquals("Debe iniciar sesion para agendar la cita.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba2
@Test
public void createSelfServiceAppointment_deberiaLanzarExcepcion_siCitaEsNull() {
    Usuario usuario = new Usuario();
    usuario.setId(1);

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createSelfServiceAppointment(null, usuario)
    );

    assertEquals("La cita no puede ser nula.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba3
@Test
public void createManualAppointment_deberiaLanzarExcepcion_siCitaEsNull() {
    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createManualAppointment(null)
    );

    assertEquals("La cita no puede ser nula.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba4
@Test
public void createSelfServiceAppointment_deberiaLanzarExcepcion_siPacienteEsInvalido() {
    Appointment appointment = new Appointment();
    appointment.setPatient(null);

    Usuario usuario = new Usuario();
    usuario.setId(1);

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createSelfServiceAppointment(appointment, usuario)
    );

    assertEquals("Paciente invalido.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba5
@Test
public void createSelfServiceAppointment_deberiaLanzarExcepcion_siProfesionalEsInvalido() {
    Appointment appointment = new Appointment();

    Usuario paciente = new Usuario();
    paciente.setId(10);
    appointment.setPatient(paciente);
    appointment.setProfessional(null);

    Usuario usuario = new Usuario();
    usuario.setId(1);

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createSelfServiceAppointment(appointment, usuario)
    );

    assertEquals("Profesional invalido.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba6
@Test
public void createSelfServiceAppointment_deberiaLanzarExcepcion_siFechaHoraEsNula() {
    Appointment appointment = new Appointment();

    Usuario paciente = new Usuario();
    paciente.setId(10);

    Usuario profesional = new Usuario();
    profesional.setId(20);

    appointment.setPatient(paciente);
    appointment.setProfessional(profesional);
    appointment.setDateTime(null);

    Usuario usuario = new Usuario();
    usuario.setId(1);

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createSelfServiceAppointment(appointment, usuario)
    );

    assertEquals("La fecha y hora son obligatorias.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba7
@Test
public void createSelfServiceAppointment_deberiaLanzarExcepcion_siFechaEsPasada() {
    Appointment appointment = new Appointment();

    Usuario paciente = new Usuario();
    paciente.setId(10);

    Usuario profesional = new Usuario();
    profesional.setId(20);

    appointment.setPatient(paciente);
    appointment.setProfessional(profesional);
    appointment.setDateTime(java.time.LocalDateTime.now().minusDays(1));

    Usuario usuario = new Usuario();
    usuario.setId(1);

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createSelfServiceAppointment(appointment, usuario)
    );

    assertEquals("No se puede agendar una cita en una fecha u hora pasada.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba8
@Test
public void createSelfServiceAppointment_deberiaLanzarExcepcion_siHorarioYaNoEstaDisponible() {
    Appointment appointment = new Appointment();

    Usuario paciente = new Usuario();
    paciente.setId(10);

    Usuario profesional = new Usuario();
    profesional.setId(20);

    appointment.setPatient(paciente);
    appointment.setProfessional(profesional);
    appointment.setDateTime(java.time.LocalDateTime.now().plusDays(1));

    Usuario usuario = new Usuario();
    usuario.setId(1);

    when(appointmentRepository.existsByProfessionalAndDateTime(20, appointment.getDateTime())).thenReturn(true);

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createSelfServiceAppointment(appointment, usuario)
    );

    assertEquals("El horario ya no esta disponible.", excepcion.getMessage());
    verify(appointmentRepository).existsByProfessionalAndDateTime(20, appointment.getDateTime());
    verify(appointmentRepository, never()).save(any());
}

//Prueba9
@Test
public void createSelfServiceAppointment_deberiaGuardarCita_siDatosSonCorrectos() {
    Appointment appointment = new Appointment();

    Usuario paciente = new Usuario();
    paciente.setId(10);

    Usuario profesional = new Usuario();
    profesional.setId(20);

    Usuario usuarioAutenticado = new Usuario();
    usuarioAutenticado.setId(1);

    appointment.setPatient(paciente);
    appointment.setProfessional(profesional);
    appointment.setDateTime(java.time.LocalDateTime.now().plusDays(1));

    when(appointmentRepository.existsByProfessionalAndDateTime(20, appointment.getDateTime())).thenReturn(false);
    when(appointmentRepository.save(appointment)).thenReturn(appointment);

    Appointment resultado = appointmentService.createSelfServiceAppointment(appointment, usuarioAutenticado);

    assertNotNull(resultado);
    assertEquals(usuarioAutenticado, appointment.getCreatedBy());
    assertEquals(com.mycompany.piedrazul.domain.model.AppointmentStatus.PROGRAMADA, appointment.getStatus());
    verify(appointmentRepository).existsByProfessionalAndDateTime(20, appointment.getDateTime());
    verify(appointmentRepository).save(appointment);
}

//Prueba10
@Test
public void createManualAppointment_deberiaLanzarExcepcion_siUsuarioCreadorEsInvalido() {
    Appointment appointment = new Appointment();

    Usuario paciente = new Usuario();
    paciente.setId(10);

    Usuario profesional = new Usuario();
    profesional.setId(20);

    Usuario creador = new Usuario();
    creador.setId(0);

    appointment.setPatient(paciente);
    appointment.setProfessional(profesional);
    appointment.setCreatedBy(creador);
    appointment.setDateTime(java.time.LocalDateTime.now().plusDays(1));

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createManualAppointment(appointment)
    );

    assertEquals("El usuario que crea la cita es obligatorio.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba11
@Test
public void createManualAppointment_deberiaLanzarExcepcion_siHorarioYaEstaOcupado() {
    Appointment appointment = new Appointment();

    Usuario paciente = new Usuario();
    paciente.setId(10);

    Usuario profesional = new Usuario();
    profesional.setId(20);

    Usuario creador = new Usuario();
    creador.setId(5);

    appointment.setPatient(paciente);
    appointment.setProfessional(profesional);
    appointment.setCreatedBy(creador);
    appointment.setDateTime(java.time.LocalDateTime.now().plusDays(1));

    when(appointmentRepository.existsByProfessionalAndDateTime(20, appointment.getDateTime())).thenReturn(true);

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.createManualAppointment(appointment)
    );

    assertEquals("El horario ya esta ocupado.", excepcion.getMessage());
    verify(appointmentRepository).existsByProfessionalAndDateTime(20, appointment.getDateTime());
    verify(appointmentRepository, never()).save(any());
}

//Prueba12
@Test
public void createManualAppointment_deberiaGuardarCita_siDatosSonCorrectos() {
    Appointment appointment = new Appointment();

    Usuario paciente = new Usuario();
    paciente.setId(10);

    Usuario profesional = new Usuario();
    profesional.setId(20);

    Usuario creador = new Usuario();
    creador.setId(5);

    appointment.setPatient(paciente);
    appointment.setProfessional(profesional);
    appointment.setCreatedBy(creador);
    appointment.setDateTime(java.time.LocalDateTime.now().plusDays(1));

    when(appointmentRepository.existsByProfessionalAndDateTime(20, appointment.getDateTime())).thenReturn(false);
    when(appointmentRepository.save(appointment)).thenReturn(appointment);

    Appointment resultado = appointmentService.createManualAppointment(appointment);

    assertNotNull(resultado);
    assertEquals(com.mycompany.piedrazul.domain.model.AppointmentStatus.PROGRAMADA, appointment.getStatus());
    verify(appointmentRepository).existsByProfessionalAndDateTime(20, appointment.getDateTime());
    verify(appointmentRepository).save(appointment);
}

//Prueba13
@Test
public void listAppointmentsByProfessionalAndDate_deberiaLanzarExcepcion_siProfesionalEsInvalido() {
    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.listAppointmentsByProfessionalAndDate(0, LocalDate.now())
    );

    assertEquals("Profesional invalido.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba14
@Test
public void listAppointmentsByProfessionalAndDate_deberiaLanzarExcepcion_siFechaEsNula() {
    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.listAppointmentsByProfessionalAndDate(20, null)
    );

    assertEquals("La fecha es obligatoria.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba15
@Test
public void listAppointmentsByProfessionalAndDate_deberiaRetornarLista_siDatosSonValidos() {
    List<Appointment> listaEsperada = java.util.Collections.singletonList(new Appointment());

    when(appointmentRepository.findByProfessionalAndDate(20, LocalDate.of(2026, 3, 30))).thenReturn(listaEsperada);

    List<Appointment> resultado = appointmentService.listAppointmentsByProfessionalAndDate(20, LocalDate.of(2026, 3, 30));

    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    verify(appointmentRepository).findByProfessionalAndDate(20, LocalDate.of(2026, 3, 30));
}

//Prueba16
@Test
public void getOccupiedSlots_deberiaLanzarExcepcion_siProfesionalEsInvalido() {
    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.getOccupiedSlots(0, LocalDate.now())
    );

    assertEquals("Profesional invalido.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba17
@Test
public void getOccupiedSlots_deberiaLanzarExcepcion_siFechaEsNula() {
    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> appointmentService.getOccupiedSlots(20, null)
    );

    assertEquals("La fecha es obligatoria.", excepcion.getMessage());
    verifyNoInteractions(appointmentRepository);
}

//Prueba18
@Test
public void getOccupiedSlots_deberiaRetornarLista_siDatosSonValidos() {
    List<java.time.LocalDateTime> horariosEsperados =
            java.util.Collections.singletonList(java.time.LocalDateTime.of(2026, 3, 30, 10, 0));

    when(appointmentRepository.findOccupiedSlots(20, LocalDate.of(2026, 3, 30))).thenReturn(horariosEsperados);

    List<java.time.LocalDateTime> resultado = appointmentService.getOccupiedSlots(20, LocalDate.of(2026, 3, 30));

    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    verify(appointmentRepository).findOccupiedSlots(20, LocalDate.of(2026, 3, 30));
}
}