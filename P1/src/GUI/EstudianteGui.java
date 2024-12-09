package GUI;

import Proyecto1.Estudiante;

import javax.swing.*;
import java.awt.*;

public class EstudianteGui extends JFrame {
    private Estudiante estudiante;

    public EstudianteGui(Estudiante estudiante) {
        this.estudiante = estudiante;
        setTitle("Menú Estudiante - " + estudiante.getNombreUsuario());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones
        JButton btnVerPerfil = new JButton("Ver Perfil");
        JButton btnInscribirseLP = new JButton("Inscribirse en un Learning Path");
        JButton btnVerProgreso = new JButton("Ver Progreso en un Learning Path");
        JButton btnMarcarActividad = new JButton("Marcar Inicio de una Actividad");

        panel.add(btnVerPerfil);
        panel.add(btnInscribirseLP);
        panel.add(btnVerProgreso);
        panel.add(btnMarcarActividad);

        // Añadir panel a la ventana
        add(panel);

        // Acciones
        btnVerPerfil.addActionListener(e -> new VerPerfilEstudianteGui(estudiante).setVisible(true));
        btnInscribirseLP.addActionListener(e -> new InscribirseLearningPathGui(estudiante).setVisible(true));
        btnVerProgreso.addActionListener(e -> new VerProgresoGui(estudiante).setVisible(true));
        btnMarcarActividad.addActionListener(e -> new MarcarActividadGui(estudiante).setVisible(true));
    }
}
