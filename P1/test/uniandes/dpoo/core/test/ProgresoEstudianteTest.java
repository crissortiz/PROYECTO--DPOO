package uniandes.dpoo.core.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Proyecto1.ProgresoEstudiante;

public class ProgresoEstudianteTest {
	
	private ProgresoEstudiante progresoEstudiante;

    @BeforeEach
    void setUp() {
        progresoEstudiante = new ProgresoEstudiante(3); // Suponemos un total de 3 actividades
    }

    @AfterEach
    void tearDown() {
        progresoEstudiante = null;
    }

    @Test
    void testCompletarActividad() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Completa una actividad y verifica el mensaje de salida
        progresoEstudiante.completarActividad("Actividad 1");
        assertTrue(outContent.toString().contains("Actividad Actividad 1 completada."));

        // Verifica que el porcentaje se actualiza correctamente
        assertEquals(33.33, progresoEstudiante.getPorcentajeCompletado(), 0.01, "El progreso debería ser aproximadamente 33.33% después de completar una actividad.");

        System.setOut(System.out);
    }

    @Test
    void testCompletarActividadDuplicada() {
        progresoEstudiante.completarActividad("Actividad 1");
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Intenta completar la misma actividad nuevamente
        progresoEstudiante.completarActividad("Actividad 1");

        // Verifica que el mensaje de actividad duplicada se muestra
        assertTrue(outContent.toString().contains("Ya completó esta actividad."));
        
        // Verifica que el progreso no se ha incrementado después de la actividad duplicada
        assertEquals(33.33, progresoEstudiante.getPorcentajeCompletado(), 0.01, "El progreso no debería cambiar al intentar completar una actividad duplicada.");

        System.setOut(System.out);
    }

    @Test
    void testCalcularPorcentajeCompletado() {
        // Completa actividades y verifica el progreso calculado
        progresoEstudiante.completarActividad("Actividad 1");
        assertEquals(33.33, progresoEstudiante.calcularPorcentajeCompletado(), 0.01, "El progreso debería ser 33.33% después de una actividad completada.");

        progresoEstudiante.completarActividad("Actividad 2");
        assertEquals(66.67, progresoEstudiante.calcularPorcentajeCompletado(), 0.01, "El progreso debería ser 66.67% después de dos actividades completadas.");

        progresoEstudiante.completarActividad("Actividad 3");
        assertEquals(100.0, progresoEstudiante.calcularPorcentajeCompletado(), 0.01, "El progreso debería ser 100% después de todas las actividades completadas.");
    }

    @Test
    void testGetPorcentajeCompletadoSinActividades() {
        ProgresoEstudiante progresoSinActividades = new ProgresoEstudiante(0);
        assertEquals(0.0, progresoSinActividades.getPorcentajeCompletado(), "El progreso debería ser 0% cuando no hay actividades.");
    }

}
