package uniandes.dpoo.core.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Proyecto1.LearningPath;
import Proyecto1.Actividad;
import Proyecto1.Estudiante;
import Proyecto1.ProgresoEstudiante;

public class LearningPathTest {
	
	private LearningPath learningPath;
    private Actividad actividad1;
    private Actividad actividad2;
    private Actividad actividad3;
    private Estudiante estudiante;

    @BeforeEach
    void setUp() {
        learningPath = new LearningPath(1, "Java Basics", "Curso básico de Java", "Curso", "Aprender Java", "Principiante", 20.0);

        actividad1 = new Actividad("Introducción a Java", "Video", 101, "Conceptos básicos", "Aprender fundamentos", "Básico", 30);
        actividad2 = new Actividad("Sintaxis en Java", "PDF", 102, "Sintaxis y variables", "Aprender sintaxis de Java", "Básico", 20);
        actividad3 = new Actividad("POO en Java", "Video", 103, "Conceptos de POO", "Aprender POO en Java", "Intermedio", 40);

        learningPath.agregarActividad(actividad1);
        learningPath.agregarActividad(actividad2);
        learningPath.agregarActividad(actividad3);

        estudiante = new Estudiante("Carlos", "password123", "carlos@example.com", 101);
    }

    @AfterEach
    void tearDown() {
        learningPath = null;
        actividad1 = null;
        actividad2 = null;
        actividad3 = null;
        estudiante = null;
    }

    @Test
    void testEliminarActividad() {
        // Verificar que las actividades están agregadas
        assertEquals(3, learningPath.getActividades().size(), "Debería haber 3 actividades inicialmente.");

        // Eliminar una actividad y verificar el resultado
        learningPath.eliminarActividad(actividad2.getId());
        assertEquals(2, learningPath.getActividades().size(), "Debería haber 2 actividades después de eliminar una.");
        assertFalse(learningPath.getActividades().contains(actividad2), "La actividad eliminada no debería estar en la lista.");
    }

    @Test
    void testCalcularProgreso() {
        estudiante.inscribirseEnLearningPath(learningPath);
        
        // Completar una actividad y calcular el progreso
        estudiante.completarActividad(learningPath.getId(), actividad1.getNombre());
        double progreso = learningPath.calcularProgreso(estudiante);
        
        // Esperamos que el progreso sea aproximadamente el 33.33% (1 de 3 actividades)
        assertEquals(33.33, progreso, 0.01, "El progreso debería ser aproximadamente 33.33% tras completar una de tres actividades.");

        // Completar otra actividad y recalcular el progreso
        estudiante.completarActividad(learningPath.getId(), actividad2.getNombre());
        progreso = learningPath.calcularProgreso(estudiante);
        
        // Esperamos que el progreso sea aproximadamente el 66.67% (2 de 3 actividades)
        assertEquals(66.67, progreso, 0.01, "El progreso debería ser aproximadamente 66.67% tras completar dos de tres actividades.");
    }

    @Test
    void testGenerarRecomendaciones() {
        estudiante.inscribirseEnLearningPath(learningPath);

        // Completar una actividad y generar recomendaciones
        estudiante.completarActividad(learningPath.getId(), actividad1.getNombre());
        List<Actividad> recomendaciones = learningPath.generarRecomendaciones(estudiante);
        
        // Deberíamos obtener las actividades que no se han completado
        assertEquals(2, recomendaciones.size(), "Debería haber 2 actividades recomendadas después de completar una.");
        assertTrue(recomendaciones.contains(actividad2), "La actividad no completada debería estar en la lista de recomendaciones.");
        assertTrue(recomendaciones.contains(actividad3), "La actividad no completada debería estar en la lista de recomendaciones.");
    }

}
