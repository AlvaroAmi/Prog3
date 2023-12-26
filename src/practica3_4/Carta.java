package practica3_4;

public class Carta { //Cartas de la baraja española
    protected int numero; //1,2,3...12
    protected String palo; //oro,copa,espada,basto

    public Carta(int numero, String palo) {
        this.numero = numero;
        this.palo = palo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    @Override
    public String toString() {
        return numero + palo;
    }
}
