package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Proyecto1.Profesor;
import Proyecto1.LearningPath;
import Persistencia.ArchivoPersistencia;

public class CrearLearningPathGui extends JFrame {
    public CrearLearningPathGui(Profesor profesor) {
        setTitle("Crear Learning Path");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos para el Learning Path
        JTextField txtTitulo = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtObjetivo = new JTextField();
        JTextField txtNivelDificultad = new JTextField();
        JTextField txtTiempoEstimado = new JTextField();

        // Botón para crear el Learning Path
        JButton btnCrear = new JButton("Crear");

        // Etiquetas y campos
        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);
        panel.add(new JLabel("Tipo (curso, examen, etc.):"));
        panel.add(txtTipo);
        panel.add(new JLabel("Objetivo:"));
        panel.add(txtObjetivo);
        panel.add(new JLabel("Nivel de Dificultad:"));
        panel.add(txtNivelDificultad);
        panel.add(new JLabel("Tiempo Estimado (en horas):"));
        panel.add(txtTiempoEstimado);
        panel.add(btnCrear);

        // Acción del botón
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String titulo = txtTitulo.getText();
                    String descripcion = txtDescripcion.getText();
                    String tipo = txtTipo.getText();
                    String objetivo = txtObjetivo.getText();
                    String nivelDificultad = txtNivelDificultad.getText();
                    double tiempoEstimado = Double.parseDouble(txtTiempoEstimado.getText());

                    // Crear Learning Path y añadirlo al profesor
                    int nuevoId = ArchivoPersistencia.cargarLearningPaths().size() + 1; // Generar ID único
                    LearningPath nuevoLP = new LearningPath(nuevoId, profesor.getIdProfesor(), titulo, descripcion, tipo, objetivo, nivelDificultad, tiempoEstimado);
                    profesor.crearLearningPath(nuevoLP);

                    // Guardar el Learning Path en persistencia
                    ArchivoPersistencia.guardarLearningPath(nuevoLP);

                    JOptionPane.showMessageDialog(null, "Learning Path creado exitosamente.");
                    dispose(); // Cerrar la ventana actual
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa un tiempo estimado válido.");
                }
            }
        });

        // Añadir panel a la ventana
        add(panel);
    }
}
