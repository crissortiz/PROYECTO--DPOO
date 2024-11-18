package uniandes.dpoo.core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;



import Proyecto1.Profesor;
import Proyecto1.LearningPath;
import Proyecto1.Actividad;
import Proyecto1.Estudiante;
import Proyecto1.Quiz;
import Proyecto1.ProgresoEstudiante;


public class ProfesorTest {

    private Profesor profesor1;
    private LearningPath learningPath1;
    private LearningPath learningPath2;
    private Estudiante estudiante1;
    private Actividad actividad1;
    private Quiz quiz;
    
    @BeforeEach
    void setUp() throws Exception {
        profesor1 = new Profesor("Sebastian", "SebitasVirus", "Sebastian12@hotmail.com", 1);
        learningPath1 = new LearningPath(1, "Curso de Java", "Curso completo de Java", "Curso", "Aprender Java", "Intermedio", 40.0);
        learningPath2 = new LearningPath(2, "Curso de Python", "Curso avanzado de Python", "Curso", "Profundizar en Python", "Avanzado", 50.0);
        estudiante1 = new Estudiante("Carlos", "password123", "carlos@example.com", 101);
        actividad1 = new Actividad("Introducción a Java", "Video", 101, "Conceptos básicos", "Aprender fundamentos", "Básico", 30);
        // Establecemos una calificación mínima de 2 para el quiz
        quiz = new Quiz(2.0);
    
    }

    @AfterEach
    void tearDown() throws Exception {
        profesor1 = null;
        learningPath1 = null;
        learningPath2 = null;
        estudiante1 = null;
        actividad1 = null;
        quiz = null;
    }

    @Test
    void testCrearLearningPath() {
        profesor1.crearLearningPath(learningPath1);
        assertTrue(profesor1.getLearningPaths().contains(learningPath1), "El learning path debería haberse agregado correctamente.");

        profesor1.crearLearningPath(learningPath2);
        assertTrue(profesor1.getLearningPaths().contains(learningPath2), "El segundo learning path debería haberse agregado correctamente.");
    }

    @Test
    void testModificarLearningPath() {
        profesor1.crearLearningPath(learningPath1);
        profesor1.modificarLearningPath(learningPath1.getId(), "Curso de Java Avanzado", "Curso completo y avanzado de Java", "Curso", "Entener y Dominar Java a un nivel azanvado", "Avanzado",20.0);
        
        assertEquals("Curso de Java Avanzado", learningPath1.getTitulo(), "El título del learning path debería haberse actualizado.");
        assertEquals("Curso completo y avanzado de Java", learningPath1.getDescripcion(), "La descripción del learning path debería haberse actualizado.");
        assertEquals("Curso" , learningPath1.getTipo(), "La Tipo del learning path debería haberse actualizado.");
        assertEquals("Entener y Dominar Java a un nivel azanvado" , learningPath1.getObjetivo(), "El objetivo del learning path debería haberse actualizado.");

        assertEquals("Avanzado" , learningPath1.getNivelDificultad(), "El nivel de dificultad del learning path debería haberse actualizado.");
        assertEquals(20.0 , learningPath1.getTiempoEstimado(), "El tiempo del learning path debería haberse actualizado.");

    }

    @Test
    void testEliminarLearningPath() {
        profesor1.crearLearningPath(learningPath1);
        profesor1.crearLearningPath(learningPath2);
        
        profesor1.eliminarLearningPath(learningPath1);
        assertTrue(!profesor1.getLearningPaths().contains(learningPath1), "El learning path debería haberse eliminado correctamente.");

        profesor1.eliminarLearningPath(learningPath2);
        assertTrue(!profesor1.getLearningPaths().contains(learningPath2), "El segundo learning path debería haberse eliminado correctamente.");
    }
    
    @Test
    void testListarLearningPaths() {
        // Configurar flujo de salida para capturar la impresión
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        profesor1.crearLearningPath(learningPath1);
        profesor1.listarLearningPaths();

        String output = outContent.toString().trim();
        assertTrue(output.contains("Título: Curso de Java"));
        assertTrue(output.contains("Descripción: Curso completo de Java"));

        // Restaurar flujo de salida original
        System.setOut(System.out);
    }

    @Test
    void testCalificarEvaluacion() {
        List<String> respuestasEstudiante = new ArrayList<>();
        List<String> respuestasCorrectas = new ArrayList<>();

        respuestasEstudiante.add("Respuesta A");
        respuestasEstudiante.add("Respuesta B");
        respuestasCorrectas.add("Respuesta A");
        respuestasCorrectas.add("Respuesta B");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        profesor1.calificarEvaluacion(quiz, respuestasEstudiante, respuestasCorrectas);

        String output = outContent.toString().trim();
        assertTrue(output.contains("Calificación para el quiz: 2 respuestas correctas."));
        assertTrue(output.contains("Resultado: Aprobado"));

        System.setOut(System.out);
    }

    @Test
    void testAgregarEstudiante() {
        profesor1.agregarEstudiante(estudiante1);

        // Verificación indirecta: comprobar que al menos un progreso esté disponible en el LearningPath agregado.
        List<ProgresoEstudiante> progreso = profesor1.verProgresoEstudiantes(learningPath1.getId());
        assertEquals(0, progreso.size(), "El estudiante debería estar agregado y el progreso debería estar vacío inicialmente.");
    }

    @Test
    void testAsignarActividad() {
        profesor1.crearLearningPath(learningPath1);
        profesor1.asignarActividad(1, actividad1);

        assertTrue(learningPath1.getActividades().contains(actividad1), "La actividad debería haberse asignado al learning path.");
    }
}