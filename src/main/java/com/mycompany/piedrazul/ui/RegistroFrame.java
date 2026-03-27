package com.mycompany.piedrazul.ui;

import com.mycompany.piedrazul.domain.model.Rol;
import com.mycompany.piedrazul.domain.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;

public class RegistroFrame extends JFrame {

    private final UsuarioService usuarioService;

    private JTextField txtPrimerNombre, txtSegundoNombre;
    private JTextField txtPrimerApellido, txtSegundoApellido;
    private JTextField txtTelefono, txtDni, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbGenero, cmbRol;

    private boolean passwordVisible = false;

    public RegistroFrame(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        initComponents();
    }

    private void initComponents() {

        setTitle("Registro - Piedra Azul");
        setSize(1000, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 240, 240));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 150, 136));
        header.setPreferredSize(new Dimension(100, 70));

        JLabel title = new JLabel("ACM – PIEDRA AZUL");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        header.add(title, BorderLayout.WEST);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(240, 240, 240));

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(650, 700));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(35, 50, 35, 50));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Registrar Usuario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 150, 136));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Completa los datos para crear una cuenta");
        lblSub.setForeground(Color.GRAY);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtPrimerNombre = crearCampo("tu primer nombre");
        txtSegundoNombre = crearCampo("tu segundo nombre");
        txtPrimerApellido = crearCampo("tu primer apellido");
        txtSegundoApellido = crearCampo("tu segundo apellido");
        txtTelefono = crearCampo("tu teléfono");
        txtDni = crearCampo("tu documento");
        txtUsername = crearCampo("tu usuario");

        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        passPanel.setBackground(Color.WHITE);

        txtPassword = new JPasswordField("tu contraseña");
        txtPassword.setForeground(Color.GRAY);
        txtPassword.setBackground(new Color(235, 235, 235));
        txtPassword.setEchoChar((char) 0);

        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).equals("tu contraseña")) {
                    txtPassword.setText("");
                    txtPassword.setForeground(Color.BLACK);
                    txtPassword.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("tu contraseña");
                    txtPassword.setForeground(Color.GRAY);
                    txtPassword.setEchoChar((char) 0);
                }
            }
        });

        JButton btnVer = new JButton("👁");
        btnVer.setFocusPainted(false);
        btnVer.setPreferredSize(new Dimension(60, 50));

        btnVer.addActionListener(e -> {
            passwordVisible = !passwordVisible;
            if (!String.valueOf(txtPassword.getPassword()).equals("tu contraseña")) {
                txtPassword.setEchoChar(passwordVisible ? (char) 0 : '•');
            }
        });

        passPanel.add(txtPassword, BorderLayout.CENTER);
        passPanel.add(btnVer, BorderLayout.EAST);

        cmbGenero = new JComboBox<>(new String[]{"HOMBRE", "MUJER", "OTRO"});
        cmbRol = new JComboBox<>(new String[]{
                "PACIENTE",
                "AGENDADOR",
                "MEDICO_TERAPISTA",
                "ADMINISTRADOR"
        });

        cmbGenero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        cmbRol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        formPanel.add(lblTitulo);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(lblSub);
        formPanel.add(Box.createVerticalStrut(30));

        agregarCampo(formPanel, "Primer Nombre *", txtPrimerNombre);
        agregarCampo(formPanel, "Segundo Nombre", txtSegundoNombre);
        agregarCampo(formPanel, "Primer Apellido *", txtPrimerApellido);
        agregarCampo(formPanel, "Segundo Apellido", txtSegundoApellido);
        agregarCampo(formPanel, "Teléfono *", txtTelefono);
        agregarCampo(formPanel, "DNI *", txtDni);
        agregarCampo(formPanel, "Usuario *", txtUsername);

        formPanel.add(crearLabel("Contraseña *"));
        formPanel.add(passPanel);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(crearLabel("Género"));
        formPanel.add(cmbGenero);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(crearLabel("Rol"));
        formPanel.add(cmbRol);

        JScrollPane scroll = new JScrollPane(formPanel);
        scroll.setBorder(null);

        JButton btnGuardar = new JButton("Registrarse");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.setBackground(new Color(0, 150, 136));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGuardar.setPreferredSize(new Dimension(180, 45));

        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnCancelar.setPreferredSize(new Dimension(160, 45));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        bottom.add(btnCancelar);
        bottom.add(btnGuardar);

        card.add(scroll, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        center.add(card, new GridBagConstraints() {{
            weightx = 1;
            weighty = 1;
            anchor = GridBagConstraints.CENTER;
        }});

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        add(main);

        btnGuardar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void agregarCampo(JPanel panel, String label, JComponent campo) {
        panel.add(crearLabel(label));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(18));
    }

    private JTextField crearCampo(String placeholder) {
        JTextField campo = new JTextField(placeholder);
        campo.setForeground(Color.GRAY);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        campo.setBackground(new Color(235, 235, 235));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                }
            }
        });

        return campo;
    }

    private JLabel crearLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return lbl;
    }

    // 🔥 SOLO ESTO SE ARREGLÓ
    private void registrarUsuario() {

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String primerNombre = txtPrimerNombre.getText().trim();
        String primerApellido = txtPrimerApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String dni = txtDni.getText().trim();

        if (primerNombre.isEmpty() || primerNombre.equals("tu primer nombre") ||
            primerApellido.isEmpty() || primerApellido.equals("tu primer apellido") ||
            telefono.isEmpty() || telefono.equals("tu teléfono") ||
            dni.isEmpty() || dni.equals("tu documento") ||
            username.isEmpty() || username.equals("tu usuario") ||
            password.isEmpty() || password.equals("tu contraseña")) {

            JOptionPane.showMessageDialog(this, "Completa todos los campos obligatorios");
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres");
            return;
        }

        if (!dni.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El DNI debe contener solo números");
            return;
        }

        try {
            boolean creado = usuarioService.registrarUsuario(
                    username,
                    password,
                    mapearRol((String) cmbRol.getSelectedItem()),
                    txtPrimerNombre.getText(),
                    txtSegundoNombre.getText(),
                    txtPrimerApellido.getText(),
                    txtSegundoApellido.getText(),
                    (String) cmbGenero.getSelectedItem(),
                    LocalDate.now(),
                    telefono,
                    dni
            );

            if (creado) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente");
                dispose();
            }

        } catch (Exception e) {

            String msg = e.getMessage() == null ? "" : e.getMessage().toLowerCase();

            if (msg.contains("existe")) {
                JOptionPane.showMessageDialog(this, "El usuario o el DNI ya existen");
            } else {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private Rol mapearRol(String rolUI) {
        switch (rolUI) {
            case "PACIENTE": return Rol.PACIENTE;
            case "AGENDADOR": return Rol.AGENDADOR;
            case "MEDICO_TERAPISTA": return Rol.MEDICO_TERAPISTA;
            case "ADMINISTRADOR": return Rol.ADMINISTRADOR;
            default: throw new IllegalArgumentException("Rol inválido");
        }
    }
}