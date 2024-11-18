package Persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import Proyecto1.Estudiante;
import Proyecto1.Profesor;

public class ArchivoPersistencia {

    private static final String ESTUDIANTES_PATH = "archivos/estudiantes.txt";
    private static final String PROFESORES_PATH = "archivos/profesores.txt";
    private static final String LEARNING_PATHS_PATH = "archivos/learningPaths.txt";

    public static ArrayList<Estudiante> cargarEstudiantes() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ESTUDIANTES_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombreUsuario = datos[0];
                String contrasena = datos[1];
                String correo = datos[2];
                int idEstudiante = Integer.parseInt(datos[3]);
                estudiantes.add(new Estudiante(nombreUsuario, contrasena, correo, idEstudiante));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar estudiantes: " + e.getMessage());
        }
        return estudiantes;
    }

    public static void guardarEstudiante(Estudiante estudiante) {
        try (FileWriter fw = new FileWriter(ESTUDIANTES_PATH, true)) {
            fw.write(estudiante.getNombreUsuario() + "," + estudiante.getContrasena() + "," +
                     estudiante.getCorreo() + "," + estudiante.getIdEstudiante() + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar estudiante: " + e.getMessage());
        }
    }

    public static ArrayList<Profesor> cargarProfesores() {
        ArrayList<Profesor> profesores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PROFESORES_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombreUsuario = datos[0];
                String contrasena = datos[1];
                String correo = datos[2];
                int idProfesor = Integer.parseInt(datos[3]);
                profesores.add(new Profesor(nombreUsuario, contrasena, correo, idProfesor));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar profesores: " + e.getMessage());
        }
        return profesores;
    }

    public static void guardarProfesor(Profesor profesor) {
        try (FileWriter fw = new FileWriter(PROFESORES_PATH, true)) {
            fw.write(profesor.getNombreUsuario() + "," + profesor.getContrasena() + "," +
                     profesor.getCorreo() + "," + profesor.getIdProfesor() + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar profesor: " + e.getMessage());
        }
    }

    public static ArrayList<String> cargarLearningPaths() {
        ArrayList<String> learningPaths = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(LEARNING_PATHS_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                learningPaths.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar learning paths: " + e.getMessage());
        }
        return learningPaths;
    }
}
