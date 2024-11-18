package uniandes.dpoo.core.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Proyecto1.Quiz;

public class QuizTest {
	
	private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz(3.0); // Establecemos una calificación mínima de 3.0 para aprobar
    }

    @AfterEach
    void tearDown() {
        quiz = null;
    }

    @Test
    void testAgregarPregunta() {
        // Verifica que la lista de preguntas esté inicialmente vacía
        assertTrue(quiz.getPreguntas().isEmpty(), "La lista de preguntas debería estar vacía al inicio.");

        // Agrega una pregunta y verifica que se haya agregado correctamente
        quiz.agregarPregunta("¿Qué es Java?");
        assertEquals(1, quiz.getPreguntas().size(), "Debería haber una pregunta en la lista.");
        assertEquals("¿Qué es Java?", quiz.getPreguntas().get(0), "La pregunta debería coincidir con la agregada.");
    }

    @Test
    void testEsAprobado() {
        // Verifica que el puntaje mínimo aprueba el quiz
        assertTrue(quiz.esAprobado(3.0), "Un puntaje de 3.0 debería aprobar el quiz.");
        
        // Verifica que un puntaje superior al mínimo apruebe el quiz
        assertTrue(quiz.esAprobado(4.0), "Un puntaje de 4.0 debería aprobar el quiz.");

        // Verifica que un puntaje inferior al mínimo no apruebe el quiz
        assertFalse(quiz.esAprobado(2.0), "Un puntaje de 2.0 no debería aprobar el quiz.");
    }

    @Test
    void testGetPreguntas() {
        quiz.agregarPregunta("¿Qué es Java?");
        quiz.agregarPregunta("¿Cuál es la última versión de Java?");
        
        assertEquals(2, quiz.getPreguntas().size(), "Debería haber dos preguntas en la lista.");
        assertTrue(quiz.getPreguntas().contains("¿Qué es Java?"), "La lista debería contener la primera pregunta agregada.");
        assertTrue(quiz.getPreguntas().contains("¿Cuál es la última versión de Java?"), "La lista debería contener la segunda pregunta agregada.");
    }

    @Test
    void testGetCalificacionMinima() {
        // Verifica que la calificación mínima sea la establecida en el constructor
        assertEquals(3.0, quiz.getCalificacionMinima(), "La calificación mínima debería ser 3.0.");
    }

}
