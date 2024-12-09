package GUI;

import Proyecto1.Estudiante;
import Proyecto1.LearningPath;
import Persistencia.ArchivoPersistencia;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VerPerfilEstudianteGui extends JFrame {
    public VerPerfilEstudianteGui(Estudiante estudiante) {
        setTitle("Perfil de Estudiante - " + estudiante.getNombreUsuario());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Información del estudiante
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        StringBuilder info = new StringBuilder();
        info.append("Nombre de Usuario: ").append(estudiante.getNombreUsuario()).append("\n");
        info.append("Correo: ").append(estudiante.getCorreo()).append("\n\n");

        // Cargar todos los Learning Paths
        List<LearningPath> allLearningPaths = ArchivoPersistencia.cargarLearningPaths();
        List<LearningPath> learningPathsInscritos = estudiante.getLearningPaths(allLearningPaths);

        if (learningPathsInscritos.isEmpty()) {
            info.append("No estás inscrito en ningún Learning Path.\n");
        } else {
            info.append("Learning Paths inscritos:\n");
            for (LearningPath lp : learningPathsInscritos) {
                info.append("- ").append(lp.getTitulo()).append(" (Progreso: ")
                    .append(estudiante.getProgreso(lp.getId()).getPorcentajeCompletado()).append("%)\n");
            }
        }

        infoArea.setText(info.toString());
        panel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        add(panel);
    }
}

