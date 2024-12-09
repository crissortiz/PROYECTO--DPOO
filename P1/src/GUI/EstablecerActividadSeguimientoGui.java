package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Proyecto1.Profesor;
import Proyecto1.LearningPath;
import Proyecto1.Actividad;

public class EstablecerActividadSeguimientoGui extends JFrame {
    public EstablecerActividadSeguimientoGui(Profesor profesor) {
        setTitle("Establecer Actividades de Seguimiento");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Lista de Learning Paths del profesor
        DefaultListModel<String> listModelLearningPaths = new DefaultListModel<>();
        for (LearningPath lp : profesor.getLearningPaths()) {
            listModelLearningPaths.addElement("ID: " + lp.getId() + " - " + lp.getTitulo());
        }
        JList<String> listaLearningPaths = new JList<>(listModelLearningPaths);
        listaLearningPaths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Panel para las actividades del Learning Path seleccionado
        DefaultListModel<String> listModelActividadesBase = new DefaultListModel<>();
        JList<String> listaActividadesBase = new JList<>(listModelActividadesBase);
        listaActividadesBase.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultListModel<String> listModelActividadesSeguimiento = new DefaultListModel<>();
        JList<String> listaActividadesSeguimiento = new JList<>(listModelActividadesSeguimiento);
        listaActividadesSeguimiento.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JTextArea areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        JScrollPane scrollDetalles = new JScrollPane(areaDetalles);

        // Botones
        JButton btnEstablecer = new JButton("Establecer Seguimiento");
        JButton btnCerrar = new JButton("Cerrar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnEstablecer);
        panelBotones.add(btnCerrar);

        // Acción al seleccionar un Learning Path
        listaLearningPaths.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = listaLearningPaths.getSelectedIndex();
                if (index >= 0) {
                    LearningPath seleccionado = profesor.getLearningPaths().get(index);
                    cargarActividades(seleccionado, listModelActividadesBase, listModelActividadesSeguimiento);
                }
            }
        });

        // Acción para establecer actividades de seguimiento
        btnEstablecer.addActionListener(e -> {
            int indexLP = listaLearningPaths.getSelectedIndex();
            int indexBase = listaActividadesBase.getSelectedIndex();
            int[] selectedIndicesArray = listaActividadesSeguimiento.getSelectedIndices();
            List<Integer> indicesSeguimiento = new ArrayList<>();
            for (int index : selectedIndicesArray) {
                indicesSeguimiento.add(index);
            }

            if (indexLP >= 0 && indexBase >= 0 && !indicesSeguimiento.isEmpty()) {
                LearningPath lpSeleccionado = profesor.getLearningPaths().get(indexLP);
                Actividad actividadBase = lpSeleccionado.getActividades().get(indexBase);

                List<Integer> idsSeguimiento = new ArrayList<>();
                for (int index : indicesSeguimiento) {
                    idsSeguimiento.add(lpSeleccionado.getActividades().get(index).getId());
                }

                profesor.establecerActividadesSeguimiento(lpSeleccionado, actividadBase.getId(), idsSeguimiento);
                JOptionPane.showMessageDialog(null, "Actividades de seguimiento establecidas exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un Learning Path, una actividad base y al menos una actividad de seguimiento.");
            }
        });


        // Acción para cerrar la ventana
        btnCerrar.addActionListener(e -> dispose());

        // Panel izquierdo
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(new JLabel("Learning Paths:"), BorderLayout.NORTH);
        panelIzquierdo.add(new JScrollPane(listaLearningPaths), BorderLayout.CENTER);

        // Panel central
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCentral.add(new JScrollPane(listaActividadesBase));
        panelCentral.add(new JScrollPane(listaActividadesSeguimiento));

        // Panel principal
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelCentral, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarActividades(LearningPath lp, DefaultListModel<String> modelBase, DefaultListModel<String> modelSeguimiento) {
        modelBase.clear();
        modelSeguimiento.clear();

        for (Actividad actividad : lp.getActividades()) {
            String detalle = "ID: " + actividad.getId() + " - " + actividad.getNombre();
            modelBase.addElement(detalle);
            modelSeguimiento.addElement(detalle);
        }
    }
}
