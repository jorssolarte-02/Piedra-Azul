package com.mycompany.piedrazul.ui.panel;

import com.mycompany.piedrazul.domain.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class PacientePanel extends JPanel {

    private Usuario usuarioActual;

    public PacientePanel(Usuario usuarioActual) {
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
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(350, 220));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // ================= BOTONES =================
        JButton btnAgendar = crearBoton("Agendar cita");
        JButton btnMisCitas = crearBoton("Mis citas");

        btnAgendar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMisCitas.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(btnAgendar);
        card.add(Box.createVerticalStrut(25));
        card.add(btnMisCitas);
        card.add(Box.createVerticalGlue());

        center.add(card);

        // ================= ADD =================
        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        // ================= ACCIONES =================

        /*
        btnAgendar.addActionListener(e -> {
            SelfServiceAppointmentDialog dialog = new SelfServiceAppointmentDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                usuarioActual
            );
            dialog.setVisible(true);
        });

        btnMisCitas.addActionListener(e -> {
            AppointmentListPanel listPanel = new AppointmentListPanel(usuarioActual, false);
            JOptionPane.showMessageDialog(this, listPanel, "Mis Citas", JOptionPane.PLAIN_MESSAGE);
        });
        */
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setFocusPainted(false);
        boton.setBackground(new Color(255, 152, 0)); // naranja paciente
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setMaximumSize(new Dimension(220, 55));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }
}