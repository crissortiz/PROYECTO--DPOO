package Proyecto1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LearningPath {
    private int id;
    private String titulo;
    private String descripcion;
    private String tipo; 
    private String objetivo; 
    private String nivelDificultad; 
    private double tiempoEstimado; 
    private List<Actividad> actividades;
    private Reseña ultimaReseña;
    private List<Reseña> resenas;
    
    private List<Estudiante> estudiantesInscritos;

    public LearningPath(int id, String titulo, String descripcion, String tipo, String objetivo, String nivelDificultad, double tiempoEstimado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.objetivo = objetivo;
        this.nivelDificultad = nivelDificultad;
        this.tiempoEstimado = tiempoEstimado;
        this.actividades = new ArrayList<>();
        this.resenas = new ArrayList<>();
        this.estudiantesInscritos = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(String nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public double getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(double tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }
    
    public void agregarResena(Reseña resena) {
        resenas.add(resena);
    }
    
    public List<Reseña> getResenas() {
        return resenas;
    }
    
    public void agregarEstudiantesInscritos(Reseña estudiantesInscritos) {
        resenas.add(estudiantesInscritos);
    }
    
    public List<Estudiante> getEstudiantesInscritos() {
     return estudiantesInscritos;
    }
    
    public void agregarActividad(Actividad actividad) {
        actividades.add(actividad);
    }

    public Reseña getUltimaReseña() {
        return ultimaReseña;
    }

    
    public void agregarEstudiante(Estudiante estudiante) {
        this.estudiantesInscritos.add(estudiante);
    }
    
    public void eliminarActividad(int idActividad) {
        actividades.removeIf(actividad -> actividad.getId() == idActividad);
    }

    public double calcularProgreso(Estudiante estudiante) {
        ProgresoEstudiante progreso = estudiante.getProgreso(this.id);
        if (progreso == null) {
            return 0.0;
        }
        long actividadesCompletadas = actividades.stream()
            .filter(actividad -> progreso.getPorcentajeCompletado() > 0)
            .count();
        return (double) actividadesCompletadas / actividades.size() * 100;
    }

    
    public List<Actividad> generarRecomendaciones(Estudiante estudiante) {
        ProgresoEstudiante progreso = estudiante.getProgreso(this.id);
        if (progreso == null) {
            return new ArrayList<>();
        }
        return actividades.stream()
            .filter(actividad -> progreso.getPorcentajeCompletado() == 0)
            .collect(Collectors.toList());
    }
    
    public LearningPath clonar() {
        return new LearningPath(
            this.id,
            this.titulo + " (Copia)",
            this.descripcion,
            this.tipo,
            this.objetivo,
            this.nivelDificultad,
            this.tiempoEstimado
        );
    }
    
    public double calcularTasaDeExito() {
        long actividadesExitosas = actividades.stream()
            .filter(actividad -> "aprobado".equalsIgnoreCase(actividad.getResultado()))
            .count();
        return (double) actividadesExitosas / actividades.size() * 100;
    }
}



