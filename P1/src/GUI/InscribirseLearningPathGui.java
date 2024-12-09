package GUI;

import Proyecto1.Estudiante;
import Proyecto1.LearningPath;
import Persistencia.ArchivoPersistencia;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InscribirseLearningPathGui extends JFrame {
    public InscribirseLearningPathGui(Estudiante estudiante) {
        setTitle("Inscribirse en un Learning Path");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<LearningPath> learningPaths = ArchivoPersistencia.cargarLearningPaths();

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Lista de Learning Paths
        DefaultListModel<String> model = new DefaultListModel<>();
        for (LearningPath lp : learningPaths) {
            model.addElement(lp.getId() + ": " + lp.getTitulo());
        }
        JList<String> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        // Botón de inscripción
        JButton btnInscribirse = new JButton("Inscribirse");
        panel.add(btnInscribirse, BorderLayout.SOUTH);

        add(panel);

        btnInscribirse.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex >= 0) {
                LearningPath lpSeleccionado = learningPaths.get(selectedIndex);
                estudiante.inscribirseEnLearningPath(lpSeleccionado);
                ArchivoPersistencia.actualizarEstudiantes(ArchivoPersistencia.cargarEstudiantes());
                JOptionPane.showMessageDialog(null, "Inscrito exitosamente en: " + lpSeleccionado.getTitulo());
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un Learning Path.");
            }
        });
    }
}
