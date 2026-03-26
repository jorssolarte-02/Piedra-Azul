package com.mycompany.piedrazul.ui.panel;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    public AdminPanel() {
        initComponents();
    }

    private void initComponents() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔹 HEADER (barra superior)
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 150, 136));
        header.setPreferredSize(new Dimension(100, 50));

        JLabel titulo = new JLabel("ACM – PIEDRA AZUL");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        JButton btnCerrar = new JButton("Cerrar sesión");
        btnCerrar.setFocusPainted(false);

        header.add(titulo, BorderLayout.WEST);
        header.add(btnCerrar, BorderLayout.EAST);

        // 🔹 PANEL CENTRAL (contenedor general)
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(Color.WHITE);

        // 🔹 PANEL INTERNO (como el recuadro de la imagen)
        JPanel panelAdmin = new JPanel(new GridLayout(2, 2, 15, 15));
        panelAdmin.setBorder(BorderFactory.createTitledBorder("Panel Administrador"));
        panelAdmin.setPreferredSize(new Dimension(300, 200));
        panelAdmin.setBackground(Color.WHITE);

        // 🔹 BOTONES (adaptados a tu imagen)
        JButton btnGestionUsuarios = crearBoton("Gestión de usuarios");
        JButton btnAuditoria = crearBoton("Auditoría del sistema");
        JButton btnConfig = crearBoton("Configuración");

        panelAdmin.add(btnGestionUsuarios);
        panelAdmin.add(btnAuditoria);
        panelAdmin.add(btnConfig);

        // Espacio vacío para cuadrar diseño
        panelAdmin.add(new JLabel());

        centro.add(panelAdmin);

        // 🔹 AGREGAR TODO
        add(header, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setFocusPainted(false);
        boton.setBackground(new Color(0, 150, 136));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setPreferredSize(new Dimension(130, 40));

        return boton;
    }
}