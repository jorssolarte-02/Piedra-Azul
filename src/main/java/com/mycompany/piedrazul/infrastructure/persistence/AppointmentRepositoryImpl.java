package com.mycompany.piedrazul.infrastructure.persistence;

import com.mycompany.piedrazul.domain.model.Appointment;
import com.mycompany.piedrazul.domain.model.AppointmentStatus;
import com.mycompany.piedrazul.domain.repository.IAppointmentRepository;
import com.mycompany.piedrazul.domain.repository.IUsuarioRepository;
import com.mycompany.piedrazul.infrastructure.persistence.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepositoryImpl implements IAppointmentRepository {

    private final IUsuarioRepository usuarioRepository;

    public AppointmentRepositoryImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Appointment save(Appointment appointment) {
        String sql = """
            INSERT INTO cita (usu_id, paciente_id, medico_id, fecha_hora_cita, cita_estado, observacion)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, appointment.getCreatedBy().getId());
            stmt.setInt(2, appointment.getPatient().getId());
            stmt.setInt(3, appointment.getProfessional().getId());
            stmt.setTimestamp(4, Timestamp.valueOf(appointment.getDateTime()));
            stmt.setString(5, appointment.getStatus().name());
            stmt.setString(6, appointment.getNotes());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        appointment.setId(generatedKeys.getInt(1));
                    }
                }
            }

            return appointment;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la cita: " + e.getMessage(), e);
        }
    }

    @Override
    public Appointment findById(int id) {
        String sql = """
            SELECT cita_id, usu_id, paciente_id, medico_id, fecha_hora_cita, cita_estado, observacion
            FROM cita
            WHERE cita_id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAppointment(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la cita por id: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public boolean existsByProfessionalAndDateTime(int professionalId, LocalDateTime dateTime) {
        String sql = """
            SELECT COUNT(*)
            FROM cita
            WHERE medico_id = ?
              AND fecha_hora_cita = ?
              AND cita_estado <> 'CANCELADA'
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, professionalId);
            stmt.setTimestamp(2, Timestamp.valueOf(dateTime));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al validar disponibilidad de la cita: " + e.getMessage(), e);
        }

        return false;
    }

    @Override
    public List<Appointment> findByProfessionalAndDate(int professionalId, LocalDate date) {
        List<Appointment> appointments = new ArrayList<>();

        String sql = """
            SELECT cita_id, usu_id, paciente_id, medico_id, fecha_hora_cita, cita_estado, observacion
            FROM cita
            WHERE medico_id = ?
              AND DATE(fecha_hora_cita) = ?
            ORDER BY fecha_hora_cita ASC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, professionalId);
            stmt.setDate(2, Date.valueOf(date));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar citas por medico y fecha: " + e.getMessage(), e);
        }

        return appointments;
    }

    @Override
    public List<LocalDateTime> findOccupiedSlots(int professionalId, LocalDate date) {
        List<LocalDateTime> occupiedSlots = new ArrayList<>();

        String sql = """
            SELECT fecha_hora_cita
            FROM cita
            WHERE medico_id = ?
              AND DATE(fecha_hora_cita) = ?
              AND cita_estado <> 'CANCELADA'
            ORDER BY fecha_hora_cita ASC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, professionalId);
            stmt.setDate(2, Date.valueOf(date));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    occupiedSlots.add(rs.getTimestamp("fecha_hora_cita").toLocalDateTime());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar horarios ocupados: " + e.getMessage(), e);
        }

        return occupiedSlots;
    }

    private Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();

        appointment.setId(rs.getInt("cita_id"));
        appointment.setPatient(usuarioRepository.findById(rs.getInt("paciente_id")));
        appointment.setProfessional(usuarioRepository.findById(rs.getInt("medico_id")));
        appointment.setCreatedBy(usuarioRepository.findById(rs.getInt("usu_id")));
        appointment.setDateTime(rs.getTimestamp("fecha_hora_cita").toLocalDateTime());
        appointment.setStatus(AppointmentStatus.valueOf(rs.getString("cita_estado")));
        appointment.setNotes(rs.getString("observacion"));

        return appointment;
    }
}