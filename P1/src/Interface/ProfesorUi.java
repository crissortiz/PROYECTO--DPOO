package Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Persistencia.ArchivoPersistencia;
import Proyecto1.LearningPath;
import Proyecto1.Profesor;
import Proyecto1.Actividad;

public class ProfesorUi {
	
	private static Profesor profesor;	

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mostrarMenu();
    }

    private static void mostrarMenu() {
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();  // Consumir el salto de línea

        switch (opcion) {
            case 1:
                iniciarSesion();
                break;
            case 2:
                registrarse();
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    private static void iniciarSesion() {
        System.out.print("Ingrese nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String contrasena = scanner.nextLine();

        List<Profesor> profesores = ArchivoPersistencia.cargarProfesores();
        boolean encontrado = false;

        for (Profesor p : profesores) { // Evitar colisión con la variable estática
            if (p.getNombreUsuario().equals(nombreUsuario) && p.getContrasena().equals(contrasena)) {
                System.out.println("Inicio de sesión exitoso. Bienvenido, " + nombreUsuario + "!");
                profesor = p; // Asignar a la variable estática
                encontrado = true;
                mostrarMenuProfesor(profesor);
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Nombre de usuario o contraseña incorrectos.");
        }
    }


    private static void registrarse() {
        System.out.print("Ingrese nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String contrasena = scanner.nextLine();
        System.out.print("Ingrese correo: ");
        String correo = scanner.nextLine();

        int idProfesor = generarIdProfesor();
        Profesor nuevoProfesor = new Profesor(nombreUsuario, contrasena, correo, idProfesor);
        ArchivoPersistencia.guardarProfesor(nuevoProfesor);

        System.out.println("Registro exitoso. Ahora puede iniciar sesión.");
        profesor = nuevoProfesor; // Asignar al profesor registrado
        mostrarMenuProfesor(nuevoProfesor);
    }


    private static void mostrarMenuProfesor(Profesor profesor) {
    	// Cargar los LearningPaths desde el archivo
        List<LearningPath> learningPaths = ArchivoPersistencia.cargarLearningPaths();
        profesor.getLearningPaths().clear(); // Limpiar la lista actual
        profesor.getLearningPaths().addAll(learningPaths); // Agregar los LearningPaths cargados

    	boolean continuar = true;

        while (continuar) {
            System.out.println("\nBienvenido, " + profesor.getNombreUsuario());
            System.out.println("1. Ver perfil");
            System.out.println("2. Crear Learning Path");
            System.out.println("3. Modificar Learning Path");
            System.out.println("4. Agregar Actividad");
            System.out.println("5. Ver mis learning Paths");
            System.out.println("6. clonar Learning Path de otro profesor");
            System.out.println("7. Califiar Examen");
            System.out.println("8. Calificar Quizzes");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    mostrarPerfil(profesor);
                    break;
                case 2:
                	crearLearningPath();
                    break;
                case 3:
                	modificarLearningPath(profesor);
                    break;
                case 4:
                    agregarActividad(profesor);
                    break;
                case 5:
                    verLearningPathsCreados();
                    break;
                case 6:
                    clonarLearningPath();
                    break;
                case 7:
                    calificarExamenes(profesor);
                    break;
                case 8:
                    calificarQuizzes(profesor);
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    private static void mostrarPerfil(Profesor profesor) {
        System.out.println("Perfil del Profesor:");
        System.out.println("Nombre: " + profesor.getNombreUsuario());
        System.out.println("Correo: " + profesor.getCorreo());
    }

    private static void crearLearningPath() {
        System.out.print("Ingrese título del Learning Path: ");
        String titulo = scanner.nextLine();

        System.out.print("Ingrese descripción del Learning Path: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese tipo del Learning Path (por ejemplo: Curso): ");
        String tipo = scanner.nextLine();

        System.out.print("Ingrese objetivo del Learning Path: ");
        String objetivo = scanner.nextLine();

        System.out.print("Ingrese nivel de dificultad (por ejemplo: Principiante, Intermedio, Avanzado): ");
        String nivelDificultad = scanner.nextLine();

        System.out.print("Ingrese tiempo estimado en horas: ");
        double tiempoEstimado = scanner.nextDouble();

        // Generar un ID único para el Learning Path
        int id = generarIdLearningPath();

        // Crear el Learning Path con el ID del profesor
        LearningPath nuevoLearningPath = new LearningPath(id, profesor.getIdProfesor(), titulo, descripcion, tipo, objetivo, nivelDificultad, tiempoEstimado);
        profesor.crearLearningPath(nuevoLearningPath);
        ArchivoPersistencia.guardarLearningPath(nuevoLearningPath);

        System.out.println("Learning Path creado exitosamente.");
    }

    private static void modificarLearningPath(Profesor profesor) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID del Learning Path a modificar: ");
        int idLearningPath = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        LearningPath lp = profesor.buscarLearningPathPorId(idLearningPath);
        if (lp == null) {
            System.out.println("Learning Path con ID " + idLearningPath + " no encontrado.");
            return; // Salir si no se encuentra el Learning Path
        }

        System.out.print("Nuevo título: ");
        String nuevoTitulo = scanner.nextLine();
        System.out.print("Nueva descripción: ");
        String nuevaDescripcion = scanner.nextLine();
        System.out.print("Nuevo tipo: ");
        String nuevoTipo = scanner.nextLine();
        System.out.print("Nuevo objetivo: ");
        String nuevoObjetivo = scanner.nextLine();
        System.out.print("Nuevo nivel de dificultad: ");
        String nuevoNivelDificultad = scanner.nextLine();
        System.out.print("Nuevo tiempo estimado: ");
        double nuevoTiempoEstimado = scanner.nextDouble();

        // Modificar los datos del Learning Path
        profesor.modificarLearningPath(idLearningPath, nuevoTitulo, nuevaDescripcion, nuevoTipo, nuevoObjetivo, nuevoNivelDificultad, nuevoTiempoEstimado);

        // Actualizar la persistencia
        ArchivoPersistencia.actualizarLearningPaths(profesor.getLearningPaths());

        System.out.println("\n¿Desea visualizar los cambios?");
        System.out.println("1. Sí");
        System.out.println("2. No");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();

        if (opcion == 1) {
            visualizarLearningPath(idLearningPath);
        } else {
            System.out.println("Regresando al menú principal...");
        }
    }

    private static void verLearningPathsCreados() {
        profesor.verLearningPathsCreados();
    }

    private static void visualizarLearningPath(int idLearningPath) {
        for (LearningPath lp : profesor.getLearningPaths()) {
            if (lp.getId() == idLearningPath) {
                System.out.println("\n--- Información del Learning Path ---");
                System.out.println("Título: " + lp.getTitulo());
                System.out.println("Descripción: " + lp.getDescripcion());
                System.out.println("Tipo: " + lp.getTipo());
                System.out.println("Objetivo: " + lp.getObjetivo());
                System.out.println("Nivel de Dificultad: " + lp.getNivelDificultad());
                System.out.println("Tiempo Estimado: " + lp.getTiempoEstimado() + " horas");
                return;
            }
        }
        System.out.println("Learning Path con ID " + idLearningPath + " no encontrado.");
    }


    private static void agregarActividad(Profesor profesor) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID del Learning Path al que desea agregar una actividad: ");
        int idLearningPath = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        LearningPath lp = profesor.buscarLearningPathPorId(idLearningPath);
        if (lp == null) {
            System.out.println("Learning Path con ID " + idLearningPath + " no encontrado.");
            return;
        }

        System.out.print("Ingrese el nombre de la actividad: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el tipo de la actividad (Quiz, Recurso, Examen): ");
        String tipo = scanner.nextLine();

        System.out.print("Ingrese la descripción de la actividad: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese la duración estimada en minutos: ");
        int duracion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Generar un ID único para la actividad
        int idActividad = lp.getActividades().size() + 1;

        Actividad nuevaActividad = new Actividad(nombre, tipo, idActividad, descripcion, "", "", duracion);

        // Agregar la actividad al Learning Path
        lp.agregarActividad(nuevaActividad);

        // Actualizar persistencia
        ArchivoPersistencia.actualizarLearningPaths(profesor.getLearningPaths());

        System.out.println("Actividad agregada exitosamente al Learning Path: " + lp.getTitulo());
    }
    
    private static void clonarLearningPath() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Clonar Learning Path ===");
        System.out.println("Lista de Learning Paths disponibles:");
        List<LearningPath> learningPaths = ArchivoPersistencia.cargarLearningPaths();

        // Mostrar todos los Learning Paths con detalles
        for (LearningPath lp : learningPaths) {
            System.out.println("ID: " + lp.getId() + " | Título: " + lp.getTitulo() + " | Profesor ID: " + lp.getIdProfesor());
        }

        System.out.print("Ingrese el ID del Learning Path que desea clonar: ");
        int idClonar = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Buscar el Learning Path por ID
        LearningPath lpOriginal = learningPaths.stream()
            .filter(lp -> lp.getId() == idClonar)
            .findFirst()
            .orElse(null);

        if (lpOriginal == null) {
            System.out.println("Learning Path con ID " + idClonar + " no encontrado.");
            return;
        }

        // Generar un nuevo ID para el clon
        int nuevoId = learningPaths.size() + 1;

        // Clonar el Learning Path
        LearningPath clon = profesor.clonarLearningPath(lpOriginal, nuevoId);

        // Guardar en persistencia
        ArchivoPersistencia.guardarLearningPath(clon);

        System.out.println("\nClonado exitosamente el Learning Path:");
        System.out.println("ID: " + clon.getId());
        System.out.println("Título: " + clon.getTitulo());
        System.out.println("Descripción: " + clon.getDescripcion());
        System.out.println("Tipo: " + clon.getTipo());
        System.out.println("Objetivo: " + clon.getObjetivo());
        System.out.println("Nivel de Dificultad: " + clon.getNivelDificultad());
        System.out.println("Tiempo Estimado: " + clon.getTiempoEstimado() + " horas");
        System.out.println("Actividades: ");
        for (Actividad actividad : clon.getActividades()) {
            System.out.println("\t- " + actividad.getNombre() + " (" + actividad.getTipo() + ")");
        }
    }


    


    private static void calificarExamenes(Profesor profesor) {
        System.out.println("Funcionalidad de calificar exámenes no implementada completamente.");
    }

    private static void calificarQuizzes(Profesor profesor) {
        System.out.println("Funcionalidad de calificar quizzes no implementada completamente.");
    }

    private static int generarIdProfesor() {
        List<Profesor> profesores = ArchivoPersistencia.cargarProfesores();
        return profesores.size() + 1;
    }

    private static int generarIdLearningPath() {
        List<LearningPath> learningPaths = ArchivoPersistencia.cargarLearningPaths();
        return learningPaths.size() + 1;
    }
    
    
}
