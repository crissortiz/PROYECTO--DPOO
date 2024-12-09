package Interface;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import Persistencia.ArchivoPersistencia;
import Proyecto1.Estudiante;
import Proyecto1.LearningPath;
import Proyecto1.ProgresoEstudiante;
import Proyecto1.Actividad;

							
public class EstudianteUi {

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

        List<Estudiante> estudiantes = ArchivoPersistencia.cargarEstudiantes();
        boolean encontrado = false;

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombreUsuario().equals(nombreUsuario) && estudiante.getContrasena().equals(contrasena)) {
                System.out.println("Inicio de sesión exitoso. Bienvenido, " + nombreUsuario + "!");
                encontrado = true;
                
                mostrarMenuEstudiante(estudiante);
                               
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

        int idEstudiante = generarIdEstudiante();  // Método para generar un nuevo ID
        Estudiante nuevoEstudiante = new Estudiante(nombreUsuario, contrasena, correo, idEstudiante);

        // Guardar el nuevo estudiante en el archivo
        ArchivoPersistencia.guardarEstudiante(nuevoEstudiante); 
        

        System.out.println("Registro exitoso. De ahora en adelante podrias iniciar sesión.");
        
        mostrarMenuEstudiante(nuevoEstudiante);
    }
    
    private static void mostrarMenuEstudiante(Estudiante estudiante) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nBienvenido, " + estudiante.getNombreUsuario());
            System.out.println("1. Ver perfil");
            System.out.println("2. Inscribirse en un Learning Path");
            System.out.println("3. Ver mi progreso en un Learning Path");
            System.out.println("4. Marcar inicio de una actividad");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    verPerfil(estudiante);
                    break;
                case 2:
                    inscribirseEnLearningPath(estudiante);
                    break;
                case 3:
                	verProgresoLearningPath(estudiante); 
                    break;
                case 4:
                	marcarInicioActividad(estudiante); 
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    

    
    private static void verPerfil(Estudiante estudiante) {
        System.out.println("\n--- Perfil del Estudiante ---");
        System.out.println("Nombre de usuario: " + estudiante.getNombreUsuario());
        System.out.println("Correo: " + estudiante.getCorreo());
        System.out.println("ID del Estudiante: " + estudiante.getIdEstudiante());
        
        // Mostrar los Learning Paths inscritos
        if (!estudiante.getProgresos().isEmpty()) {
            System.out.println("\nLearning Paths inscritos:");
            for (Integer idLearningPath : estudiante.getProgresos().keySet()) {
                ProgresoEstudiante progreso = estudiante.getProgresos().get(idLearningPath);
                System.out.println("- ID del Learning Path: " + idLearningPath);
                System.out.println("  Progreso: " + progreso.getPorcentajeCompletado() + "%");

                // Detalles de actividades completadas y pendientes
                System.out.println("  Actividades completadas:");
                for (Actividad actividad : progreso.getActividadesCompletadas()) {
                    System.out.println("    - " + actividad.getNombre() + " (Duración: " + actividad.getDuracion() + " min)");
                }

                System.out.println("  Actividades pendientes:");
                for (Actividad actividad : progreso.getActividadesPendientes()) {
                    System.out.println("    - " + actividad.getNombre() + " (Duración: " + actividad.getDuracion() + " min)");
                }
            }
        } else {
            System.out.println("\nNo está inscrito en ningún Learning Path.");
        }
    }


    
    private static void inscribirseEnLearningPath(Estudiante estudiante) {
        List<LearningPath> learningPaths = ArchivoPersistencia.cargarLearningPaths();

        System.out.println("\n--- Learning Paths Disponibles ---");
        for (LearningPath lp : learningPaths) {
            System.out.println("ID: " + lp.getId() + " | Título: " + lp.getTitulo() +
                               " | Descripción: " + lp.getDescripcion());
        }

        System.out.print("\nIngrese el ID del Learning Path al que desea inscribirse: ");
        int idLearningPath = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Buscar el Learning Path seleccionado
        LearningPath lp = learningPaths.stream()
            .filter(path -> path.getId() == idLearningPath)
            .findFirst()
            .orElse(null);

        if (lp == null) {
            System.out.println("Learning Path con ID " + idLearningPath + " no encontrado.");
            return;
        }

        estudiante.inscribirseEnLearningPath(lp);

        // Actualizar persistencia
        List<Estudiante> estudiantes = ArchivoPersistencia.cargarEstudiantes();
        estudiantes.removeIf(e -> e.getIdEstudiante() == estudiante.getIdEstudiante());
        estudiantes.add(estudiante);
        ArchivoPersistencia.actualizarEstudiantes(estudiantes);
    }
    
    private static void verProgresoLearningPath(Estudiante estudiante) {
        if (estudiante.getProgresos().isEmpty()) {
            System.out.println("\nNo estás inscrito en ningún Learning Path.");
            return;
        }

        System.out.println("\n--- Learning Paths Inscritos ---");
        for (Integer idLearningPath : estudiante.getProgresos().keySet()) {
            System.out.println("ID: " + idLearningPath);
        }

        System.out.print("\nSeleccione el ID del Learning Path para ver su progreso: ");
        int idLearningPath = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        ProgresoEstudiante progreso = estudiante.getProgresos().get(idLearningPath);
        if (progreso == null) {
            System.out.println("No estás inscrito en este Learning Path.");
            return;
        }

        System.out.println("\n--- Progreso del Learning Path ID " + idLearningPath + " ---");
        System.out.println("Porcentaje completado: " + progreso.getPorcentajeCompletado() + "%");

        System.out.println("\nActividades Completadas:");
        for (Actividad actividad : progreso.getActividadesCompletadas()) {
            System.out.println("- " + actividad.getNombre() + " (Duración: " + actividad.getDuracion() + " min)");
        }

        System.out.println("\nActividades Pendientes:");
        for (Actividad actividad : progreso.getActividadesPendientes()) {
            System.out.println("- " + actividad.getNombre() + " (Duración: " + actividad.getDuracion() + " min)");
        }
    }


    
    private static void marcarInicioActividad(Estudiante estudiante) {
        if (estudiante.getProgresos().isEmpty()) {
            System.out.println("\nNo estás inscrito en ningún Learning Path.");
            return;
        }

        System.out.println("\n--- Learning Paths Inscritos ---");
        for (Integer idLearningPath : estudiante.getProgresos().keySet()) {
            System.out.println("- ID: " + idLearningPath);
        }

        System.out.print("\nIngrese el ID del Learning Path: ");
        int idLearningPath = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        ProgresoEstudiante progreso = estudiante.getProgresos().get(idLearningPath);
        if (progreso == null) {
            System.out.println("No estás inscrito en este Learning Path.");
            return;
        }

        System.out.println("\nActividades pendientes:");
        for (Actividad actividad : progreso.getActividadesPendientes()) {
            System.out.println("- " + actividad.getNombre());
        }

        System.out.print("\nIngrese el nombre de la actividad para marcar inicio: ");
        String nombreActividad = scanner.nextLine();

        boolean inicioExitoso = progreso.iniciarActividad(nombreActividad);
        if (inicioExitoso) {
            System.out.println("Actividad marcada como iniciada.");
            // Actualizar persistencia
            List<Estudiante> estudiantes = ArchivoPersistencia.cargarEstudiantes();
            estudiantes.removeIf(e -> e.getIdEstudiante() == estudiante.getIdEstudiante());
            estudiantes.add(estudiante);
            ArchivoPersistencia.actualizarEstudiantes(estudiantes);
        }
    }





    

    private static int generarIdEstudiante() {
        List<Estudiante> estudiantes = ArchivoPersistencia.cargarEstudiantes();
        return estudiantes.size() + 1;
    }
}



