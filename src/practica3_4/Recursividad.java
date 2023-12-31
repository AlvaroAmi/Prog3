package practica3_4;

import java.util.*;
import java.sql.*;

public class Recursividad {
    static Connection conn = null;

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

    public void filtraManos(ArrayList<Carta> baraja, int n, Filtro filtro) throws SQLException {
        filtraManosAux(n,new HashSet<>(),baraja,0, filtro);
    }

    public void filtraManosAux(int n, HashSet<Carta> comb, ArrayList<Carta> baraja, int inicio, Filtro filtro) throws SQLException {
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
                                guardaManos(comb,Filtro.POKER);
                                System.out.println(comb);
                            }
                            break;
                        case FULL:
                            if(contadorNumeros.containsValue(3) && contadorNumeros.containsValue(2)){
                                guardaManos(comb,Filtro.FULL);
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
                    guardaManos(comb,Filtro.ESCALERA);
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

    public void guardaManos(HashSet<Carta> comb, Filtro filtro) throws SQLException {
        Statement stmt = conn.createStatement();

        //Definir tablas
        creaTablas();

        String query1 = "INSERT INTO MANOFILTRO VALUES('"+filtro+"','"+comb+"');";

        for(Carta carta : comb){
            String query2 = "INSERT INTO CARTAMANO VALUES('" + comb + "','" + carta + "');";
            stmt.executeUpdate(query2);
        }
        stmt.executeUpdate(query1);

    }

    public void creaTablas() throws SQLException {
        Statement stmt = conn.createStatement();

        String query2 = "CREATE TABLE IF NOT EXISTS MANOFILTRO(CODIGO_FILTRO STRING NOT NULL, CODIGO_MANO STRING NOT NULL, FOREIGN KEY(CODIGO_FILTRO) REFERENCES FILTRO(CODIGO), FOREIGN KEY(CODIGO_MANO) REFERENCES CARTAMANO(CODIGO_CARTAMANO));";
        insertaDatos();
        String query3 = "CREATE TABLE IF NOT EXISTS CARTAMANO(CODIGO_CARTAMANO STRING, CODIGO_CARTA, FOREIGN KEY(CODIGO_CARTA) REFERENCES CARTA(CODIGO_CARTA));";

        stmt.executeUpdate(query2);
        stmt.executeUpdate(query3);

    }

    private void insertaDatos() throws SQLException {
        Statement stmt = conn.createStatement();

        //INSERTAR CARTAS
        String query1 = "DROP TABLE IF EXISTS CARTA;";
        String query2 = "CREATE TABLE IF NOT EXISTS CARTA(CODIGO_CARTA INTEGER PRIMARY KEY AUTOINCREMENT,NUMERO INT NOT NULL,PALO STRING NOT NULL);";

        stmt.executeUpdate(query1);
        stmt.executeUpdate(query2);

        //Insertar cada carta de la baraja española
        ArrayList<String> palos = new ArrayList<>();
        palos.add("oro"); palos.add("espada"); palos.add("copa"); palos.add("basto");

        for(String palo:palos){
            for(int i=1;i<13;i++){
                String query = "INSERT INTO CARTA(NUMERO,PALO) VALUES(" + i + ", '" + palo + "');";
                stmt.executeUpdate(query);
            }
        }

        //INSERTAR FILTROS
        String query3 = "DROP TABLE IF EXISTS FILTRO;";
        String query4 = "CREATE TABLE IF NOT EXISTS FILTRO(CODIGO STRING NOT NULL, DESCRIPION STRING, PRIMARY KEY(CODIGO));";

        stmt.executeUpdate(query3);
        stmt.executeUpdate(query4);


        String query5 = "INSERT INTO FILTRO VALUES('POKER','4 cartas con la misma figura en 5 cartas');";
        String query6 = "INSERT INTO FILTRO VALUES('FULL',' 3 cartas con la misma figura, 2 cartas con otra figura, en manos de 5 cartas');";
        String query7 = "INSERT INTO FILTRO VALUES('ESCALERA',' 5 cartas consecutivas del mismo palo');";

        stmt.executeUpdate(query5);
        stmt.executeUpdate(query6);
        stmt.executeUpdate(query7);

    }

    public void destruyeTablas() throws SQLException {
        Statement stmt = conn.createStatement();

        String query1 = "DROP TABLE IF EXISTS CARTA;";
        String query2 = "DROP TABLE IF EXISTS FILTRO;";
        String query3 = "DROP TABLE IF EXISTS MANOFILTRO;";
        String query4 = "DROP TABLE IF EXISTS CARTAMANO;";

        stmt.executeUpdate(query1);
        stmt.executeUpdate(query2);
        stmt.executeUpdate(query3);
        stmt.executeUpdate(query4);

    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:recursividad.db");
        }catch (SQLException e){
        }

        Recursividad rec = new Recursividad();
        rec.destruyeTablas(); //Resetear base de datos (se puede quitar si de quieren almacenar todas las manos generadas en todas las ejecuciones)
        //Prueba ejercicio 4.1
        System.out.println(rec.invertirFrase("Buenas tardes"));

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

        rec.filtraManos(barajaPrueba, 5, Filtro.POKER);
        //rec.filtraManos(barajaPrueba, 5, Filtro.FULL);
        //rec.filtraManos(barajaPrueba, 5, Filtro.ESCALERA);

    }
}


