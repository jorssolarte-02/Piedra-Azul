package com.mycompany.piedrazul.ui;

import com.mycompany.piedrazul.domain.model.Usuario;
import com.mycompany.piedrazul.domain.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//clase login
public class LoginFrame extends JFrame {

    private UsuarioService usuarioService;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegistrar, btnMostrar;

    private JLabel lblUserError, lblPassError;
    private JLabel lblUserCheck, lblPassCheck;

    private boolean passwordVisible = false;

    public LoginFrame(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        initComponents();
    }

    private void initComponents() {

        setTitle("ACM - Piedra Azul");
        setSize(1000, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(650, 700));
        card.setMinimumSize(new Dimension(600, 650));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(35, 50, 35, 50));

        JLabel lblTitulo = new JLabel("Iniciar sesión");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 150, 136));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Accede por medio de tu cuenta");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 15));

        txtUsername = new JTextField("tu usuario");
        txtUsername.setForeground(Color.GRAY);
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        txtUsername.setBackground(new Color(235,235,235));
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        txtPassword = new JPasswordField("tu contraseña");
        txtPassword.setForeground(Color.GRAY);
        txtPassword.setBackground(new Color(235,235,235));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setEchoChar((char) 0); // 🔥 importante para placeholder

        btnMostrar = new JButton("👁");
        btnMostrar.setPreferredSize(new Dimension(60,50));

        passPanel.add(txtPassword, BorderLayout.CENTER);
        passPanel.add(btnMostrar, BorderLayout.EAST);

        btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBackground(new Color(0, 150, 136));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(Color.WHITE);
        btnRegistrar.setForeground(new Color(0, 150, 136));
        btnRegistrar.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 136)));
        btnRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnRegistrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(10));
        card.add(lblSub);
        card.add(Box.createVerticalStrut(35));

        card.add(lblUsuario);
        card.add(Box.createVerticalStrut(5));
        card.add(txtUsername);
        card.add(Box.createVerticalStrut(20));

        card.add(lblPassword);
        card.add(Box.createVerticalStrut(5));
        card.add(passPanel);
        card.add(Box.createVerticalStrut(25));

        card.add(btnLogin);
        card.add(Box.createVerticalStrut(15));
        card.add(btnRegistrar);

        center.add(card);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        add(main);

        placeholder();
        eventos();
    }

    // 🔥 ERROR ESTILO MODERNO
    private void mostrarError(String mensaje) {

        JDialog dialog = new JDialog(this, "Error", true);
        dialog.setSize(360, 170);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel icon = new JLabel("X", SwingConstants.CENTER);
        icon.setOpaque(true);
        icon.setBackground(Color.RED);
        icon.setForeground(Color.WHITE);
        icon.setPreferredSize(new Dimension(45,45));
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

    // 🔥 AQUÍ ESTÁ EL ARREGLO
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
                    txtPassword.setEchoChar('•'); // 🔥 OCULTA
                }
            }

            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("tu contraseña");
                    txtPassword.setForeground(Color.GRAY);
                    txtPassword.setEchoChar((char) 0); // 🔥 MUESTRA placeholder
                }
            }
        });
    }

    private void eventos() {

        btnMostrar.addActionListener(e -> {
            passwordVisible = !passwordVisible;
            txtPassword.setEchoChar(passwordVisible ? (char) 0 : '•');
        });

        btnLogin.addActionListener(e -> login());

        btnRegistrar.addActionListener(e -> {
            new RegistroFrame(usuarioService).setVisible(true);
        });
    }

    private void login() {

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || username.equals("tu usuario") ||
            password.isEmpty() || password.equals("tu contraseña")) {

            mostrarError("Ingrese usuario y contraseña");
            return;
        }

        try {
            Usuario usuario = usuarioService.autenticar(username, password);

            if (usuario == null) {
                mostrarError("Usuario o contraseña incorrectos");
                return;
            }

            new MenuPrincipalFrame(usuario, usuarioService).setVisible(true);
            this.dispose();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }
}