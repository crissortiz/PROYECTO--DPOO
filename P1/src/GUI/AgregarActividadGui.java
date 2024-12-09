package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import Proyecto1.Profesor;
import Proyecto1.LearningPath;
import Proyecto1.Actividad;
import Persistencia.ArchivoPersistencia;

public class AgregarActividadGui extends JFrame {
    public AgregarActividadGui(Profesor profesor) {
        setTitle("Agregar Actividad");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos para la actividad
        JTextField txtIdLearningPath = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JTextField txtDuracion = new JTextField();

        JButton btnAgregar = new JButton("Agregar");

        panel.add(new JLabel("ID del Learning Path:"));
        panel.add(txtIdLearningPath);
        panel.add(new JLabel("Nombre de la Actividad:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Tipo:"));
        panel.add(txtTipo);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);
        panel.add(new JLabel("Duración (en minutos):"));
        panel.add(txtDuracion);
        panel.add(btnAgregar);

        // Acción del botón
        btnAgregar.addActionListener(e -> {
            try {
                int idLearningPath = Integer.parseInt(txtIdLearningPath.getText());
                String nombre = txtNombre.getText();
                String tipo = txtTipo.getText();
                String descripcion = txtDescripcion.getText();
                int duracion = Integer.parseInt(txtDuracion.getText());

                LearningPath lp = profesor.buscarLearningPathPorId(idLearningPath);
                if (lp != null) {
                    int idActividad = lp.getActividades().size() + 1; // Generar ID único
                    Actividad nuevaActividad = new Actividad(nombre, tipo, idActividad, descripcion, "", "", duracion);
                    lp.agregarActividad(nuevaActividad);

                    ArchivoPersistencia.actualizarLearningPaths(profesor.getLearningPaths());
                    JOptionPane.showMessageDialog(null, "Actividad agregada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Learning Path no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingresa valores válidos.");
            }
        });

        // Añadir panel a la ventana
        add(panel);
    }
}
