package GUI;

import Proyecto1.Estudiante;
import Proyecto1.LearningPath;
import Proyecto1.ProgresoEstudiante;
import Proyecto1.Actividad;
import Persistencia.ArchivoPersistencia;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VerProgresoGui extends JFrame {
    public VerProgresoGui(Estudiante estudiante) {
        setTitle("Progreso de Estudiante - " + estudiante.getNombreUsuario());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Información del progreso
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        StringBuilder info = new StringBuilder();
        List<LearningPath> allLearningPaths = ArchivoPersistencia.cargarLearningPaths();
        List<LearningPath> learningPathsInscritos = estudiante.getLearningPaths(allLearningPaths);

        if (learningPathsInscritos.isEmpty()) {
            info.append("No estás inscrito en ningún Learning Path.\n");
        } else {
            info.append("Progreso en Learning Paths:\n");
            for (LearningPath lp : learningPathsInscritos) {
                ProgresoEstudiante progreso = estudiante.getProgreso(lp.getId());
                info.append("- ").append(lp.getTitulo()).append(" (Progreso: ")
                    .append(progreso.getPorcentajeCompletado()).append("%)\n");

                info.append("  Actividades completadas:\n");
                for (Actividad actividad : progreso.getActividadesCompletadas()) {
                    info.append("    - ").append(actividad.getNombre()).append("\n");
                }

                info.append("  Actividades pendientes:\n");
                for (Actividad actividad : progreso.getActividadesPendientes()) {
                    info.append("    - ").append(actividad.getNombre()).append("\n");
                }
            }
        }

        infoArea.setText(info.toString());
        panel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        add(panel);
    }
}
