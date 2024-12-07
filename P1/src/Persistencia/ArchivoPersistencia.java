package Persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import Proyecto1.Estudiante;
import Proyecto1.Profesor;
import Proyecto1.LearningPath;
import Proyecto1.Actividad;


import java.util.ArrayList;
import java.util.List;


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

    public static ArrayList<LearningPath> cargarLearningPaths() {
        ArrayList<LearningPath> learningPaths = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(LEARNING_PATHS_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue; // Ignorar líneas vacías
                }

                String[] datos = linea.split(",");
                if (datos.length < 8) { // Verificar que hay al menos 8 elementos
                    System.out.println("Línea malformada, ignorada: " + linea);
                    continue;
                }

                int id = Integer.parseInt(datos[0]);
                int idProfesor = Integer.parseInt(datos[1]); // Cargar el ID del profesor
                String titulo = datos[2];
                String descripcion = datos[3];
                String tipo = datos[4];
                String objetivo = datos[5];
                String nivelDificultad = datos[6];
                double tiempoEstimado = Double.parseDouble(datos[7]);

                LearningPath lp = new LearningPath(id, idProfesor, titulo, descripcion, tipo, objetivo, nivelDificultad, tiempoEstimado);

                // Leer actividades si existen
                if (datos.length > 8) {
                    String[] actividadesDatos = datos[8].split(";");
                    for (String actividadData : actividadesDatos) {
                        if (!actividadData.trim().isEmpty()) { // Ignorar actividades vacías
                            String[] actividadCampos = actividadData.split(";");
                            int idActividad = Integer.parseInt(actividadCampos[0]);
                            String nombre = actividadCampos[1];
                            String tipoActividad = actividadCampos[2];
                            String descripcionActividad = actividadCampos[3];
                            int duracion = Integer.parseInt(actividadCampos[4]);
                            Actividad actividad = new Actividad(nombre, tipoActividad, idActividad, descripcionActividad, "", "", duracion);
                            lp.agregarActividad(actividad);
                        }
                    }
                }

                learningPaths.add(lp);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar Learning Paths: " + e.getMessage());
        }
        return learningPaths;
    }


    public static void guardarLearningPath(LearningPath learningPath) {
        try (FileWriter fw = new FileWriter(LEARNING_PATHS_PATH, true)) {
            fw.write(
                learningPath.getId() + "," +
                learningPath.getIdProfesor() + "," + // Guardar el ID del profesor
                learningPath.getTitulo() + "," +
                learningPath.getDescripcion() + "," +
                learningPath.getTipo() + "," +
                learningPath.getObjetivo() + "," +
                learningPath.getNivelDificultad() + "," +
                learningPath.getTiempoEstimado()
            );

            // Guardar actividades si existen
            if (!learningPath.getActividades().isEmpty()) {
                fw.write(",");
                for (Actividad actividad : learningPath.getActividades()) {
                    fw.write(
                        actividad.getId() + ";" +
                        actividad.getNombre() + ";" +
                        actividad.getTipo() + ";" +
                        actividad.getDescripcion() + ";" +
                        actividad.getDuracion() + ";"
                    );
                }
            }
            fw.write("\n");
        } catch (IOException e) {
            System.out.println("Error al guardar Learning Path: " + e.getMessage());
        }
    }

    
    
    public static void actualizarLearningPaths(List<LearningPath> learningPaths) {
        try (FileWriter fw = new FileWriter(LEARNING_PATHS_PATH, false)) {
            for (LearningPath lp : learningPaths) {
                // Guardar datos básicos del Learning Path
                fw.write(lp.getId() + "," + lp.getTitulo() + "," +
                         lp.getDescripcion() + "," + lp.getTipo() + "," +
                         lp.getObjetivo() + "," + lp.getNivelDificultad() + "," +
                         lp.getTiempoEstimado());

                // Guardar las actividades asociadas al Learning Path
                for (Actividad actividad : lp.getActividades()) {
                    fw.write("," + actividad.getId() + ";" + actividad.getNombre() + ";" +
                             actividad.getTipo() + ";" + actividad.getDescripcion() + ";" +
                             actividad.getDuracion());
                }

                fw.write("\n"); // Finalizar línea del Learning Path
            }
            System.out.println("Archivo de Learning Paths actualizado correctamente con actividades.");
        } catch (IOException e) {
            System.out.println("Error al actualizar Learning Paths: " + e.getMessage());
        }
    }


}
