package ejercicio06_03;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Ejercicio06_03Ej {
	
	private static DataSetMunicipiosEj dataset;
	
	public static void main(String[] args) {
		JFrame ventana = new JFrame( "Ejercicio 6.3" );
		ventana.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		ventana.setLocationRelativeTo( null );
		ventana.setSize( 200, 80 );

		JButton bCargaMunicipios = new JButton( "Carga municipios > 200k" );
		ventana.add( bCargaMunicipios );
		
		bCargaMunicipios.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargaMunicipios(ventana);
				ventana.setVisible(false);
			}
		});
		
		ventana.setVisible( true );


	}

	
	private static void cargaMunicipios(JFrame ventana) {
		try {
			dataset = new DataSetMunicipiosEj( "municipios200k.txt" );
			System.out.println( "Cargados municipios:" );
			for (MunicipioEj m : dataset.getListaMunicipios() ) {
				System.out.println( "\t" + m );
			}
			// TODO Resolver el ejercicio 6.3
			VentanaTablaDatosEj ventanaTablaDatosEj = new VentanaTablaDatosEj();
			ventanaTablaDatosEj.setSize(700,500);
			ventanaTablaDatosEj.setLocationRelativeTo(null);
			ventanaTablaDatosEj.setVisible(true);
			ventanaTablaDatosEj.setDatos(dataset);
			ventanaTablaDatosEj.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					ventana.setVisible(true);
				}
			});
		} catch (IOException e) {
			System.err.println( "Error en carga de municipios" );
		}
	}




}
