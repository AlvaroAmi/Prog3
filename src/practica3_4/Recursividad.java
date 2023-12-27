package practica3_4;

import java.util.*;

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
    4.2. Método invertirPalabras, que recibe un String y lo devuelve invertido palabra a palabra (considerando los
    separadores habituales espacio, tabulador, salto de línea, símbolos de puntuación), de forma recursiva.
     */

    public String invertirPalabras(String original){
        if (original == null || original.length() <= 0) {
            return "";
        }
        StringBuilder sub = new StringBuilder();
        int index = 0;
        while (index < original.length() && Character.isLetter(original.charAt(index))) {
            sub.append(original.charAt(index));
            index++;
        }
        if(index < original.length()){
            return invertirPalabras(original.substring(index+1)) + original.charAt(index) + sub;
        }else{
            return sub.toString();
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

    public void filtraManos(ArrayList<Carta> baraja, int n, Filtro filtro){
        filtraManosAux(n,new HashSet<>(),baraja,0, filtro);
    }

    public void filtraManosAux(int n, HashSet<Carta> comb, ArrayList<Carta> baraja, int inicio, Filtro filtro){
        if (n == 0){

            if (filtro == Filtro.POKER||filtro == Filtro.FULL){
                if (comb.size() == 5){
                    HashMap<String, Integer> contadorNumeros = new HashMap<>();
                    for(Carta carta : comb){
                        contadorNumeros.put(carta.getPalo(), contadorNumeros.getOrDefault(carta.getPalo(), 0) + 1);
                    }
                    switch (filtro){
                        case POKER:
                            if(contadorNumeros.containsValue(4)){
                                System.out.println(comb);
                            }
                            break;
                        case FULL:
                            if(contadorNumeros.containsValue(3) && contadorNumeros.containsValue(2)){
                                System.out.println(comb);
                            }
                            break;
                    }

                }
            }else if (filtro == Filtro.ESCALERA) {
                ArrayList<Carta> combOrdenado = new ArrayList<>(comb);
                combOrdenado.sort(Carta::compareTo);
                boolean escalera = true;

                for (int i = 0; i < combOrdenado.size() - 1; i++) {
                    if (combOrdenado.get(i).getNumero() != combOrdenado.get(i + 1).getNumero() - 1 ||
                            !combOrdenado.get(i).getPalo().equals(combOrdenado.get(i + 1).getPalo())) {
                        escalera = false;
                        break;
                    }
                }

                if (escalera) {
                    System.out.println(comb);
                }
            }

        }
        for (int i = inicio; i < baraja.size(); i++) {
            HashSet<Carta> combNuevo = new HashSet<>(comb);
            combNuevo.add(baraja.get(i));
            filtraManosAux(n - 1, combNuevo, baraja, i + 1,filtro);
        }
    }


    public static void main(String[] args) {
        Recursividad rec = new Recursividad();
        //Prueba ejercicio 4.1
        //System.out.println(rec.invertirFrase("Buenas tardes"));

        //Prueba ejercicio 4.2
        System.out.println(rec.invertirPalabras("Hola, buenas tardes!"));


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
        Carta oro1 = new Carta(1,"oro");
        Carta oro2 = new Carta(2,"oro");
        Carta oro4 = new Carta(4,"oro");
        Carta oro5 = new Carta(5,"oro");
        Carta espada1 = new Carta(1,"espada");
        Carta espada2 = new Carta(2,"espada");
        Carta espada3 = new Carta(3,"espada");
        Carta basto2 = new Carta(2,"basto");
        Carta basto3 = new Carta(3,"basto");
        Carta basto4 = new Carta(4,"basto");

        ArrayList<Carta> barajaPrueba = new ArrayList<>();
        barajaPrueba.add(oro1);
        barajaPrueba.add(oro2);
        barajaPrueba.add(oro3);
        barajaPrueba.add(oro4);
        barajaPrueba.add(oro5);
        barajaPrueba.add(copa7);
        barajaPrueba.add(basto1);
        barajaPrueba.add(espada10);
        barajaPrueba.add(espada1);
        barajaPrueba.add(espada2);
        barajaPrueba.add(espada3);
        barajaPrueba.add(basto2);
        barajaPrueba.add(basto3);
        barajaPrueba.add(basto4);


        //rec.filtraManos(barajaPrueba, 5, Filtro.POKER);
        //rec.filtraManos(barajaPrueba, 5, Filtro.FULL);
        //rec.filtraManos(barajaPrueba, 5, Filtro.ESCALERA);

    }
}


