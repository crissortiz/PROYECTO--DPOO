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
import Proyecto1.ProgresoEstudiante;


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
                Estudiante estudiante = new Estudiante(nombreUsuario, contrasena, correo, idEstudiante);

                // Cargar los IDs de los Learning Paths inscritos
                for (int i = 4; i < datos.length; i++) {
                    int idLearningPath = Integer.parseInt(datos[i]);
                    ProgresoEstudiante progreso = new ProgresoEstudiante(idLearningPath);
                    estudiante.getProgresos().put(idLearningPath, progreso);
                }
                estudiantes.add(estudiante);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar estudiantes: " + e.getMessage());
        }
        return estudiantes;
    }


    public static void guardarEstudiante(Estudiante estudiante) {
        try (FileWriter fw = new FileWriter(ESTUDIANTES_PATH, true)) {
            fw.write(estudiante.getNombreUsuario() + "," + estudiante.getContrasena() + "," +
                     estudiante.getCorreo() + "," + estudiante.getIdEstudiante());

            // Agregar los IDs de los Learning Paths inscritos
            for (Integer idLearningPath : estudiante.getProgresos().keySet()) {
                fw.write("," + idLearningPath);
            }
            fw.write("\n");
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
                String[] partes = linea.split(",");
                
                // Leer los campos obligatorios
                int id = Integer.parseInt(partes[0]);
                int idProfesor = Integer.parseInt(partes[1]); // Leer ID del profesor
                String titulo = partes[2];
                String descripcion = partes[3];
                String tipo = partes[4];
                String objetivo = partes[5];
                String nivelDificultad = partes[6];
                double tiempoEstimado = Double.parseDouble(partes[7]);
                
                // Crear el Learning Path
                LearningPath lp = new LearningPath(id, idProfesor, titulo, descripcion, tipo, objetivo, nivelDificultad, tiempoEstimado);

                // Cargar actividades, si existen
                if (partes.length > 8) {
                    String[] actividades = partes[8].split("\\|");
                    for (String actividadStr : actividades) {
                        String[] actividadPartes = actividadStr.split(";");
                        int idActividad = Integer.parseInt(actividadPartes[0]);
                        String nombre = actividadPartes[1];
                        String tipoActividad = actividadPartes[2];
                        String descripcionActividad = actividadPartes[3];
                        int duracion = Integer.parseInt(actividadPartes[4]);
                        Actividad actividad = new Actividad(nombre, tipoActividad, idActividad, descripcionActividad, "", "", duracion);
                        lp.agregarActividad(actividad);
                    }
                }

                // Agregar el Learning Path a la lista
                learningPaths.add(lp);
            }
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
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
        try (FileWriter fw = new FileWriter(LEARNING_PATHS_PATH, false)) { // Sobrescribir archivo
            for (LearningPath lp : learningPaths) {
                StringBuilder sb = new StringBuilder();
                sb.append(lp.getId()).append(",")
                  .append(lp.getTitulo()).append(",")
                  .append(lp.getDescripcion()).append(",")
                  .append(lp.getTipo()).append(",")
                  .append(lp.getObjetivo()).append(",")
                  .append(lp.getNivelDificultad()).append(",")
                  .append(lp.getTiempoEstimado()).append(",")
                  .append(lp.getIdProfesor()); // Incluir ID del profesor

                // Serializar actividades, si existen
                if (!lp.getActividades().isEmpty()) {
                    sb.append(",");
                    for (Actividad actividad : lp.getActividades()) {
                        sb.append(actividad.getId()).append(";")
                          .append(actividad.getNombre()).append(";")
                          .append(actividad.getTipo()).append(";")
                          .append(actividad.getDescripcion()).append(";")
                          .append(actividad.getDuracion()).append("|");
                    }
                    sb.deleteCharAt(sb.length() - 1); // Quitar el último "|"
                }

                fw.write(sb.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar Learning Paths: " + e.getMessage());
        }
    }

    
    public static void actualizarEstudiantes(List<Estudiante> estudiantes) {
        try (FileWriter fw = new FileWriter(ESTUDIANTES_PATH, false)) { // Sobrescribir archivo
            for (Estudiante estudiante : estudiantes) {
                fw.write(estudiante.getNombreUsuario() + "," + estudiante.getContrasena() + "," +
                         estudiante.getCorreo() + "," + estudiante.getIdEstudiante());

                // Agregar los IDs de los Learning Paths inscritos
                for (Integer idLearningPath : estudiante.getProgresos().keySet()) {
                    fw.write("," + idLearningPath);
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar estudiantes: " + e.getMessage());
        }
    }



}
