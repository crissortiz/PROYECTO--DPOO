package Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Persistencia.ArchivoPersistencia;
import Proyecto1.LearningPath;
import Proyecto1.Profesor;

public class ProfesorUi {
	
	private Profesor profesor; // Instancia del profesor

    // Constructor que recibe una instancia de Profesor
    public ProfesorUi(Profesor profesor) {
        this.profesor = profesor;
    }
    
    public static void main(String[] args) {
        Profesor profesor = new Profesor(nombreUsuario, contrasena, correo, idProfesor);
        ProfesorUi profesorUi = new ProfesorUi(profesor);
        profesorUi.mostrarMenu();
    }

    // Mostrar menú de opciones
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Menú Profesor ===");
            System.out.println("1. Crear Learning Path");
            System.out.println("2. Listar Learning Paths");
            System.out.println("3. Calificar Actividad");
            System.out.println("4. Revisar Tasa de Éxito");
            System.out.println("5. Clonar Learning Path");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    crearLearningPath();
                    break;
                case 2:
                    listarLearningPaths();
                    break;
                case 3:
                    calificarActividad();
                    break;
                case 4:
                    revisarTasaDeExito();
                    break;
                case 5:
                    clonarLearningPath();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema del Profesor...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    // Función para crear un nuevo Learning Path
    public void crearLearningPath() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Crear un nuevo Learning Path ===");

        System.out.print("Ingrese el ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Tipo: ");
        String tipo = scanner.nextLine();

        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();

        System.out.print("Nivel de dificultad: ");
        String nivelDificultad = scanner.nextLine();

        System.out.print("Tiempo estimado (en horas): ");
        double tiempoEstimado = scanner.nextDouble();

        // Crear el Learning Path
        LearningPath nuevoLearningPath = new LearningPath(id, titulo, descripcion, tipo, objetivo, nivelDificultad, tiempoEstimado);

        profesor.crearLearningPath(nuevoLearningPath);
        System.out.println("Learning Path creado exitosamente.");
    }

    // Función para listar Learning Paths
    public void listarLearningPaths() {
        System.out.println("\n=== Lista de Learning Paths ===");
        profesor.listarLearningPaths(); // Llama al método que imprime la lista
    }
    
    // Función para calificar una actividad
    public void calificarActividad() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Calificar Actividad ===");
        System.out.print("Nombre del Learning Path: ");
        String nombreLearningPath = scanner.nextLine();

        System.out.print("Nombre de la Actividad: ");
        String nombreActividad = scanner.nextLine();

        System.out.print("Calificación (0-100): ");
        double calificacion = scanner.nextDouble();

        profesor.calificarActividad(nombreLearningPath, nombreActividad, calificacion);
    }

    // Función para revisar la tasa de éxito de un Learning Path
    public void revisarTasaDeExito() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Revisar Tasa de Éxito ===");
        System.out.print("Título del Learning Path: ");
        String titulo = scanner.nextLine(); // Ahora pedimos el título en lugar del ID

        profesor.revisarTasaDeExito(titulo); // Llama al método con el título
    } 

    // Función para clonar un Learning Path existente
    public void clonarLearningPath() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Clonar Learning Path ===");
        System.out.print("ID del Learning Path a clonar: ");
        int id = scanner.nextInt();

        // Buscar el Learning Path por ID
        LearningPath lpOriginal = profesor.buscarLearningPathPorId(id);

        if (lpOriginal != null) {
            profesor.clonarLearningPath(lpOriginal); // Clona el Learning Path si existe
            System.out.println("Learning Path clonado exitosamente.");
        } else {
            System.out.println("Learning Path con ID " + id + " no encontrado.");
        }
    }
} 