package com.mycompany.piedrazul.domain.repository;

import com.mycompany.piedrazul.domain.model.Appointment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentRepository {

    Appointment save(Appointment appointment);

    Appointment findById(int id);

    boolean existsByProfessionalAndDateTime(int professionalId, LocalDateTime dateTime);

    List<Appointment> findByProfessionalAndDate(int professionalId, LocalDate date);

    List<LocalDateTime> findOccupiedSlots(int professionalId, LocalDate date);
}