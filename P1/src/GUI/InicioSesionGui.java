package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Persistencia.ArchivoPersistencia;
import Proyecto1.Profesor;
import Proyecto1.Estudiante;
import java.util.List;

public class InicioSesionGui extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion, btnRegistrarse;

    public InicioSesionGui() {
        setTitle("Inicio de Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Componentes
        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();

        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnRegistrarse = new JButton("Registrarse");

        // Añadir componentes al panel
        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblContrasena);
        panel.add(txtContrasena);
        panel.add(btnIniciarSesion);
        panel.add(btnRegistrarse);

        // Añadir panel a la ventana
        add(panel);

        // Acciones de los botones
        configurarAcciones();
    }

    private void configurarAcciones() {
        // Acción para el botón "Iniciar Sesión"
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                // Cargar usuarios desde la persistencia
                List<Profesor> profesores = ArchivoPersistencia.cargarProfesores();
                List<Estudiante> estudiantes = ArchivoPersistencia.cargarEstudiantes();

                // Validar profesores
                for (Profesor p : profesores) {
                    if (p.getNombreUsuario().equals(usuario) && p.getContrasena().equals(contrasena)) {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso (Profesor)");
                        new ProfesorGui(p).setVisible(true); // Abre menú profesor
                        dispose(); // Cierra la ventana actual
                        return;
                    }
                }

                // Validar estudiantes
                for (Estudiante estudiante : estudiantes) { // Cambié 'e' a 'estudiante'
                    if (estudiante.getNombreUsuario().equals(usuario) && estudiante.getContrasena().equals(contrasena)) {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso (Estudiante)");
                        new EstudianteGui(estudiante).setVisible(true); // Abre menú estudiante
                        dispose();
                        return;
                    }
                }

                // Usuario no encontrado
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
            }
        });

        // Acción para el botón "Registrarse"
        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistroGui().setVisible(true); // Ir a la pantalla de registro
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InicioSesionGui().setVisible(true));
    }
}