package GUI;

import javax.swing.*;
import java.awt.*;
import Proyecto1.Profesor;
import Proyecto1.LearningPath;
import Proyecto1.Actividad;

public class VerLearningPathsGui extends JFrame {
    public VerLearningPathsGui(Profesor profesor) {
        setTitle("Ver Learning Paths");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Modelo para la lista de Learning Paths
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (LearningPath lp : profesor.getLearningPaths()) {
            listModel.addElement("ID: " + lp.getId() + " - " + lp.getTitulo());
        }
        JList<String> listaLearningPaths = new JList<>(listModel);
        listaLearningPaths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Panel para mostrar detalles del Learning Path seleccionado
        JTextArea areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        JScrollPane scrollDetalles = new JScrollPane(areaDetalles);

        // Acción al seleccionar un Learning Path
        listaLearningPaths.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = listaLearningPaths.getSelectedIndex();
                if (index >= 0) {
                    LearningPath seleccionado = profesor.getLearningPaths().get(index);
                    mostrarDetallesLearningPath(seleccionado, areaDetalles);
                }
            }
        });

        // Botón para cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCerrar);

        // Añadir componentes al panel principal
        panel.add(new JScrollPane(listaLearningPaths), BorderLayout.WEST);
        panel.add(scrollDetalles, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        // Añadir panel a la ventana
        add(panel);
    }

    private void mostrarDetallesLearningPath(LearningPath lp, JTextArea areaDetalles) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("Título: ").append(lp.getTitulo()).append("\n");
        detalles.append("Descripción: ").append(lp.getDescripcion()).append("\n");
        detalles.append("Tipo: ").append(lp.getTipo()).append("\n");
        detalles.append("Objetivo: ").append(lp.getObjetivo()).append("\n");
        detalles.append("Nivel de Dificultad: ").append(lp.getNivelDificultad()).append("\n");
        detalles.append("Tiempo Estimado: ").append(lp.getTiempoEstimado()).append(" horas\n\n");

        detalles.append("Actividades:\n");
        if (lp.getActividades().isEmpty()) {
            detalles.append("  No hay actividades asociadas a este Learning Path.\n");
        } else {
            for (Actividad actividad : lp.getActividades()) {
                detalles.append("  - ID: ").append(actividad.getId()).append("\n");
                detalles.append("    Nombre: ").append(actividad.getNombre()).append("\n");
                detalles.append("    Tipo: ").append(actividad.getTipo()).append("\n");
                detalles.append("    Descripción: ").append(actividad.getDescripcion()).append("\n");
                detalles.append("    Duración: ").append(actividad.getDuracion()).append(" minutos\n");
                detalles.append("    ------------------------------\n");
            }
        }

        areaDetalles.setText(detalles.toString());
    }
}
