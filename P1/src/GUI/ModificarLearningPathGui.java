package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Proyecto1.Profesor;
import Proyecto1.LearningPath;
import Persistencia.ArchivoPersistencia;
import java.util.List;

public class ModificarLearningPathGui extends JFrame {
    public ModificarLearningPathGui(Profesor profesor) {
        setTitle("Modificar Learning Path");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Lista de Learning Paths del profesor
        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<LearningPath> learningPaths = profesor.getLearningPaths();
        for (LearningPath lp : learningPaths) {
            listModel.addElement("ID: " + lp.getId() + " - " + lp.getTitulo());
        }
        JList<String> listaLearningPaths = new JList<>(listModel);

        // Detalles del Learning Path seleccionado
        JTextField txtTitulo = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtObjetivo = new JTextField();
        JTextField txtNivelDificultad = new JTextField();
        JTextField txtTiempoEstimado = new JTextField();

        JButton btnModificar = new JButton("Modificar");
        JButton btnCerrar = new JButton("Cerrar");

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.add(new JLabel("Título:"));
        formPanel.add(txtTitulo);
        formPanel.add(new JLabel("Descripción:"));
        formPanel.add(txtDescripcion);
        formPanel.add(new JLabel("Tipo:"));
        formPanel.add(txtTipo);
        formPanel.add(new JLabel("Objetivo:"));
        formPanel.add(txtObjetivo);
        formPanel.add(new JLabel("Nivel de Dificultad:"));
        formPanel.add(txtNivelDificultad);
        formPanel.add(new JLabel("Tiempo Estimado:"));
        formPanel.add(txtTiempoEstimado);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnCerrar);

        // Acciones
        listaLearningPaths.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = listaLearningPaths.getSelectedIndex();
                if (index >= 0) {
                    LearningPath seleccionado = learningPaths.get(index);
                    txtTitulo.setText(seleccionado.getTitulo());
                    txtDescripcion.setText(seleccionado.getDescripcion());
                    txtTipo.setText(seleccionado.getTipo());
                    txtObjetivo.setText(seleccionado.getObjetivo());
                    txtNivelDificultad.setText(seleccionado.getNivelDificultad());
                    txtTiempoEstimado.setText(String.valueOf(seleccionado.getTiempoEstimado()));
                }
            }
        });

        btnModificar.addActionListener(e -> {
            int index = listaLearningPaths.getSelectedIndex();
            if (index >= 0) {
                try {
                    LearningPath seleccionado = learningPaths.get(index);
                    seleccionado.setTitulo(txtTitulo.getText());
                    seleccionado.setDescripcion(txtDescripcion.getText());
                    seleccionado.setTipo(txtTipo.getText());
                    seleccionado.setObjetivo(txtObjetivo.getText());
                    seleccionado.setNivelDificultad(txtNivelDificultad.getText());
                    seleccionado.setTiempoEstimado(Double.parseDouble(txtTiempoEstimado.getText()));

                    ArchivoPersistencia.actualizarLearningPaths(learningPaths);
                    JOptionPane.showMessageDialog(null, "Learning Path modificado exitosamente.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa un tiempo estimado válido.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un Learning Path.");
            }
        });

        btnCerrar.addActionListener(e -> dispose());

        // Estructura de la ventana
        panel.add(new JScrollPane(listaLearningPaths), BorderLayout.WEST);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
    }
}
