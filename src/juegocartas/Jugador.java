/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegocartas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import javax.swing.JPanel;
import java.util.Arrays;
import java.util.Collections;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN = 30;
    private final int DISTANCIA = 50;

    private final Carta[] cartas = new Carta[TOTAL_CARTAS];
    private final Random r = new Random();

    int puntaje = 0;

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void organizar() {
        for (int i = 0; i < cartas.length - 1; i++) {
            int indice = i;
            for (int j = i + 1; j < cartas.length; j++) {
                if (cartas[j].getIndice() < cartas[indice].getIndice()) {
                    indice = j;
                }
            }
            // Intercambiar las cartas solo si el índice es diferente
            if (indice != i) {
                Carta temp = cartas[i];
                cartas[i] = cartas[indice];
                cartas[indice] = temp;
            }
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int x = MARGEN;
        for (Carta c : cartas) {
            c.mostrar(pnl, x, MARGEN);
            x += DISTANCIA;
        }
        pnl.repaint();
    }

    public static void eliminarDuplicados(ArrayList<NombreCarta> lista) {
        // Creamos un HashSet a partir de la lista
        HashSet<NombreCarta> conjunto = new HashSet<>(lista);
        // Limpiamos la lista
        lista.clear();
        // Añadimos de vuelta los elementos únicos en orden
        lista.addAll(conjunto);
    }

    public static void eliminarnull(ArrayList<Carta> lista) {

        // Creamos un HashSet a partir de la lista
        HashSet<Carta> conjunto = new HashSet<>(lista);
        conjunto.remove(null);
        // Limpiamos la lista
        lista.clear();
        // Añadimos de vuelta los elementos únicos en orden
        lista.addAll(conjunto);
    }

    public String getGrupos() {
        Carta[] copia_cartas = cartas.clone();
        ArrayList<Carta> copia = new ArrayList<Carta>(Arrays.asList(copia_cartas));
        int totalGrupos = 0;
        String mensaje = "No se encontraron grupos\n";
        int[] contadores = new int[NombreCarta.values().length];

        ArrayList<NombreCarta> arTre = new ArrayList<NombreCarta>();
        ArrayList<NombreCarta> arPica = new ArrayList<NombreCarta>();
        ArrayList<NombreCarta> arCora = new ArrayList<NombreCarta>();
        ArrayList<NombreCarta> arDia = new ArrayList<NombreCarta>();
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        for (int j = 1; j < copia_cartas.length; j++) {
            int primeraCarta = copia_cartas[j].getIndice();
            int segundaCarta = copia_cartas[j - 1].getIndice();
            if (primeraCarta == segundaCarta + 1) {
                if (primeraCarta <= 13 && segundaCarta <= 13) {
                    arTre.add(copia_cartas[j - 1].getNombre());
                    arTre.add(copia_cartas[j].getNombre());
                    copia.set(j, null);
                    copia.set(j - 1, null);

                } else if (primeraCarta > 13 && primeraCarta <= 26 && segundaCarta > 13 && segundaCarta <= 26) {
                    arPica.add(cartas[j - 1].getNombre());
                    arPica.add(cartas[j].getNombre());
                    copia.set(j, null);
                    copia.set(j - 1, null);

                } else if (primeraCarta > 26 && primeraCarta <= 39 && segundaCarta > 26 && segundaCarta <= 39) {
                    arCora.add(cartas[j - 1].getNombre());
                    arCora.add(cartas[j].getNombre());
                    copia.set(j, null);
                    copia.set(j - 1, null);
                } else if (primeraCarta > 39 && segundaCarta > 39) {
                    arDia.add(cartas[j - 1].getNombre());
                    arDia.add(cartas[j].getNombre());
                    copia.set(j, null);
                    copia.set(j - 1, null);
                }
            }
        }

        eliminarDuplicados(arTre);
        eliminarDuplicados(arPica);
        eliminarDuplicados(arCora);
        eliminarDuplicados(arDia);
        eliminarnull(copia);

        int[] contadoresCopia = new int[NombreCarta.values().length];
        for (Carta k : copia) {
            contadoresCopia[k.getNombre().ordinal()]++;
        }
        for (int c : contadoresCopia) {
            if (c >= 2) {
                totalGrupos++;
            }

        }

        if (totalGrupos >= 0) {
            mensaje = "Los grupos encontrados fueron:\n";
            for (int i = 0; i < contadoresCopia.length; i++) {
                if (contadoresCopia[i] >= 2) {
                    mensaje += Grupo.values()[contadoresCopia[i]] + " de " + NombreCarta.values()[i] + "\n";
                }
                if (contadoresCopia[i] == 1) {
                    if (NombreCarta.values()[i].ordinal() + 1 > 10 || NombreCarta.values()[i].ordinal() + 1 == 1) {
                        puntaje += 10;
                    } else {
                        puntaje += NombreCarta.values()[i].ordinal() + 1;//Organizar para que valga lo de las letras
                    }
                }
            }
        }

        if (!arTre.isEmpty()) {
            Collections.sort(arTre);
            String numeros = "";
            for (NombreCarta t : arTre) {
                numeros += t.toString() + ", ";
            }
            numeros = numeros.substring(0, numeros.length() - 2);
            mensaje += "Escalera de Treboles: " + numeros + "\n";
        }
        if (!arPica.isEmpty()) {
            Collections.sort(arPica);
            String numeros = "";
            for (NombreCarta t : arPica) {
                numeros += t.toString() + ", ";
            }
            numeros = numeros.substring(0, numeros.length() - 2);
            mensaje += "Escalera de Picas: " + numeros + "\n";
        }
        if (!arCora.isEmpty()) {
            Collections.sort(arCora);
            String numeros = "";
            for (NombreCarta t : arCora) {
                numeros += t.toString() + ", ";
            }
            numeros = numeros.substring(0, numeros.length() - 2);
            mensaje += "Escalera de Corazones: " + numeros + "\n";
        }
        if (!arDia.isEmpty()) {
            Collections.sort(arDia);
            String numeros = "";
            for (NombreCarta t : arDia) {
                numeros += t.toString() + ", ";
            }
            numeros = numeros.substring(0, numeros.length() - 2);
            mensaje += "Escalera de Diamantes: " + numeros + "\n";
        }

        return mensaje;
    }

    public String calcularPuntaje() {
        puntaje = 0;
        getGrupos();
        String puntajeString = String.valueOf(puntaje);
        return puntajeString;
    }

}
