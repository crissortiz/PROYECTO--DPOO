package GUI;

import javax.swing.*;

public class RegistroGui extends JFrame {
    public RegistroGui() {
        setTitle("Registro de Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Crear componentes básicos
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Registro de Usuario");
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblUsuario = new JLabel("Nombre de usuario:");
        JTextField txtUsuario = new JTextField();

        JLabel lblContrasena = new JLabel("Contraseña:");
        JPasswordField txtContrasena = new JPasswordField();

        JButton btnRegistrar = new JButton("Registrar");

        // Acción para registrar al usuario
        btnRegistrar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());

            if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                new InicioSesionGui().setVisible(true); // Regresa al inicio de sesión
                dispose(); // Cierra la ventana actual
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            }
        });

        // Añadir componentes al panel
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblContrasena);
        panel.add(txtContrasena);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnRegistrar);

        // Añadir el panel al marco
        add(panel);
    }
}
