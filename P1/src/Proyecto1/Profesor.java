package Proyecto1;

import java.util.ArrayList;
import java.util.List;

public class Profesor extends Usuario {
    private int idProfesor;
    private List<LearningPath> learningPaths;
    private List<Estudiante> estudiantes;

    public Profesor(String nombreUsuario, String contrasena, String correo, int idProfesor) {
        super(nombreUsuario, contrasena, correo);
        this.idProfesor = idProfesor;
        this.learningPaths = new ArrayList<>();
        this.estudiantes = new ArrayList<>();
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public List<LearningPath> getLearningPaths() {
        return learningPaths;
    }

    public void crearLearningPath(LearningPath lp) {
        learningPaths.add(lp);
    }


    public void eliminarLearningPath(LearningPath lp) {
        learningPaths.remove(lp);
    }

    public void listarLearningPaths() {
        System.out.println("Learning Paths creados por el profesor:");
        for (LearningPath lp : learningPaths) {
            System.out.println("Título: " + lp.getTitulo() + ", Descripción: " + lp.getDescripcion());
        }
    }
    public void calificarEvaluacion(Quiz quiz, List<String> respuestasEstudiante, List<String> respuestasCorrectas) {
        int puntaje = 0;

        for (int i = 0; i < respuestasEstudiante.size(); i++) {
            if (respuestasEstudiante.get(i).equals(respuestasCorrectas.get(i))) {
                puntaje++;
            }
        }

        boolean aprobado = quiz.esAprobado(puntaje);

        System.out.println("Calificación para el quiz: " + puntaje + " respuestas correctas.");
        System.out.println("Resultado: " + (aprobado ? "Aprobado" : "No aprobado"));
    }
    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }
    public List<ProgresoEstudiante> verProgresoEstudiantes(int idLearningPath) {
        List<ProgresoEstudiante> progresoEstudiantes = new ArrayList<>();
    
        for (Estudiante estudiante : estudiantes) {
            ProgresoEstudiante progreso = estudiante.getProgreso(idLearningPath);
            if (progreso != null) { 
                progresoEstudiantes.add(progreso);
            }
        }
    
        return progresoEstudiantes;
    }
    public void asignarActividad(int idLearningPath, Actividad actividad) {
        for (LearningPath lp : learningPaths) {
            if (lp.getId() == idLearningPath) {
                lp.agregarActividad(actividad);
                System.out.println("Actividad asignada al Learning Path: " + lp.getTitulo());
                return;
            }
        }
        System.out.println("Learning Path con ID " + idLearningPath + " no encontrado.");
    }
    public void modificarLearningPath(int idLearningPath, String nuevoTitulo, String nuevaDescripcion, String nuevoTipo, String nuevoObjetivo, String nuevoNivelDificultad, double nuevoTiempoEstimado) {
        for (LearningPath lp : learningPaths) {
            if (lp.getId() == idLearningPath) {
                lp.setTitulo(nuevoTitulo);
                lp.setDescripcion(nuevaDescripcion);
                lp.setTipo(nuevoTipo);
                lp.setObjetivo(nuevoObjetivo);
                lp.setNivelDificultad(nuevoNivelDificultad);
                lp.setTiempoEstimado(nuevoTiempoEstimado);
                System.out.println("Learning Path modificado: " + lp.getTitulo());
                return;
            }
        }
        System.out.println("Learning Path con ID " + idLearningPath + " no encontrado.");
    }
    
    
    public LearningPath buscarLearningPathPorId(int id) {
        for (LearningPath lp : learningPaths) {
            if (lp.getId() == id) {
                return lp; // Retorna el Learning Path si lo encuentra
            }
        }
        return null; // Retorna null si no lo encuentra
    }
    
    public void calificarActividad(String tituloLearningPath, String nombreActividad, double calificacion) {
        for (LearningPath lp : learningPaths) {
            if (lp.getTitulo().equals(tituloLearningPath)) {
                for (Actividad actividad : lp.getActividades()) { // Busca actividades en el Learning Path
                    if (actividad.getNombre().equals(nombreActividad)) {
                        actividad.calificar(calificacion);
                        System.out.println("Actividad '" + nombreActividad + "' en el Learning Path '" + tituloLearningPath + "' calificada como: " + actividad.getResultado());
                        return;
                    }
                }
                System.out.println("Actividad no encontrada en el Learning Path: " + tituloLearningPath);
                return;
            }
        }
        System.out.println("Learning Path no encontrado: " + tituloLearningPath);
    }

    public void revisarTasaDeExito(String tituloLearningPath) {
        for (LearningPath lp : learningPaths) {
            if (lp.getTitulo().equals(tituloLearningPath)) {
                double tasaDeExito = lp.calcularTasaDeExito();
                System.out.println("Tasa de éxito del Learning Path '" + tituloLearningPath + "': " + tasaDeExito + "%");
                return;
            }
        }
        System.out.println("Learning Path no encontrado: " + tituloLearningPath);
    }
    
    
    public void verLearningPathsCreados() {
        System.out.println("\n=== Learning Paths creados por el Profesor ===");
        // Filtrar los Learning Paths creados por este profesor
        List<LearningPath> learningPathsCreados = learningPaths.stream()
                .filter(lp -> lp.getIdProfesor() == this.idProfesor)
                .toList();

        if (learningPathsCreados.isEmpty()) {
            System.out.println("No hay Learning Paths creados por usted.");
            return;
        }

        for (LearningPath lp : learningPathsCreados) {
            System.out.println("---------------------------------------------");
            System.out.println("ID: " + lp.getId());
            System.out.println("Título: " + lp.getTitulo());
            System.out.println("Descripción: " + lp.getDescripcion());
            System.out.println("Tipo: " + lp.getTipo());
            System.out.println("Objetivo: " + lp.getObjetivo());
            System.out.println("Nivel de Dificultad: " + lp.getNivelDificultad());
            System.out.println("Tiempo Estimado: " + lp.getTiempoEstimado() + " horas");

            // Mostrar actividades
            if (!lp.getActividades().isEmpty()) {
                System.out.println("Actividades:");
                for (Actividad actividad : lp.getActividades()) {
                    System.out.println("\t- ID: " + actividad.getId());
                    System.out.println("\t  Nombre: " + actividad.getNombre());
                    System.out.println("\t  Tipo: " + actividad.getTipo());
                    System.out.println("\t  Descripción: " + actividad.getDescripcion());
                    System.out.println("\t  Duración: " + actividad.getDuracion() + " minutos");
                }
            } else {
                System.out.println("No hay actividades asociadas a este Learning Path.");
            }
        }
        System.out.println("---------------------------------------------");
    }

    
    
}

