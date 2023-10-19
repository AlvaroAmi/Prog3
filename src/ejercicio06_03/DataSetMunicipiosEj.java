package ejercicio06_03;

import java.io.*;
import java.util.*;

/** Permite gestionar datasets de municipios. Cada objeto contiene un dataset de 'n' municipios
 */
public class DataSetMunicipiosEj {
	
	private List<MunicipioEj> lMunicipioEjs = new ArrayList<MunicipioEj>();
	
	/** Crea un nuevo dataset de municipios, cargando los datos desde el fichero indicado
	 * @param nombreFichero	Nombre de fichero o recurso en formato de texto. En cada línea debe incluir los datos de un municipio <br>
	 * separados por tabulador: código nombre habitantes provincia autonomía
	 * @throws IOException	Si hay error en la lectura del fichero
	 */
	public DataSetMunicipiosEj(String nombreFichero ) throws IOException {
		File ficMunicipios = new File( nombreFichero );
		Scanner lecturaFic = null;
		if (ficMunicipios.exists()) {
			lecturaFic = new Scanner( ficMunicipios );
		} else {
			lecturaFic = new Scanner( DataSetMunicipiosEj.class.getResourceAsStream( nombreFichero ) );
		}
		int numLinea = 0;
		while (lecturaFic.hasNextLine()) {
			numLinea++;
			String linea = lecturaFic.nextLine();
			String[] partes = linea.split( "\t" );
			try {
				int codigo = Integer.parseInt( partes[0] );
				String nombre = partes[1];
				int habitantes = Integer.parseInt( partes[2] );
				String provincia = partes[3];
				String comunidad = partes[4];
				MunicipioEj muni = new MunicipioEj( codigo, nombre, habitantes, provincia, comunidad );
				lMunicipioEjs.add( muni );
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.err.println( "Error en lectura de línea " + numLinea );
			}
		}
	}
	
	/** Devuelve la lista de municipios
	 * @return	Lista de municipios
	 */
	public List<MunicipioEj> getListaMunicipios() {
		return lMunicipioEjs;
	}
	
	/** Añade un municipio al final
	 * @param muni	Municipio a añadir
	 */
	public void anyadir( MunicipioEj muni ) {
		lMunicipioEjs.add( muni );
	}
	
	/** Añade un municipio en un punto dado
	 * @param muni	Municipio a añadir
	 * @param posicion	Posición relativa del municipio a añadir (de 0 a n)
	 */
	public void anyadir(MunicipioEj muni, int posicion ) {
		lMunicipioEjs.add( posicion, muni );
	}
	
	/** Quita un municipio
	 * @param codigoMuni	Código del municipio a eliminar
	 */
	public void quitar( int codigoMuni ) {
		for (int i = 0; i< lMunicipioEjs.size(); i++) {
			if (lMunicipioEjs.get(i).getCodigo() == codigoMuni) {
				lMunicipioEjs.remove(i);
				return;
			}
		}
	}
	
}
