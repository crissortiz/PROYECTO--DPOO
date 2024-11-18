package Proyecto1;
public class Reseña {
    private String comentario;
    private int calificacion; 
    private String nombreEstudiante;

    public Reseña(String comentario, int calificacion, String nombreEstudiante) {
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getComentario() {
        return comentario;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    @Override
    public String toString() {
        return "Reseña de " + nombreEstudiante + ": " + comentario + " (Calificación: " + calificacion + ")";
    }
}

