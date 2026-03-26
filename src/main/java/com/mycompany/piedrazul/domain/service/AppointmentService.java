package com.mycompany.piedrazul.domain.service;

import com.mycompany.piedrazul.domain.model.Appointment;
import com.mycompany.piedrazul.domain.model.AppointmentStatus;
import com.mycompany.piedrazul.domain.model.Usuario;
import com.mycompany.piedrazul.domain.repository.IAppointmentRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {

    private final IAppointmentRepository appointmentRepository;

    public AppointmentService(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createSelfServiceAppointment(Appointment appointment, Usuario authenticatedUser) {
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("Debe iniciar sesion para agendar la cita.");
        }

        validarDatosBasicos(appointment);

        if (appointmentRepository.existsByProfessionalAndDateTime(
                appointment.getProfessional().getId(),
                appointment.getDateTime())) {
            throw new IllegalArgumentException("El horario ya no esta disponible.");
        }

        appointment.setCreatedBy(authenticatedUser);
        appointment.setStatus(AppointmentStatus.PROGRAMADA);

        return appointmentRepository.save(appointment);
    }

    public Appointment createManualAppointment(Appointment appointment) {
        validarDatosBasicos(appointment);

        if (appointment.getCreatedBy() == null || appointment.getCreatedBy().getId() <= 0) {
            throw new IllegalArgumentException("El usuario que crea la cita es obligatorio.");
        }

        if (appointmentRepository.existsByProfessionalAndDateTime(
                appointment.getProfessional().getId(),
                appointment.getDateTime())) {
            throw new IllegalArgumentException("El horario ya esta ocupado.");
        }

        appointment.setStatus(AppointmentStatus.PROGRAMADA);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> listAppointmentsByProfessionalAndDate(int professionalId, LocalDate date) {
        if (professionalId <= 0) {
            throw new IllegalArgumentException("Profesional invalido.");
        }

        if (date == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }

        return appointmentRepository.findByProfessionalAndDate(professionalId, date);
    }

    public List<LocalDateTime> getOccupiedSlots(int professionalId, LocalDate date) {
        if (professionalId <= 0) {
            throw new IllegalArgumentException("Profesional invalido.");
        }

        if (date == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }

        return appointmentRepository.findOccupiedSlots(professionalId, date);
    }

    private void validarDatosBasicos(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("La cita no puede ser nula.");
        }

        if (appointment.getPatient() == null || appointment.getPatient().getId() <= 0) {
            throw new IllegalArgumentException("Paciente invalido.");
        }

        if (appointment.getProfessional() == null || appointment.getProfessional().getId() <= 0) {
            throw new IllegalArgumentException("Profesional invalido.");
        }

        if (appointment.getDateTime() == null) {
            throw new IllegalArgumentException("La fecha y hora son obligatorias.");
        }

        if (appointment.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede agendar una cita en una fecha u hora pasada.");
        }
    }
}