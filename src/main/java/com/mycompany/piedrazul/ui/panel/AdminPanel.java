package com.mycompany.piedrazul.ui.panel;

import com.mycompany.piedrazul.domain.model.Usuario;

import javax.swing.*;
import java.awt.*;
//clase admin
public class AdminPanel extends JPanel {

    private Usuario usuario;

    public AdminPanel(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
    }

    public AdminPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void initComponents() {

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // 🔹 HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 150, 136));
        header.setPreferredSize(new Dimension(100, 60));

        JLabel titulo = new JLabel("ACM – PIEDRA AZUL");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        header.add(titulo, BorderLayout.WEST);

        // 🔹 CONTENIDO CENTRAL
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(new Color(240, 240, 240));

        // 🔹 TÍTULO (MÁS GRANDE)
        JLabel lblPanel = new JLabel("Administrador");
        lblPanel.setFont(new Font("Segoe UI", Font.BOLD, 45)); // aumentado
        lblPanel.setForeground(new Color(0, 150, 136));
        lblPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 CONTENEDOR DE BOTONES (SUBIDO UN POCO)
        JPanel panelContenedor = new JPanel(new GridBagLayout());
        panelContenedor.setBackground(new Color(240, 240, 240));
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // 👈 sube todo

        JPanel panelAdmin = new JPanel(new GridLayout(2, 2, 20, 20));
        panelAdmin.setPreferredSize(new Dimension(420, 260));
        panelAdmin.setBackground(Color.WHITE);
        panelAdmin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // 🔹 BOTONES
        panelAdmin.add(crearBoton("Gestión de usuarios"));
        panelAdmin.add(crearBoton("Auditoría del sistema"));
        panelAdmin.add(crearBoton("Configuración"));
        panelAdmin.add(new JLabel());

        panelContenedor.add(panelAdmin);

        // 🔹 ORDEN FINAL (menos espacio arriba)
        centro.add(Box.createVerticalStrut(15));
        centro.add(lblPanel);
        centro.add(Box.createVerticalStrut(15)); // reducido para subir botones
        centro.add(panelContenedor);

        add(header, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setFocusPainted(false);
        boton.setBackground(new Color(0, 150, 136));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(180, 60));

        return boton;
    }
}