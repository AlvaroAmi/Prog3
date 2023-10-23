package practica6;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ModeloTabla extends DatasetParaJTable{

    public ModeloTabla( String nombreFichero ) throws IOException {
        super( new Municipio( 0, "", 0, 0, "", "","") );


            try {

                add( muni );
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                e.printStackTrace();

            }
        }
    }

    /** Devuelve la lista de municipios
     * @return	Lista de municipios
     */
    @SuppressWarnings("unchecked")
    public List<Municipio> getListaMunicipios() {
        return (List<Municipio>) getLista();
    }

    /** Añade un municipio al final
     * @param muni	Municipio a añadir
     */
    public void anyadir( Municipio muni ) {
        add( muni );
    }

    /** Añade un municipio en un punto dado
     * @param muni	Municipio a añadir
     * @param posicion	Posición relativa del municipio a añadir (de 0 a n)
     */
    public void anyadir( Municipio muni, int posicion ) {
        anyadeFila( posicion, muni );
    }

    /** Quita un municipio
     * @param codigoMuni	Código del municipio a eliminar
     */
    public void quitar( int codigoMuni ) {
        for (int i=0; i<size(); i++) {
            if (((Municipio)get(i)).getCodigo() == codigoMuni) {
                borraFila( i );
                return;
            }
        }
    }

    // Queremos que las celdas sean editables excepto el código
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return false;
        }
        return true;
    }
}
