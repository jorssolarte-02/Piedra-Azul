package com.mycompany.piedrazul.ui.panel;

import com.mycompany.piedrazul.domain.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class MedicoPanel extends JPanel {

    private Usuario usuarioActual;

    public MedicoPanel(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        initComponents();
    }

    private void initComponents() {

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 150, 136));
        header.setPreferredSize(new Dimension(100, 50));

        JLabel title = new JLabel("ACM – PIEDRA AZUL");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        header.add(title, BorderLayout.WEST);

        // ================= CENTRO =================
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(240, 240, 240));

        JPanel card = new JPanel();
        card.setLayout(new GridLayout(2, 2, 20, 20));
        card.setPreferredSize(new Dimension(380, 240));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // ================= BOTONES =================
        JButton btnAgendar = crearBoton("Agendar cita");
        JButton btnMisCitas = crearBoton("Mis citas");
        JButton btnPacientes = crearBoton("Pacientes atendidos");
        JButton btnHistorial = crearBoton("Historial");

        // ================= ACCIONES =================

        /*
        btnAgendar.addActionListener(e -> {
            ManualAppointmentDialog dialog = new ManualAppointmentDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                usuarioActual
            );
            dialog.setVisible(true);
        });

        btnMisCitas.addActionListener(e -> {
            AppointmentListPanel listPanel = new AppointmentListPanel(usuarioActual, false);
            JOptionPane.showMessageDialog(this, listPanel, "Mis Citas", JOptionPane.PLAIN_MESSAGE);
        });

        btnHistorial.addActionListener(e -> {
            AppointmentListPanel listPanel = new AppointmentListPanel(usuarioActual, true);
            JOptionPane.showMessageDialog(this, listPanel, "Historial", JOptionPane.PLAIN_MESSAGE);
        });
        */

        btnPacientes.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Funcionalidad en desarrollo",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // ================= ADD BOTONES =================
        card.add(btnAgendar);
        card.add(btnMisCitas);
        card.add(btnPacientes);
        card.add(btnHistorial);

        center.add(card);

        // ================= ADD PRINCIPAL =================
        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setFocusPainted(false);
        boton.setBackground(new Color(76, 175, 80)); // verde médico
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(150, 55));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }
}