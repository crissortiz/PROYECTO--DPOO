package uniandes.dpoo.core.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import Proyecto1.Estudiante;
import Proyecto1.LearningPath;
import Proyecto1.ProgresoEstudiante;
import Proyecto1.Actividad;
import Proyecto1.Reseña;

public class EstudianteTest {

    private Estudiante estudiante;
    private LearningPath learningPath;
    private Actividad actividad1;
    private Actividad actividad2;

    @BeforeEach
    void setUp() {
        estudiante = new Estudiante("Carlos", "password123", "carlos@example.com", 101);
        learningPath = new LearningPath(1, "Java Basics", "Curso básico de Java", "Curso", "Aprender Java", "Principiante", 20.0);
        actividad1 = new Actividad("Introducción a Java", "Video", 101, "Conceptos básicos", "Aprender fundamentos", "Básico", 30);
        actividad2 = new Actividad("Sintaxis en Java", "PDF", 102, "Sintaxis y variables", "Aprender sintaxis de Java", "Básico", 20);


        // Agregar actividades al Learning Path
        learningPath.agregarActividad(actividad1);
        learningPath.agregarActividad(actividad2);
    }

    @AfterEach
    void tearDown() {
        estudiante = null;
        learningPath = null;
        actividad1 = null;
        actividad2 = null;
    }

    @Test
    void testInscribirseEnLearningPath() {
        estudiante.inscribirseEnLearningPath(learningPath);
        
        ProgresoEstudiante progreso = estudiante.getProgreso(learningPath.getId());
        assertNotNull(progreso, "El estudiante debería estar inscrito en el Learning Path y tener un progreso asociado.");
        assertEquals(0.0, progreso.getPorcentajeCompletado(), "El porcentaje de progreso debería ser 0 al inscribirse.");
    }

    @Test
    void testInscribirseEnLearningPathYaInscrito() {
        estudiante.inscribirseEnLearningPath(learningPath);
        
        // Capturar la salida para verificar el mensaje de inscripción duplicada
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        estudiante.inscribirseEnLearningPath(learningPath);

        assertTrue(outContent.toString().contains("Ya está inscrito en este Learning Path."));

        System.setOut(System.out);
    }

    @Test
    void testCompletarActividad() {
        estudiante.inscribirseEnLearningPath(learningPath);
        estudiante.completarActividad(learningPath.getId(), actividad1.getNombre());

        ProgresoEstudiante progreso = estudiante.getProgreso(learningPath.getId());
        assertEquals(50.0, progreso.getPorcentajeCompletado(), "El progreso debería ser del 50% tras completar una de dos actividades.");
    }

    @Test
    void testCompletarActividadNoInscrito() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        estudiante.completarActividad(learningPath.getId(), actividad1.getNombre());

        assertTrue(outContent.toString().contains("No está inscrito en este Learning Path."));

        System.setOut(System.out);
    }

    @Test
    void testGetProgreso() {
        estudiante.inscribirseEnLearningPath(learningPath);
        
        ProgresoEstudiante progreso = estudiante.getProgreso(learningPath.getId());
        assertNotNull(progreso, "El progreso debería existir para un Learning Path en el que el estudiante está inscrito.");
        assertEquals(0.0, progreso.getPorcentajeCompletado(), "El progreso debería comenzar en 0 al inscribirse.");
    }

    @Test
    void testHacerReseña() {
        estudiante.inscribirseEnLearningPath(learningPath);
        estudiante.hacerReseña(learningPath, "Excelente curso!", 5);
        
        Reseña ultimaReseña = learningPath.getUltimaReseña();
        assertNotNull(ultimaReseña, "Debería haber una reseña en el Learning Path.");
        assertEquals("Excelente curso!", ultimaReseña.getComentario(), "El comentario de la reseña debería coincidir.");
        assertEquals(5, ultimaReseña.getCalificacion(), "La calificación de la reseña debería coincidir.");
    }

    @Test
    void testHacerReseñaSinInscripcion() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        estudiante.hacerReseña(learningPath, "Buen curso", 4);

        assertTrue(outContent.toString().contains("Debe estar inscrito en el Learning Path para hacer una reseña."));

        System.setOut(System.out);
    }
}
	
