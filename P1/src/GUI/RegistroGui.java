package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Persistencia.ArchivoPersistencia;
import Proyecto1.Profesor;
import Proyecto1.Estudiante;
import java.util.List;

public class RegistroGui extends JFrame {
    private JTextField txtNombreUsuario;
    private JPasswordField txtContrasena;
    private JRadioButton rbProfesor, rbEstudiante;
    private JButton btnRegistrar, btnVolver;

    public RegistroGui() {
        setTitle("Registro de Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Componentes
        JLabel lblNombreUsuario = new JLabel("Nombre de usuario:");
        txtNombreUsuario = new JTextField();

        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();

        rbProfesor = new JRadioButton("Profesor");
        rbEstudiante = new JRadioButton("Estudiante");
        ButtonGroup grupoUsuarios = new ButtonGroup();
        grupoUsuarios.add(rbProfesor);
        grupoUsuarios.add(rbEstudiante);

        btnRegistrar = new JButton("Registrar");
        btnVolver = new JButton("Volver");

        // Añadir componentes al panel
        panel.add(lblNombreUsuario);
        panel.add(txtNombreUsuario);
        panel.add(lblContrasena);
        panel.add(txtContrasena);

        JPanel panelRadioButtons = new JPanel();
        panelRadioButtons.add(rbProfesor);
        panelRadioButtons.add(rbEstudiante);
        panel.add(panelRadioButtons);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVolver);
        panel.add(panelBotones);

        // Añadir panel a la ventana
        add(panel);

        // Acciones de los botones
        configurarAcciones();
    }

    private void configurarAcciones() {
        // Acción para el botón "Registrar"
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nombreUsuario = txtNombreUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                // Verificar que los campos no estén vacíos
                if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
                    return;
                }

                // Verificar que se haya seleccionado un tipo de usuario
                if (!rbProfesor.isSelected() && !rbEstudiante.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un tipo de usuario.");
                    return;
                }

                // Cargar listas de usuarios desde el archivo
                List<Profesor> profesores = ArchivoPersistencia.cargarProfesores();
                List<Estudiante> estudiantes = ArchivoPersistencia.cargarEstudiantes();

                // Validar que el nombre de usuario no esté repetido
                for (Profesor p : profesores) {
                    if (p.getNombreUsuario().equals(nombreUsuario)) {
                        JOptionPane.showMessageDialog(null, "El nombre de usuario ya está registrado como Profesor.");
                        return;
                    }
                }
                
                // Cambié el nombre de la variable para evitar el conflicto con "e"
                for (Estudiante estudiante : estudiantes) {
                    if (estudiante.getNombreUsuario().equals(nombreUsuario)) {
                        JOptionPane.showMessageDialog(null, "El nombre de usuario ya está registrado como Estudiante.");
                        return;
                    }
                }

                // Registrar usuario
                if (rbProfesor.isSelected()) {
                    // Crear nuevo Profesor
                    int idProfesor = profesores.size() + 1;
                    Profesor nuevoProfesor = new Profesor(nombreUsuario, contrasena, "", idProfesor);
                    profesores.add(nuevoProfesor);
                    ArchivoPersistencia.guardarProfesor(nuevoProfesor);
                    JOptionPane.showMessageDialog(null, "Registro exitoso como Profesor.");
                } else {
                    // Crear nuevo Estudiante
                    int idEstudiante = estudiantes.size() + 1;
                    Estudiante nuevoEstudiante = new Estudiante(nombreUsuario, contrasena, "", idEstudiante);
                    estudiantes.add(nuevoEstudiante);
                    ArchivoPersistencia.guardarEstudiante(nuevoEstudiante);
                    JOptionPane.showMessageDialog(null, "Registro exitoso como Estudiante.");
                }

                // Volver al inicio de sesión
                new InicioSesionGui().setVisible(true);
                dispose();
            }
        });

        // Acción para el botón "Volver"
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InicioSesionGui().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroGui().setVisible(true));
    }
}




