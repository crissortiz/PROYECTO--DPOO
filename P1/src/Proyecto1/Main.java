package Proyecto1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<LearningPath> learningPaths = new ArrayList<>();
    private static List<Profesor> profesores = new ArrayList<>();
    private static List<Estudiante> estudiantes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static String directorioBase = ""; 

    public static void main(String[] args) {
        seleccionarDirectorio(); 

        while (true) {
            mostrarMenuPrincipal();
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearProfesor();
                    break;
                case 2:
                    crearEstudiante();
                    break;
                case 3:
                    iniciarSesionProfesor();
                    break;
                case 4:
                    iniciarSesionEstudiante();
                    break;
                case 5:
                    guardarDatos(); 
                    System.out.println("Datos guardados exitosamente.");
                    break;
                case 6:
                    cargarDatos(); 
                    System.out.println("Datos cargados exitosamente.");
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void seleccionarDirectorio() {
        System.out.println("Por favor, ingresa el directorio donde se guardarán/cargarán los archivos:");
        directorioBase = scanner.nextLine();
        File dir = new File(directorioBase);
        if (!dir.exists()) {
            System.out.println("El directorio no existe, creando...");
            dir.mkdirs(); 
        }
    }


    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Crear profesor");
        System.out.println("2. Crear estudiante");
        System.out.println("3. Iniciar sesión como profesor");
        System.out.println("4. Iniciar sesión como estudiante");
        System.out.println("5. Guardar datos");
        System.out.println("6. Cargar datos");
        System.out.println("7. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private static void cargarDatos() {
        cargarProfesores();
        cargarEstudiantes();
        cargarLearningPaths();
    }

    private static void guardarDatos() {
        guardarProfesores();
        guardarEstudiantes();
        guardarLearningPaths();
    }

    private static void cargarProfesores() {
        try (BufferedReader br = new BufferedReader(new FileReader(directorioBase + File.separator + "profesores.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                Profesor profesor = new Profesor(datos[0], datos[1], datos[2], Integer.parseInt(datos[3]));
                profesores.add(profesor);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar profesores: " + e.getMessage());
        }
    }

    private static void guardarProfesores() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(directorioBase + File.separator + "profesores.txt"))) {
            for (Profesor profesor : profesores) {
                pw.println(profesor.getNombreUsuario() + "," + profesor.getContrasena() + "," + profesor.getCorreo() + "," + profesor.getIdProfesor());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar profesores: " + e.getMessage());
        }
    }

    private static void cargarEstudiantes() {
        try (BufferedReader br = new BufferedReader(new FileReader(directorioBase + File.separator + "estudiantes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                Estudiante estudiante = new Estudiante(datos[0], datos[1], datos[2], Integer.parseInt(datos[3]));
                estudiantes.add(estudiante);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar estudiantes: " + e.getMessage());
        }
    }

    private static void guardarEstudiantes() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(directorioBase + File.separator + "estudiantes.txt"))) {
            for (Estudiante estudiante : estudiantes) {
                pw.println(estudiante.getNombreUsuario() + "," + estudiante.getContrasena() + "," + estudiante.getCorreo() + "," + estudiante.getIdEstudiante());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar estudiantes: " + e.getMessage());
        }
    }

    private static void cargarLearningPaths() {
        try (BufferedReader br = new BufferedReader(new FileReader(directorioBase + File.separator + "learningPaths.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                LearningPath lp = new LearningPath(Integer.parseInt(datos[0]), datos[1], datos[2], datos[3], datos[4], datos[5], Double.parseDouble(datos[6]));
                learningPaths.add(lp);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar learning paths: " + e.getMessage());
        }
    }

    private static void guardarLearningPaths() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(directorioBase + File.separator + "learningPaths.txt"))) {
            for (LearningPath lp : learningPaths) {
                pw.println(lp.getId() + "," + lp.getTitulo() + "," + lp.getDescripcion() + "," + lp.getTipo() + "," + lp.getObjetivo() + "," + lp.getNivelDificultad() + "," + lp.getTiempoEstimado());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar learning paths: " + e.getMessage());
        }
    }
    private static void crearProfesor() {
        System.out.println("Ingrese nombre de usuario:");
        String nombreUsuario = scanner.nextLine();
        System.out.println("Ingrese contraseña:");
        String contrasena = scanner.nextLine();
        System.out.println("Ingrese correo:");
        String correo = scanner.nextLine();

        int id = profesores.size() + 1;
        Profesor profesor = new Profesor(nombreUsuario, contrasena, correo, id);
        profesores.add(profesor);

        System.out.println("Profesor creado exitosamente.");
    }

    private static void crearEstudiante() {
        System.out.println("Ingrese nombre de usuario:");
        String nombreUsuario = scanner.nextLine();
        System.out.println("Ingrese contraseña:");
        String contrasena = scanner.nextLine();
        System.out.println("Ingrese correo:");
        String correo = scanner.nextLine();

        int id = estudiantes.size() + 1;
        Estudiante estudiante = new Estudiante(nombreUsuario, contrasena, correo, id);
        estudiantes.add(estudiante);

        System.out.println("Estudiante creado exitosamente.");
    }

    private static void iniciarSesionProfesor() {
        System.out.println("Ingrese ID del Profesor:");
        int idProfesor = scanner.nextInt();
        scanner.nextLine(); 

        Profesor profesor = obtenerProfesor(idProfesor);
        if (profesor != null) {
            mostrarMenuProfesor(profesor);
        } else {
            System.out.println("Profesor no encontrado.");
        }
    }

    private static void mostrarMenuProfesor(Profesor profesor) {
        boolean salir = false;
        while (!salir) {
            System.out.println("1. Crear Learning Path");
            System.out.println("2. Modificar Learning Path");
            System.out.println("3. Agregar Actividad");
            System.out.println("4. Cerrar sesión");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearLearningPathProfesor(profesor);
                    break;
                /*
                case 2:
                    modificarLearningPath(profesor);
                    break;
                */
                case 3:
                    agregarActividad(profesor);
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void crearLearningPathProfesor(Profesor profesor) {
        System.out.println("Ingrese título del Learning Path:");
        String titulo = scanner.nextLine();
        System.out.println("Ingrese descripción:");
        String descripcion = scanner.nextLine();
        System.out.println("Ingrese tipo de Learning Path:");
        String tipo = scanner.nextLine();
        System.out.println("Ingrese el objetivo del Learning Path:");
        String objetivo = scanner.nextLine();
        System.out.println("Ingrese el nivel de dificultad (principiante, intermedio, avanzado):");
        String nivelDificultad = scanner.nextLine();
        System.out.println("Ingrese el tiempo estimado (en horas):");
        double tiempoEstimado = scanner.nextDouble();
        scanner.nextLine(); 

        LearningPath lp = new LearningPath(learningPaths.size() + 1, titulo, descripcion, tipo, objetivo, nivelDificultad, tiempoEstimado);
        profesor.crearLearningPath(lp);
        learningPaths.add(lp);
        System.out.println("Learning Path creado exitosamente.");
    }
    /*
    private static void modificarLearningPath(Profesor profesor) {
        System.out.println("Ingrese ID del Learning Path a modificar:");
        int idLP = scanner.nextInt();
        scanner.nextLine();

        LearningPath lp = obtenerLearningPath(idLP);
        if (lp != null) {
            System.out.println("Ingrese nuevo título:");
            String nuevoTitulo = scanner.nextLine();
            System.out.println("Ingrese nueva descripción:");
            String nuevaDescripcion = scanner.nextLine();

            profesor.modificarLearningPath(lp, nuevoTitulo, nuevaDescripcion);
            System.out.println("Learning Path modificado exitosamente.");
        } else {
            System.out.println("Learning Path no encontrado.");
        }
    } 
    */

    private static void agregarActividad(Profesor profesor) {
        System.out.println("Ingrese ID del Learning Path:");
        int idLP = scanner.nextInt();
        scanner.nextLine();
    
        LearningPath lp = obtenerLearningPath(idLP);
        if (lp != null) {
            System.out.println("Ingrese nombre de la actividad:");
            String nombreActividad = scanner.nextLine();
            System.out.println("Ingrese tipo de actividad (Tarea, Examen, etc.):");
            String tipo = scanner.nextLine();
            System.out.println("Ingrese descripción de la actividad:");
            String descripcion = scanner.nextLine();
            System.out.println("Ingrese objetivo de la actividad:");
            String objetivo = scanner.nextLine();
            System.out.println("Ingrese nivel de dificultad de la actividad:");
            String nivelDificultad = scanner.nextLine();
            System.out.println("Ingrese duración de la actividad (en minutos):");
            int duracion = scanner.nextInt();
            scanner.nextLine();  

            int idActividad = lp.getActividades().size() + 1;
    
            Actividad actividad = new Actividad(nombreActividad, tipo, idActividad, descripcion, objetivo, nivelDificultad, duracion);
            lp.agregarActividad(actividad);
            System.out.println("Actividad agregada exitosamente.");
        } else {
            System.out.println("Learning Path no encontrado.");
        }
    }


    private static void iniciarSesionEstudiante() {
        System.out.println("Ingrese ID del Estudiante:");
        int idEstudiante = scanner.nextInt();
        scanner.nextLine(); 

        Estudiante estudiante = obtenerEstudiante(idEstudiante);
        if (estudiante != null) {
            mostrarMenuEstudiante(estudiante);
        } else {
            System.out.println("Estudiante no encontrado.");
        }
    }

    private static void mostrarMenuEstudiante(Estudiante estudiante) {
        boolean salir = false;
        while (!salir) {
            System.out.println("1. Inscribirse en Learning Path");
            System.out.println("2. Marcar Actividad como Finalizada");
            System.out.println("3. Ver Progreso");
            System.out.println("4. Hacer Reseña");
            System.out.println("5. Ver Learning Path y Reseña");
            System.out.println("6. Cerrar sesión");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    inscribirseEnLearningPath(estudiante);
                    break;
                case 2:
                    completarActividadEstudiante(estudiante);
                    break;
                case 3:
                    verProgreso(estudiante);
                    break;
                case 4:
                    hacerReseñaEstudiante(estudiante);
                    break;
                case 5:
                    verLearningPathYReseña();
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void inscribirseEnLearningPath(Estudiante estudiante) {
        System.out.println("Ingrese ID del Learning Path:");
        int idLP = scanner.nextInt();
        scanner.nextLine();

        LearningPath lp = obtenerLearningPath(idLP);
        if (lp != null) {
            estudiante.inscribirseEnLearningPath(lp);
        } else {
            System.out.println("Learning Path no encontrado.");
        }
    }

    private static void completarActividadEstudiante(Estudiante estudiante) {
        System.out.println("Ingrese ID del Learning Path:");
        int idLP = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese nombre de la actividad:");
        String nombreActividad = scanner.nextLine();

        estudiante.completarActividad(idLP, nombreActividad);
    }

    private static void verProgreso(Estudiante estudiante) {
        System.out.println("Ingrese ID del Learning Path:");
        int idLP = scanner.nextInt();
        scanner.nextLine();

        ProgresoEstudiante progreso = estudiante.getProgreso(idLP);
        if (progreso != null) {
            System.out.println("Progreso: " + progreso.getPorcentajeCompletado() + "%");
        } else {
            System.out.println("No se ha iniciado este Learning Path.");
        }
    }

    private static void hacerReseñaEstudiante(Estudiante estudiante) {
        System.out.println("Ingrese ID del Learning Path:");
        int idLP = scanner.nextInt();
        scanner.nextLine();

        LearningPath lp = obtenerLearningPath(idLP);
        if (lp != null) {
            System.out.println("Ingrese su comentario:");
            String comentario = scanner.nextLine();
            System.out.println("Ingrese una calificación (1 a 5):");
            int calificacion = scanner.nextInt();
            scanner.nextLine();

            if (calificacion < 1 || calificacion > 5) {
                System.out.println("La calificación debe estar entre 1 y 5.");
            } else {
                estudiante.hacerReseña(lp, comentario, calificacion);
            }
        } else {
            System.out.println("Learning Path no encontrado.");
        }
    }

    private static void verLearningPathYReseña() {
        System.out.println("Ingrese ID del Learning Path:");
        int idLP = scanner.nextInt();
        scanner.nextLine();

        LearningPath lp = obtenerLearningPath(idLP);
        if (lp != null) {
            System.out.println("Learning Path: " + lp.getTitulo());
            System.out.println("Descripción: " + lp.getDescripcion());
            Reseña ultimaReseña = lp.getUltimaReseña();
            if (ultimaReseña != null) {
                System.out.println("Última reseña: " + ultimaReseña);
            } else {
                System.out.println("Este Learning Path aún no tiene reseñas.");
            }
        } else {
            System.out.println("Learning Path no encontrado.");
        }
    }

    private static Profesor obtenerProfesor(int id) {
        for (Profesor p : profesores) {
            if (p.getIdProfesor() == id) {
                return p;
            }
        }
        return null;
    }

    private static Estudiante obtenerEstudiante(int id) {
        for (Estudiante e : estudiantes) {
            if (e.getIdEstudiante() == id) {
                return e;
            }
        }
        return null;
    }

    private static LearningPath obtenerLearningPath(int id) {
        for (LearningPath lp : learningPaths) {
            if (lp.getId() == id) {
                return lp;
            }
        }
        return null;
    }
}


