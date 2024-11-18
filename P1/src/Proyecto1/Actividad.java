package Proyecto1;
import java.util.HashMap;
import java.util.Map;

public class Actividad {
    private String nombre;
    private String tipo; 
    private boolean completado;
    private int id;
    private double rating;
    private String descripcion;
    private String objetivo;
    private String nivelDificultad;
    private String resultado;
    private int duracion;
    private Map<Integer, String> resultadosEstudiantes;

    public Actividad(String nombre, String tipo, int id, String descripcion, String objetivo, String nivelDificultad, int duracion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.completado = false;
        this.id = id;
        this.rating = 0.0;
        this.descripcion = descripcion;
        this.objetivo = objetivo;
        this.nivelDificultad = nivelDificultad;
        this.resultado = "pendiente";
        this.duracion = duracion;
        this.resultadosEstudiantes = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void marcarCompletado() {
        this.completado = true;
    }
    public int getId() {
        return id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public void asignarAEstudiante(int idEstudiante) {
        resultadosEstudiantes.put(idEstudiante, ""); 
    }

    public void calificarActividad(int idEstudiante, String resultado) {
        if (resultadosEstudiantes.containsKey(idEstudiante)) {
            resultadosEstudiantes.put(idEstudiante, resultado);
            System.out.println("Actividad calificada para el estudiante con ID " + idEstudiante);
        } else {
            System.out.println("Estudiante no encontrado en esta actividad.");
        }
    }

    public String obtenerResultado(int idEstudiante) {
        return resultadosEstudiantes.getOrDefault(idEstudiante, "No asignado o no calificado");
    }
    
    public void calificar(double calificacion) {
        if (calificacion >= 3.0) {
            this.resultado = "aprobado";
        } else {
            this.resultado = "reprobado";
        }
    }
    
    
}

