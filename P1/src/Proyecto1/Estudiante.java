package Proyecto1;

import java.util.HashMap;
import java.util.Map;

public class Estudiante extends Usuario {
    private int idEstudiante;
    private Map<Integer, ProgresoEstudiante> progresos; 

    public Estudiante(String nombreUsuario, String contrasena, String correo, int idEstudiante) {
        super(nombreUsuario, contrasena, correo);
        this.idEstudiante = idEstudiante;
        this.progresos = new HashMap<>();
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

   
    public void inscribirseEnLearningPath(LearningPath lp) {
        if (!progresos.containsKey(lp.getId())) {
            ProgresoEstudiante progreso = new ProgresoEstudiante(lp.getActividades().size());
            progresos.put(lp.getId(), progreso);
            System.out.println("Estudiante inscrito en el Learning Path: " + lp.getTitulo());
        } else {
            System.out.println("Ya está inscrito en este Learning Path.");
        }
    }

    
    public void completarActividad(int idLearningPath, String nombreActividad) {
        ProgresoEstudiante progreso = progresos.get(idLearningPath);
        if (progreso != null) {
            boolean actividadCompletada = progreso.completarActividad(nombreActividad);
            if (actividadCompletada) {
                System.out.println("Actividad '" + nombreActividad + "' completada con éxito en el Learning Path con ID " + idLearningPath + ".");
            } else {
                System.out.println("No se encontró la actividad '" + nombreActividad + "' en este Learning Path o ya estaba completada.");
            }
        } else {
            System.out.println("No está inscrito en el Learning Path con ID " + idLearningPath + ".");
        }
    }

    
    public ProgresoEstudiante getProgreso(int idLearningPath) {
        return progresos.get(idLearningPath);
    }



	public void hacerReseña(LearningPath lp, String comentario, int calificacion) {
	    if (progresos.containsKey(lp.getId())) {
	        Reseña reseña = new Reseña(comentario, calificacion, this.getNombreUsuario());
	        lp.agregarResena(reseña);
	        System.out.println("Reseña añadida al Learning Path: " + lp.getTitulo());
	    } else {
	        System.out.println("Debe estar inscrito en el Learning Path para hacer una reseña.");
	    }
	}
	
	public void verProgresoEnLearningPath(LearningPath lp) {
	    double progreso = lp.calcularProgreso(this);
	    if (progreso > 0) {
	        System.out.println("Progreso en " + lp.getTitulo() + ": " + progreso + "%");
	    } else {
	        System.out.println("No estás inscrito en este Learning Path o no has comenzado.");
	    }
	}


}
