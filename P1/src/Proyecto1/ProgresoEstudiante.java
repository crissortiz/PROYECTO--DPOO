package Proyecto1;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProgresoEstudiante {
	private int idLearningPath; // ID del Learning Path asociado
    private double porcentajeCompletado;
    private List<Actividad> actividadesPendientes; // Actividades aún no completadas
    private List<Actividad> actividadesCompletadas; // Actividades ya completadas

    public ProgresoEstudiante(int idLearningPath) {
        this.idLearningPath = idLearningPath;
        this.porcentajeCompletado = 0.0;
        this.actividadesPendientes = new ArrayList<>();
        this.actividadesCompletadas = new ArrayList<>();
    }

    public double getPorcentajeCompletado() {
        return porcentajeCompletado;
    }

    public List<Actividad> getActividadesPendientes() {
        if (actividadesPendientes.isEmpty()) {
            System.out.println("No hay actividades pendientes en este Learning Path.");
        }
        return actividadesPendientes;
    }


    public List<Actividad> getActividadesCompletadas() {
        return actividadesCompletadas;
    }

    // Agregar actividad a la lista de pendientes
    public void agregarActividadPendiente(Actividad actividad) {
        actividadesPendientes.add(actividad);
    }

    public boolean iniciarActividad(String nombreActividad) {
        for (Actividad actividad : actividadesPendientes) {
            if (actividad.getNombre().equals(nombreActividad)) {
                actividadesPendientes.remove(actividad); // Remover de pendientes
                actividadesCompletadas.add(actividad);  // Mover a completadas
                actualizarProgreso(); // Actualizar progreso
                System.out.println("Actividad '" + nombreActividad + "' iniciada exitosamente.");
                return true;
            }
        }
        System.out.println("Actividad '" + nombreActividad + "' no encontrada o ya iniciada.");
        return false; // No se encontró la actividad
    }


    // Actualizar el porcentaje completado según las actividades realizadas
    private void actualizarProgreso() {
        if (!actividadesCompletadas.isEmpty()) {
            this.porcentajeCompletado = (double) actividadesCompletadas.size() /
                                         (actividadesPendientes.size() + actividadesCompletadas.size()) * 100;
        } else {
            this.porcentajeCompletado = 0.0; // Si no hay actividades completadas
        }
    }

    // Calcular manualmente el porcentaje completado (redundante, pero puede ser útil)
    public double calcularPorcentajeCompletado() {
        if (actividadesPendientes.isEmpty() && actividadesCompletadas.isEmpty()) {
            return 0.0;
        }
        return (double) actividadesCompletadas.size() /
               (actividadesPendientes.size() + actividadesCompletadas.size()) * 100;
    }
}

