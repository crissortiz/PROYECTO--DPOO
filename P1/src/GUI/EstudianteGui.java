package GUI;

import javax.swing.*;
import Proyecto1.Estudiante;

public class EstudianteGui extends JFrame {
    public EstudianteGui(Estudiante estudiante) {
        setTitle("Panel del Estudiante");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Agregar un mensaje inicial o componentes adicionales
        JLabel lblBienvenida = new JLabel("Bienvenido, " + estudiante.getNombreUsuario());
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);

        add(lblBienvenida);
        // Aquí puedes añadir más componentes y lógica según los requerimientos
    }
}
