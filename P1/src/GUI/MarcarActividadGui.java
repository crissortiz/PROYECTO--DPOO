package GUI;

import Proyecto1.Estudiante;
import Proyecto1.LearningPath;
import Proyecto1.ProgresoEstudiante;
import Proyecto1.Actividad;
import Persistencia.ArchivoPersistencia;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MarcarActividadGui extends JFrame {
    private JComboBox<LearningPath> comboLearningPaths;
    private JComboBox<Actividad> comboActividades;
    private JButton btnIniciarActividad;

    public MarcarActividadGui(Estudiante estudiante) {
        setTitle("Marcar Inicio de Actividad - " + estudiante.getNombreUsuario());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Cargar Learning Paths inscritos
        List<LearningPath> allLearningPaths = ArchivoPersistencia.cargarLearningPaths();
        List<LearningPath> learningPathsInscritos = estudiante.getLearningPaths(allLearningPaths);

        // ComboBox de Learning Paths
        comboLearningPaths = new JComboBox<>(learningPathsInscritos.toArray(new LearningPath[0]));
        comboLearningPaths.addActionListener(e -> cargarActividades());

        // ComboBox de Actividades
        comboActividades = new JComboBox<>();

        // Botón para marcar la actividad
        btnIniciarActividad = new JButton("Marcar Inicio de Actividad");
        btnIniciarActividad.addActionListener(e -> iniciarActividad(estudiante));

        // Añadir componentes al panel
        JPanel panelSeleccion = new JPanel();
        panelSeleccion.add(new JLabel("Selecciona un Learning Path:"));
        panelSeleccion.add(comboLearningPaths);
        panel.add(panelSeleccion, BorderLayout.NORTH);

        JPanel panelActividades = new JPanel();
        panelActividades.add(new JLabel("Selecciona una Actividad:"));
        panelActividades.add(comboActividades);
        panel.add(panelActividades, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnIniciarActividad);
        panel.add(panelBoton, BorderLayout.SOUTH);

        add(panel);

        // Cargar actividades inicialmente
        if (learningPathsInscritos.size() > 0) {
            cargarActividades();
        }
    }

    private void cargarActividades() {
        LearningPath selectedLP = (LearningPath) comboLearningPaths.getSelectedItem();
        List<Actividad> actividades = selectedLP != null ? selectedLP.getActividades() : List.of();

        comboActividades.removeAllItems();
        for (Actividad actividad : actividades) {
            comboActividades.addItem(actividad);
        }
    }

    private void iniciarActividad(Estudiante estudiante) {
        LearningPath selectedLP = (LearningPath) comboLearningPaths.getSelectedItem();
        Actividad selectedActividad = (Actividad) comboActividades.getSelectedItem();

        if (selectedLP != null && selectedActividad != null) {
            ProgresoEstudiante progreso = estudiante.getProgreso(selectedLP.getId());
            boolean actividadIniciada = progreso.iniciarActividad(selectedActividad.getNombre());

            if (actividadIniciada) {
                JOptionPane.showMessageDialog(this, "Actividad '" + selectedActividad.getNombre() + "' iniciada.");
            } else {
                JOptionPane.showMessageDialog(this, "La actividad no se pudo iniciar.");
            }
        }
    }
}
