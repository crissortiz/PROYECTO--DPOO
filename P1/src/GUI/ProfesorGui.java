package GUI;

import javax.swing.*;
import Proyecto1.Profesor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfesorGui extends JFrame {
    
    public ProfesorGui(Profesor profesor) {
        setTitle("Panel del Profesor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Agregar un mensaje inicial
        JLabel lblBienvenida = new JLabel("Bienvenido, " + profesor.getNombreUsuario());
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblBienvenida);

        // Botón para ver el perfil
        JButton btnVerPerfil = new JButton("Ver Perfil");
        btnVerPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se abriría la ventana para ver el perfil del profesor
                new VerPerfilProfesorGui(profesor).setVisible(true);
            }
        });
        panel.add(btnVerPerfil);

        // Botón para crear un Learning Path
        JButton btnCrearLearningPath = new JButton("Crear Learning Path");
        btnCrearLearningPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se abriría la ventana para crear un Learning Path
                new CrearLearningPathGui(profesor).setVisible(true);
            }
        });
        panel.add(btnCrearLearningPath);

        // Botón para modificar un Learning Path
        JButton btnModificarLearningPath = new JButton("Modificar Learning Path");
        btnModificarLearningPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se abriría la ventana para modificar un Learning Path
                new ModificarLearningPathGui(profesor).setVisible(true);
            }
        });
        panel.add(btnModificarLearningPath);

        // Botón para agregar una actividad
        JButton btnAgregarActividad = new JButton("Agregar Actividad");
        btnAgregarActividad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se abriría la ventana para agregar una actividad a un Learning Path
                new AgregarActividadGui(profesor).setVisible(true);
            }
        });
        panel.add(btnAgregarActividad);

        // Botón para ver los Learning Paths creados
        JButton btnVerLearningPaths = new JButton("Ver Learning Paths Creados");
        btnVerLearningPaths.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se abriría la ventana para mostrar los Learning Paths creados por el profesor
                new VerLearningPathsGui(profesor).setVisible(true);
            }
        });
        panel.add(btnVerLearningPaths);

        // Botón para clonar un Learning Path
        JButton btnClonarLearningPath = new JButton("Clonar Learning Path");
        btnClonarLearningPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se abriría la ventana para clonar un Learning Path
                new ClonarLearningPathGui(profesor).setVisible(true);
            }
        });
        panel.add(btnClonarLearningPath);

        // Botón para establecer actividad de seguimiento
        JButton btnEstablecerActividadSeguimiento = new JButton("Establecer Actividad de Seguimiento");
        btnEstablecerActividadSeguimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se abriría la ventana para establecer actividades de seguimiento
                new EstablecerActividadSeguimientoGui(profesor).setVisible(true);
            }
        });
        panel.add(btnEstablecerActividadSeguimiento);

        // Añadir panel a la ventana
        add(panel);
    }

    public static void main(String[] args) {
        // Crear un objeto de prueba para el profesor
        Profesor profesor = new Profesor("Profesor1", "password", "profesor1@correo.com", 1);
        SwingUtilities.invokeLater(() -> new ProfesorGui(profesor).setVisible(true));
    }
}

