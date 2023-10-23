package practica6;

import java.util.*;

public class ModeloTabla extends DatasetParaJTable{
    protected ArrayList<Municipio> provisional;
    protected static final int COL_CODIGO = 0;
    protected static final int COL_PROVINCIA = 6;
    protected static final int COL_COMUNIDAD = 7;

    public ModeloTabla(DataSetMunicipios datos, String provincia )  {
        super( new Municipio( 0, "", 0, 0, "", "","") );
        provisional = new ArrayList<>();
        for (Municipio m : datos.getListaMunicipios()){
            if (m.getProvincia().equals(provincia)){
                provisional.add(m);}}
        Collections.sort(provisional);
        for (Municipio m2 : provisional){
            add(m2);
        }
    }

    public List<Municipio> getListaMunicipios() {
        return (List<Municipio>) getLista();
    }

    public void anyadir( Municipio muni ) {
        add( muni );
    }

    public void anyadir( Municipio muni, int posicion ) {
        anyadeFila( posicion, muni );
    }


    public void quitar( int codigoMuni ) {
        for (int i=0; i<size(); i++) {
            if (((Municipio)get(i)).getCodigo() == codigoMuni) {
                borraFila( i );
                return;
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == COL_CODIGO || columnIndex == COL_PROVINCIA|| columnIndex == COL_COMUNIDAD) {
            return false;
        }
        return true;
    }
}
