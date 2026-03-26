package com.mycompany.piedrazul.domain.model;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private Usuario patient;         // Debe representar el paciente seleccionado
    private Usuario professional;    // Debe representar el medico/terapista seleccionado
    private Usuario createdBy;       // Usuario logueado que crea la cita
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private String notes;

    public Appointment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getPatient() {
        return patient;
    }

    public void setPatient(Usuario patient) {
        this.patient = patient;
    }

    public Usuario getProfessional() {
        return professional;
    }

    public void setProfessional(Usuario professional) {
        this.professional = professional;
    }

    public Usuario getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Usuario createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patient=" + (patient != null ? patient.toString() : "null") +
                ", professional=" + (professional != null ? professional.toString() : "null") +
                ", createdBy=" + (createdBy != null ? createdBy.toString() : "null") +
                ", dateTime=" + dateTime +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                '}';
    }
}