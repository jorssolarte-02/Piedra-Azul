package com.mycompany.piedrazul.ui.panel;

import com.mycompany.piedrazul.domain.model.Usuario;

import javax.swing.*;
import java.awt.*;
//clase paciente
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

        // ================= CONTENEDOR GENERAL =================
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(new Color(240, 240, 240));

        // 🔹 TÍTULO
        JLabel lblTitulo = new JLabel("Paciente");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblTitulo.setForeground(new Color(0, 150, 136));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ================= CENTRO =================
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(240, 240, 240));

        JPanel card = new JPanel();
        card.setLayout(new GridLayout(1, 2, 30, 0)); // 🔥 2 columnas
        card.setPreferredSize(new Dimension(450, 220));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // ================= BOTONES =================
        JButton btnAgendar = crearBoton("Agendar cita");
        JButton btnMisCitas = crearBoton("Mis citas");

        card.add(btnAgendar);
        card.add(btnMisCitas);

        center.add(card);

        // ================= ADD =================
        contenedor.add(Box.createVerticalStrut(20));
        contenedor.add(lblTitulo);
        contenedor.add(Box.createVerticalStrut(20));
        contenedor.add(center);

        add(header, BorderLayout.NORTH);
        add(contenedor, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton("<html><center>" + texto + "</center></html>");

        boton.setFocusPainted(false);
        boton.setBackground(new Color(0, 150, 136));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // 🔥 BOTÓN CUADRADO
        boton.setPreferredSize(new Dimension(150, 150));

        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }
}