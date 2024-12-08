package GUI;

import javax.swing.*;
import Proyecto1.Profesor;

public class ProfesorGui extends JFrame {
    public ProfesorGui(Profesor profesor) {
        setTitle("Panel del Profesor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Agregar un mensaje inicial o componentes adicionales
        JLabel lblBienvenida = new JLabel("Bienvenido, " + profesor.getNombreUsuario());
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);

        add(lblBienvenida);
        // Aquí puedes añadir más componentes y lógica según los requerimientos
    }
}
