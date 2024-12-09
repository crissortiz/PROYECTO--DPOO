package GUI;

import javax.swing.*;
import java.awt.*;
import Proyecto1.Profesor;

public class VerPerfilProfesorGui extends JFrame {
	
    public VerPerfilProfesorGui(Profesor profesor) {
        setTitle("Perfil del Profesor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Información del profesor
        JLabel lblTitulo = new JLabel("Perfil del Profesor", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblNombre = new JLabel("Nombre de usuario: " + profesor.getNombreUsuario());
        JLabel lblCorreo = new JLabel("Correo: " + profesor.getCorreo());
        JLabel lblLearningPaths = new JLabel("Learning Paths creados: " + profesor.getLearningPaths().size());

        // Botón para cerrar la ventana
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        // Añadir componentes al panel
        panel.add(lblTitulo);
        panel.add(lblNombre);
        panel.add(lblCorreo);
        panel.add(lblLearningPaths);
        panel.add(btnCerrar);

        // Añadir panel a la ventana
        add(panel);
    }
}
