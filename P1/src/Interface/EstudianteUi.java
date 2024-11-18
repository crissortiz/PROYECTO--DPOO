package Interface;

import java.util.List;
import java.util.Scanner;
import Persistencia.ArchivoPersistencia;
import Proyecto1.Estudiante;

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
        ArchivoPersistencia.guardarEstudiante(nuevoEstudiante);  // Cambiamos a guardarEstudiante

        System.out.println("Registro exitoso. De ahora en adelante podrias iniciar sesión.");
        
        mostrarMenuEstudiante(nuevoEstudiante);
    }
    
    private static void mostrarMenuEstudiante(Estudiante estudiante) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nBienvenido, " + estudiante.getNombreUsuario());
            System.out.println("1. Ver perfil");
            System.out.println("2. Opción de ejemplo 2");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.println("Perfil del Estudiante:");
                    System.out.println("Nombre: " + estudiante.getNombreUsuario());
                    System.out.println("Correo: " + estudiante.getCorreo());
                    break;
                case 2:
                    System.out.println("Opción 2 seleccionada.");
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
        }

    private static int generarIdEstudiante() {
        List<Estudiante> estudiantes = ArchivoPersistencia.cargarEstudiantes();
        return estudiantes.size() + 1;
    }
}



