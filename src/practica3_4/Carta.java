package practica3_4;

public class Carta implements Comparable{ //Cartas de la baraja española
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


    @Override
    public int compareTo(Object o) {
        Carta c = (Carta) o;
        if (this.palo.compareTo(c.palo) == 0){
            return this.numero - c.numero;
        }else{
            return this.palo.compareTo(c.palo);
        }
    }
}
