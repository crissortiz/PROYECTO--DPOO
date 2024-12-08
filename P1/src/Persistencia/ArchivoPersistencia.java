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
                
                // Validación básica: la línea debe tener al menos 8 partes (ID, ID Profesor, título, etc.)
                if (partes.length < 8) {
                    System.out.println("Línea inválida o incompleta en el archivo de Learning Paths: " + linea);
                    continue; // Saltar líneas incorrectas
                }

                int id = Integer.parseInt(partes[0]); // ID del Learning Path
                int idProfesor = Integer.parseInt(partes[1]); // ID del profesor
                String titulo = partes[2]; // Título del Learning Path
                String descripcion = partes[3]; // Descripción
                String tipo = partes[4]; // Tipo (curso, examen, etc.)
                String objetivo = partes[5]; // Objetivo
                String nivelDificultad = partes[6]; // Nivel de dificultad
                double tiempoEstimado = Double.parseDouble(partes[7]); // Tiempo estimado en horas

                // Crear instancia de LearningPath
                LearningPath lp = new LearningPath(id, idProfesor, titulo, descripcion, tipo, objetivo, nivelDificultad, tiempoEstimado);

                // Cargar actividades, si existen
                if (partes.length > 8) {
                    String[] actividades = partes[8].split("\\|");
                    for (String actividadStr : actividades) {
                        String[] actividadPartes = actividadStr.split(";");
                        
                        // Validar que la actividad tenga los datos mínimos necesarios
                        if (actividadPartes.length < 5) {
                            System.out.println("Actividad inválida en el archivo: " + actividadStr);
                            continue; // Saltar actividades incompletas
                        }
                        
                        int idActividad = Integer.parseInt(actividadPartes[0]); // ID de la actividad
                        String nombre = actividadPartes[1]; // Nombre de la actividad
                        String tipoActividad = actividadPartes[2]; // Tipo de actividad
                        String descripcionActividad = actividadPartes[3]; // Descripción de la actividad
                        int duracion = Integer.parseInt(actividadPartes[4]); // Duración en minutos

                        // Crear la actividad
                        Actividad actividad = new Actividad(nombre, tipoActividad, idActividad, descripcionActividad, "", "", duracion);

                        lp.agregarActividad(actividad);
                    }
                }

                learningPaths.add(lp);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar Learning Paths: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato del archivo de Learning Paths: " + e.getMessage());
        }
        return learningPaths;
    }








    public static void guardarLearningPath(LearningPath lp) {
        try (FileWriter fw = new FileWriter(LEARNING_PATHS_PATH, true)) {
            StringBuilder sb = new StringBuilder();
            sb.append(lp.getId()).append(",");
            sb.append(lp.getTitulo()).append(",");
            sb.append(lp.getDescripcion()).append(",");
            sb.append(lp.getTipo()).append(",");
            sb.append(lp.getObjetivo()).append(",");
            sb.append(lp.getNivelDificultad()).append(",");
            sb.append(lp.getTiempoEstimado()).append(",");
            sb.append(lp.getIdProfesor()).append(","); // Agregar ID del profesor

            // Agregar actividades, si existen
            if (!lp.getActividades().isEmpty()) {
                List<String> actividades = new ArrayList<>();
                for (Actividad actividad : lp.getActividades()) {
                    actividades.add(actividad.getId() + ";" +
                                    actividad.getNombre() + ";" +
                                    actividad.getTipo() + ";" +
                                    actividad.getDescripcion() + ";" +
                                    actividad.getDuracion());
                }
                sb.append(String.join("|", actividades));
            }

            fw.write(sb.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar el Learning Path: " + e.getMessage());
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

    private static String serializarActividades(List<Actividad> actividades) {
        StringBuilder sb = new StringBuilder();
        for (Actividad actividad : actividades) {
            sb.append(actividad.getId()).append(";")
              .append(actividad.getNombre()).append(";")
              .append(actividad.getTipo()).append(";")
              .append(actividad.getDescripcion()).append(";")
              .append(actividad.getDuracion());

            if (!actividad.getActividadesSeguimiento().isEmpty()) {
                sb.append("->");
                List<String> idsSeguimiento = actividad.getActividadesSeguimiento().stream()
                    .map(a -> String.valueOf(a.getId()))
                    .toList();
                sb.append(String.join("|", idsSeguimiento));
            }

            sb.append("|");
        }
        return sb.toString();
    }
    
    private static void cargarActividadesConSeguimiento(LearningPath lp, String[] partes) {
        if (partes.length > 8) {
            String[] actividades = partes[8].split("\\|");
            for (String actividadStr : actividades) {
                String[] actividadPartes = actividadStr.split("->");
                String[] datosActividad = actividadPartes[0].split(";");

                int idActividad = Integer.parseInt(datosActividad[0]);
                String nombre = datosActividad[1];
                String tipoActividad = datosActividad[2];
                String descripcionActividad = datosActividad[3];
                int duracion = Integer.parseInt(datosActividad[4]);

                Actividad actividad = new Actividad(nombre, tipoActividad, idActividad, descripcionActividad, "", "", duracion);

                // Cargar actividades de seguimiento
                if (actividadPartes.length > 1) {
                    String[] idsSeguimiento = actividadPartes[1].split("\\|");
                    for (String idSeguimiento : idsSeguimiento) {
                        int idSeg = Integer.parseInt(idSeguimiento);
                        Actividad actividadSeguimiento = lp.getActividades().stream()
                            .filter(a -> a.getId() == idSeg)
                            .findFirst()
                            .orElse(null);

                        if (actividadSeguimiento != null) {
                            actividad.agregarActividadSeguimiento(actividadSeguimiento);
                        }
                    }
                }

                lp.agregarActividad(actividad);
            }
        }
    }




}
