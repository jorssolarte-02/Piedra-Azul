package com.mycompany.piedrazul.ui;

import com.mycompany.piedrazul.domain.model.Usuario;
import com.mycompany.piedrazul.domain.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private UsuarioService usuarioService;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegistrar, btnMostrar;

    private JLabel lblUserError, lblPassError;
    private JLabel lblUserCheck, lblPassCheck;

    private JPanel errorPanel;
    private JLabel errorLabel;

    private boolean passwordVisible = false;

    public LoginFrame(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        initComponents();
    }

    private void initComponents() {

        setTitle("ACM - Piedra Azul");
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 240, 240));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 150, 136));
        header.setPreferredSize(new Dimension(100, 50));

        JLabel title = new JLabel("ACM – PIEDRA AZUL");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        header.add(title, BorderLayout.WEST);

        // CENTRO
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(240, 240, 240));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(360, 400));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Iniciar sesión");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 150, 136));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Accede por medio de tu cuenta");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== USUARIO =====
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));

        txtUsername = new JTextField("tu usuario");
        txtUsername.setForeground(Color.GRAY);
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        lblUserError = new JLabel(" ");
        lblUserError.setForeground(Color.RED);
        lblUserError.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        lblUserCheck = new JLabel(" ");
        lblUserCheck.setForeground(new Color(0, 150, 0));

        // ===== PASSWORD =====
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        txtPassword = new JPasswordField("tu contraseña");
        txtPassword.setForeground(Color.GRAY);

        btnMostrar = new JButton("👁");
        btnMostrar.setFocusPainted(false);

        passPanel.add(txtPassword, BorderLayout.CENTER);
        passPanel.add(btnMostrar, BorderLayout.EAST);

        lblPassError = new JLabel(" ");
        lblPassError.setForeground(Color.RED);
        lblPassError.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        lblPassCheck = new JLabel(" ");
        lblPassCheck.setForeground(new Color(0, 150, 0));

        // ERROR PANEL
        errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorPanel.setBackground(new Color(255, 230, 230));
        errorPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        errorPanel.setVisible(false);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        errorPanel.add(new JLabel("❌"));
        errorPanel.add(errorLabel);

        // BOTONES
        btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBackground(new Color(0, 150, 136));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(Color.WHITE);
        btnRegistrar.setForeground(new Color(0, 150, 136));
        btnRegistrar.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 136)));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // ADD
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(5));
        card.add(lblSub);
        card.add(Box.createVerticalStrut(20));

        card.add(lblUsuario);
        card.add(txtUsername);
        card.add(lblUserError);
        card.add(lblUserCheck);
        card.add(Box.createVerticalStrut(10));

        card.add(lblPassword);
        card.add(passPanel);
        card.add(lblPassError);
        card.add(lblPassCheck);
        card.add(Box.createVerticalStrut(10));

        card.add(errorPanel);
        card.add(Box.createVerticalStrut(15));

        card.add(btnLogin);
        card.add(Box.createVerticalStrut(10));
        card.add(btnRegistrar);

        center.add(card);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        add(main);

        placeholder();
        eventos();
    }

    private void placeholder() {
        txtUsername.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtUsername.getText().equals("tu usuario")) {
                    txtUsername.setText("");
                    txtUsername.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (txtUsername.getText().isEmpty()) {
                    txtUsername.setText("tu usuario");
                    txtUsername.setForeground(Color.GRAY);
                }
            }
        });

        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).equals("tu contraseña")) {
                    txtPassword.setText("");
                    txtPassword.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("tu contraseña");
                    txtPassword.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void eventos() {

        txtUsername.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validarUsuario();
            }
        });

        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validarPassword();
            }
        });

        btnMostrar.addActionListener(e -> {
            passwordVisible = !passwordVisible;
            txtPassword.setEchoChar(passwordVisible ? (char) 0 : '•');
        });

        btnLogin.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());

        btnRegistrar.addActionListener(e -> {
            new RegistroFrame(usuarioService).setVisible(true);
        });
    }

    private boolean validarUsuario() {
        String user = txtUsername.getText().trim();

        if (user.isEmpty() || user.equals("tu usuario")) {
            lblUserError.setText("Ingrese su usuario");
            lblUserCheck.setText(" ");
            return false;
        }

        lblUserError.setText(" ");
        lblUserCheck.setText("✔");
        return true;
    }

    private boolean validarPassword() {
        String pass = new String(txtPassword.getPassword());

        if (pass.isEmpty() || pass.equals("tu contraseña")) {
            lblPassError.setText("Ingrese su contraseña");
            lblPassCheck.setText(" ");
            return false;
        }

        if (pass.length() < 6) {
            lblPassError.setText("Mínimo 6 caracteres");
            lblPassCheck.setText(" ");
            return false;
        }

        lblPassError.setText(" ");
        lblPassCheck.setText("✔");
        return true;
    }

    private void mostrarError(String msg) {
        errorLabel.setText(msg);
        errorPanel.setVisible(true);
    }

    private void ocultarError() {
        errorPanel.setVisible(false);
    }

    private void login() {

        ocultarError();

        if (!validarUsuario() | !validarPassword()) {
            mostrarError("Complete correctamente los campos");
            return;
        }

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        try {
            Usuario usuario = usuarioService.autenticar(username, password);

            if (usuario == null) {
                mostrarError("Usuario o contraseña incorrectos");
                return;
            }

            new MenuPrincipalFrame(usuario, usuarioService).setVisible(true);
            this.dispose();

        } catch (IllegalStateException ex) {
            mostrarError(ex.getMessage());
        }
    }
}