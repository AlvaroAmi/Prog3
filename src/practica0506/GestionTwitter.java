package practica0506;

import java.io.File;

public class GestionTwitter {

	public static void main(String[] args) {
		try {
			// TODO Configurar el path y ruta del fichero a cargar
			String fileName = "src/practica0506/data.csv";
			CSV.processCSV( new File( fileName ) );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
