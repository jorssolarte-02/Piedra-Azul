package com.mycompany.piedrazul.ui;

import com.mycompany.piedrazul.domain.model.Rol;
import com.mycompany.piedrazul.domain.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class RegistroFrame extends JFrame {

    private UsuarioService usuarioService;

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
        setSize(700, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 240, 240));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 150, 136));
        header.setPreferredSize(new Dimension(100, 50));

        JLabel title = new JLabel("ACM – PIEDRA AZUL");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        header.add(title, BorderLayout.WEST);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(240, 240, 240));

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(420, 520));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Registrar Usuario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 150, 136));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Completa los datos para crear una cuenta");
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtPrimerNombre = crearCampo("tu primer nombre");
        txtSegundoNombre = crearCampo("tu segundo nombre");

        txtPrimerApellido = crearCampo("tu primer apellido");
        txtSegundoApellido = crearCampo("tu segundo apellido");

        txtTelefono = crearCampo("tu teléfono");
        txtDni = crearCampo("tu documento");
        txtUsername = crearCampo("tu usuario");

        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        txtPassword = new JPasswordField("tu contraseña");
        txtPassword.setForeground(Color.GRAY);
        txtPassword.setBackground(new Color(235,235,235));
        txtPassword.setEchoChar((char) 0); // 👈 importante

        // 🔥 Placeholder contraseña
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
        btnVer.addActionListener(e -> {
            passwordVisible = !passwordVisible;
            txtPassword.setEchoChar(passwordVisible ? (char) 0 : '•');
        });

        passPanel.add(txtPassword, BorderLayout.CENTER);
        passPanel.add(btnVer, BorderLayout.EAST);

        cmbGenero = new JComboBox<>(new String[]{"HOMBRE", "MUJER"});
        cmbRol = new JComboBox<>(new String[]{"PACIENTE", "MEDICO_TERAPISTA", "AGENDADOR", "ADMINISTRADOR"});

        formPanel.add(lblTitulo);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(lblSub);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(crearLabel("Primer Nombre *"));
        formPanel.add(txtPrimerNombre);

        formPanel.add(crearLabel("Segundo Nombre"));
        formPanel.add(txtSegundoNombre);

        formPanel.add(crearLabel("Primer Apellido *"));
        formPanel.add(txtPrimerApellido);

        formPanel.add(crearLabel("Segundo Apellido"));
        formPanel.add(txtSegundoApellido);

        formPanel.add(crearLabel("Teléfono *"));
        formPanel.add(txtTelefono);

        formPanel.add(crearLabel("DNI *"));
        formPanel.add(txtDni);

        formPanel.add(crearLabel("Usuario *"));
        formPanel.add(txtUsername);

        formPanel.add(crearLabel("Contraseña *"));
        formPanel.add(passPanel);

        formPanel.add(crearLabel("Género"));
        formPanel.add(cmbGenero);

        formPanel.add(crearLabel("Rol"));
        formPanel.add(cmbRol);

        JScrollPane scroll = new JScrollPane(formPanel);
        scroll.setBorder(null);

        JButton btnGuardar = new JButton("Registrarse");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.setBackground(new Color(0,150,136));
        btnGuardar.setForeground(Color.WHITE);

        JPanel bottom = new JPanel();
        bottom.add(btnCancelar);
        bottom.add(btnGuardar);

        card.add(scroll, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        center.add(card);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        add(main);

        btnGuardar.addActionListener(e -> registrar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void mostrarErrorDialog(String mensaje) {

        JDialog dialog = new JDialog(this, "Error", true);
        dialog.setSize(350, 160);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel icon = new JLabel("X", SwingConstants.CENTER);
        icon.setForeground(Color.WHITE);
        icon.setOpaque(true);
        icon.setBackground(Color.RED);
        icon.setPreferredSize(new Dimension(40,40));
        icon.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel text = new JLabel("<html>" + mensaje + "</html>");
        text.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        center.add(icon);
        center.add(text);

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> dialog.dispose());

        JPanel bottom = new JPanel();
        bottom.add(ok);

        panel.add(center, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void registrar() {

    // 🔹 Limpiar datos (MUY IMPORTANTE)
    String username = txtUsername.getText().trim();
    String password = new String(txtPassword.getPassword()).trim();
    String primerNombre = txtPrimerNombre.getText().trim();
    String segundoNombre = txtSegundoNombre.getText().trim();
    String primerApellido = txtPrimerApellido.getText().trim();
    String segundoApellido = txtSegundoApellido.getText().trim();
    String telefono = txtTelefono.getText().trim();
    String dniText = txtDni.getText().trim();

    // 🔹 Validación correcta (evita placeholders y vacíos)
    if (primerNombre.isEmpty() || primerNombre.equals("tu primer nombre") ||
        primerApellido.isEmpty() || primerApellido.equals("tu primer apellido") ||
        telefono.isEmpty() || telefono.equals("tu teléfono") ||
        dniText.isEmpty() || dniText.equals("tu documento") ||
        username.isEmpty() || username.equals("tu usuario") ||
        password.isEmpty() || password.equals("tu contraseña") || password.length() < 6) {

        mostrarErrorDialog("Todos los campos son obligatorios");
        return;
    }

    try {
        int dni = Integer.parseInt(dniText);

        usuarioService.registrarUsuario(
                username,
                password,
                Rol.valueOf((String) cmbRol.getSelectedItem()),
                primerNombre,
                segundoNombre,
                primerApellido,
                segundoApellido,
                (String) cmbGenero.getSelectedItem(),
                LocalDate.now(),
                telefono,
                dni
        );

        JOptionPane.showMessageDialog(this, "Usuario registrado correctamente");
        dispose();

    } catch (NumberFormatException e) {
        mostrarErrorDialog("El DNI debe ser numérico");

    } catch (Exception e) {

        String msg = e.getMessage().toLowerCase();

        if (msg.contains("existe")) {
            mostrarErrorDialog("El usuario ya existe");
        } else if (msg.contains("intentos") || msg.contains("bloqueado")) {
            mostrarErrorDialog("Has realizado varios intentos de registro en poco tiempo.<br>Por seguridad, el registro está bloqueado durante 15 minutos.");
        } else {
            mostrarErrorDialog(e.getMessage());
        }
    }
}

    private JTextField crearCampo(String placeholder) {
        JTextField campo = new JTextField(placeholder);
        campo.setForeground(Color.GRAY);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campo.setBackground(new Color(235,235,235));

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
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return lbl;
    }
}