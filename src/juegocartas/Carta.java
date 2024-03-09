/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegocartas;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Carta {

    private int indice;

    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }

    public int getIndice() {
        return indice;
    }

    public void mostrar(JPanel pnl, int x, int y) {
        // obtener el nombre del archivo
        String nombreImagen = "/imagenes/CARTA" + String.valueOf(indice) + ".JPG";
        // cargar la imagen en memoria  
        ImageIcon imagen = new ImageIcon(getClass().getResource(nombreImagen));
        // crear la instancia del objeto JLABEL
        JLabel lbl = new JLabel(imagen);
        // definir coordenadas de despliegue y el ancho y alto
        lbl.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
        // agregar la etiqueta al panel 
        pnl.add(lbl);
    }

    

    public Pinta getPinta() {
        if (indice <= 13) {
            return Pinta.TREBOL;

        } else if (indice <= 26) {
            return Pinta.PICA;

        } else if (indice <= 39) {
            return Pinta.CORAZON;

        } else {
            return Pinta.DIAMANTE;
        }

    }

    public NombreCarta getNombre() {
        int residuo = indice % 13;
        if (residuo == 0) {
            residuo = 13;
        }
        return NombreCarta.values()[residuo - 1];
    }
    
}
