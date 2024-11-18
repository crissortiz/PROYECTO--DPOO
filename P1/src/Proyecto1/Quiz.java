package Proyecto1;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private List<String> preguntas;       
    private double calificacionMinima;    

    public Quiz(double calificacionMinima) {
        this.preguntas = new ArrayList<>();
        this.calificacionMinima = calificacionMinima;
    }

    public void agregarPregunta(String pregunta) {
        preguntas.add(pregunta);
    }

    public boolean esAprobado(double puntajeObtenido) {
        return puntajeObtenido >= calificacionMinima;
    }

    public List<String> getPreguntas() {
        return preguntas;
    }

    public double getCalificacionMinima() {
        return calificacionMinima;
    }
}
