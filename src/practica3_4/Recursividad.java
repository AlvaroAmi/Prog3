package practica3_4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Recursividad {

    /*
    4.1. Método invertirFrase, que recibe un String y lo devuelve invertido letra a letra, de forma recursiva
     */
    public String invertirFrase(String original){
        if (original.length() > 0){
        return original.charAt(original.length()-1) + invertirFrase(original.substring(0,original.length()-1));
        }else{
            return "";
        }
    }

    /*
    4.3. Método posiblesManos, que usando una clase Carta que puedes definir como desees (representando a una
    carta de baraja francesa o española), recibe un número n y un arraylist de esas cartas (“baraja”), y genera en
    consola todas las posibles combinaciones (“manos”) de “n” cartas de esa baraja. Prueba el método con un valor
    “n” pequeño para que la salida sea manejable.
     */
    public void posiblesManos(ArrayList<Carta> baraja, int n){
        posiblesManosAux(n,new HashSet<>(),baraja,0);
    }

    public void posiblesManosAux(int n, HashSet<Carta> comb, ArrayList<Carta> baraja, int inicio){
        if (n == 0){
            System.out.println(comb);
            return;
        }
        for (int i = inicio; i < baraja.size(); i++) {
            HashSet<Carta> combNuevo = new HashSet<>(comb);
            combNuevo.add(baraja.get(i));
            posiblesManosAux(n - 1, combNuevo, baraja, i + 1);
        }
    }

    /*
    4.4. Método filtraManos, copiando el anterior pero modificándolo para que saque solo las manos que cumplan
    una condición de tu elección (por ejemplo, solo las combinaciones que incluyen al menos un as).
     */

    public void filtraManos(ArrayList<Carta> baraja, int n, String palo){
        filtraManosAux(n,new HashSet<>(),baraja,0, palo);
    }

    public void filtraManosAux(int n, HashSet<Carta> comb, ArrayList<Carta> baraja, int inicio, String palo){
        if (n == 0){
            Iterator a = comb.iterator();
            while(a.hasNext()){
                Object carta = a.next();
                Carta carta1 = (Carta) carta;
                if (carta1.getPalo().equals(palo)){
                    System.out.println(comb);
                    return;
                }
            }

        }
        for (int i = inicio; i < baraja.size(); i++) {
            HashSet<Carta> combNuevo = new HashSet<>(comb);
            combNuevo.add(baraja.get(i));
            filtraManosAux(n - 1, combNuevo, baraja, i + 1, palo);
        }
    }


    public static void main(String[] args) {
        Recursividad rec = new Recursividad();
        //Prueba ejercicio 4.1
        //System.out.println(rec.invertirFrase("Buenas tardes"));

        //Prueba ejercicio 4.3
        Carta oro3 = new Carta(3,"oro");
        Carta copa7 = new Carta(7,"copa");
        Carta basto1 = new Carta(1,"basto");
        Carta espada10 = new Carta(10, "espada");
        ArrayList<Carta> baraja1 = new ArrayList<>();
        baraja1.add(oro3);
        baraja1.add(copa7);
        baraja1.add(basto1);
        baraja1.add(espada10);

        //rec.posiblesManos(baraja1, 3);

        //Prueba ejercicio 4.4
        rec.filtraManos(baraja1, 3, "copa");
    }
}


