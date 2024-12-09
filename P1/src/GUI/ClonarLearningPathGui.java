package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import Proyecto1.Profesor;
import Proyecto1.LearningPath;
import Persistencia.ArchivoPersistencia;

public class ClonarLearningPathGui extends JFrame {
    public ClonarLearningPathGui(Profesor profesor) {
        setTitle("Clonar Learning Path");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Lista de Learning Paths disponibles
        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<LearningPath> todosLearningPaths = ArchivoPersistencia.cargarLearningPaths();
        for (LearningPath lp : todosLearningPaths) {
            if (lp.getIdProfesor() != profesor.getIdProfesor()) { // Filtrar por otros profesores
                listModel.addElement("ID: " + lp.getId() + " - " + lp.getTitulo());
            }
        }
        JList<String> listaLearningPaths = new JList<>(listModel);

        JButton btnClonar = new JButton("Clonar");
        JButton btnCerrar = new JButton("Cerrar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnClonar);
        panelBotones.add(btnCerrar);

        btnClonar.addActionListener(e -> {
            int index = listaLearningPaths.getSelectedIndex();
            if (index >= 0) {
                LearningPath seleccionado = todosLearningPaths.get(index);
                int nuevoId = profesor.getLearningPaths().size() + 1; // Generar nuevo ID
                profesor.clonarLearningPath(seleccionado, nuevoId);

                ArchivoPersistencia.actualizarLearningPaths(todosLearningPaths);
                JOptionPane.showMessageDialog(null, "Learning Path clonado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un Learning Path para clonar.");
            }
        });

        btnCerrar.addActionListener(e -> dispose());

        panel.add(new JScrollPane(listaLearningPaths), BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }
}
