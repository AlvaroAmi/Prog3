package practica6;

import java.io.*;
import java.util.*;

/** Permite gestionar datasets de municipios. Cada objeto contiene un dataset de 'n' municipios
 */
public class DataSetMunicipios extends DatasetParaJTable {
	public HashMap<String,ArrayList> mapa = new HashMap<>();

	public Map getMapa(){
		for (int i = 0; i < size(); i++) {
			Municipio muni = (Municipio) get(i);
			if (mapa.containsKey(muni.getAutonomia())) {
				if (!mapa.get(muni.getAutonomia()).contains(muni.getProvincia())){
				mapa.get(muni.getAutonomia()).add(muni.getProvincia());}
			} else {
				ArrayList<String> lista = new ArrayList<>();
				lista.add(muni.getProvincia());
				mapa.put(muni.getAutonomia(), lista);
			}
		}


		System.out.println(mapa);
		return mapa;
	};









	public DataSetMunicipios( String nombreFichero ) throws IOException {
		super( new Municipio( 0, "", 0, 0, "", "","") );
		File ficMunicipios = new File( nombreFichero );
		Scanner lecturaFic = null;
		if (ficMunicipios.exists()) {
			lecturaFic = new Scanner( ficMunicipios );
		} else {
			lecturaFic = new Scanner( DataSetMunicipios.class.getResourceAsStream( nombreFichero ) );
		}
		int numLinea = 0;
		while (lecturaFic.hasNextLine()) {
			numLinea++;
			String linea = lecturaFic.nextLine();
			String[] partes = linea.split( ",");
			try {
				int codigo = Integer.parseInt( partes[0] );
				String nombre = partes[1];
				int habitantes = Integer.parseInt( partes[2] );
				int superficie = Integer.parseInt(partes[3]);
				String capital = partes[4];
				String provincia = partes[5];
				String comunidad = partes[6];
				Municipio muni = new Municipio( codigo, nombre, habitantes, superficie, capital, provincia, comunidad );
				add( muni );
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				e.printStackTrace();
				System.err.println( "Error en lectura de línea " + numLinea );
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
