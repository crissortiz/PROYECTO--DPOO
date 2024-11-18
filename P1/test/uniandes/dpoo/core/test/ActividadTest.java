package uniandes.dpoo.core.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Proyecto1.Actividad;

public class ActividadTest {

	private Actividad actividad;

    @BeforeEach
    void setUp() {
        actividad = new Actividad("Introducción a Java", "Video", 101, "Conceptos básicos de Java", "Aprender fundamentos de Java", "Básico", 30);
    }

    @AfterEach
    void tearDown() {
        actividad = null;
    }

    @Test
    void testMarcarCompletado() {
        // Verifica que la actividad inicialmente no esté completada
        assertFalse(actividad.isCompletado(), "La actividad no debería estar completada al inicio.");

        // Marca la actividad como completada
        actividad.marcarCompletado();

        // Verifica que la actividad esté marcada como completada
        assertTrue(actividad.isCompletado(), "La actividad debería estar marcada como completada.");
    }

    @Test
    void testAsignarAEstudiante() {
        int idEstudiante = 1001;

        // Asigna la actividad al estudiante
        actividad.asignarAEstudiante(idEstudiante);

        // Verifica que el estudiante esté en los resultados con un resultado vacío
        assertEquals("", actividad.obtenerResultado(idEstudiante), "El resultado del estudiante debería estar vacío al asignarse.");
    }

    @Test
    void testCalificarActividadEstudianteAsignado() {
        int idEstudiante = 1001;
        String resultado = "Excelente trabajo";

        // Asigna y califica la actividad para el estudiante
        actividad.asignarAEstudiante(idEstudiante);
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        actividad.calificarActividad(idEstudiante, resultado);

        // Verifica que el mensaje de calificación se haya mostrado correctamente
        assertTrue(outContent.toString().contains("Actividad calificada para el estudiante con ID " + idEstudiante));

        // Verifica que el resultado del estudiante sea el esperado
        assertEquals(resultado, actividad.obtenerResultado(idEstudiante), "El resultado del estudiante debería ser 'Excelente trabajo'.");

        System.setOut(System.out);
    }

    @Test
    void testCalificarActividadEstudianteNoAsignado() {
        int idEstudiante = 1002;
        String resultado = "Buen esfuerzo";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Intenta calificar sin asignar al estudiante
        actividad.calificarActividad(idEstudiante, resultado);

        // Verifica que el mensaje de error se haya mostrado
        assertTrue(outContent.toString().contains("Estudiante no encontrado en esta actividad."));

        // Verifica que el resultado devuelto indique que el estudiante no ha sido asignado
        assertEquals("No asignado o no calificado", actividad.obtenerResultado(idEstudiante), "El estudiante no debería tener un resultado asignado.");

        System.setOut(System.out);
    }

    @Test
    void testSettersAndGetters() {
        // Prueba los métodos de obtención y asignación de atributos
        actividad.setDescripcion("Descripción avanzada");
        assertEquals("Descripción avanzada", actividad.getDescripcion(), "La descripción debería coincidir.");

        actividad.setObjetivo("Dominar Java");
        assertEquals("Dominar Java", actividad.getObjetivo(), "El objetivo debería coincidir.");

        actividad.setNivelDificultad("Avanzado");
        assertEquals("Avanzado", actividad.getNivelDificultad(), "El nivel de dificultad debería coincidir.");

        actividad.setResultado("Aprobado");
        assertEquals("Aprobado", actividad.getResultado(), "El resultado debería coincidir.");

        actividad.setDuracion(45);
        assertEquals(45, actividad.getDuracion(), "La duración debería coincidir.");

        actividad.setRating(4.5);
        assertEquals(4.5, actividad.getRating(), 0.01, "La calificación debería coincidir.");
    }
}
