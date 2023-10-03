package ejercicio06;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaAleatoria extends JFrame {
	private static Dimension TAMANYO_INICIAL_VENTANA = new Dimension( 400, 300 );
	protected JPanel botones;
	protected JComboBox sel;
	protected JTable tabla;
	protected JButton aleatorio;
	protected JPanel pnltabla;
	protected DefaultTableModel modelo = new DefaultTableModel();
	
	protected int rnd() {
		return (int)((Math.random())*1000);
		
	}
	
	public VentanaAleatoria() {
		setSize( TAMANYO_INICIAL_VENTANA );
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		botones = new JPanel();
		sel = new JComboBox();
		sel.addItem("2x2"); sel.addItem("3x3"); sel.addItem("4x4"); sel.addItem("5x5");
		botones.add(sel);
		aleatorio = new JButton("Aleatorio");
		botones.add(aleatorio);
		this.add(botones,BorderLayout.SOUTH);
 
		
		modelo.addColumn("1");
		modelo.addColumn("2");
		modelo.addRow(new Object[] {});
		modelo.addRow(new Object[] {});
		
		
		tabla = new JTable(modelo);
		pnltabla = new JPanel();
		pnltabla.add(tabla);
		this.add(pnltabla,BorderLayout.CENTER);
		
		sel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(sel.getSelectedItem() == "2x2") {
					modelo = new DefaultTableModel(2,2) {
						
					};
					
					tabla.setModel(modelo);
				}else if(sel.getSelectedItem() =="3x3") {
					modelo = new DefaultTableModel(3,3);
					tabla.setModel(modelo);
				}else if(sel.getSelectedItem() =="4x4") {
					modelo = new DefaultTableModel(4,4);
					tabla.setModel(modelo);
				}else if(sel.getSelectedItem() =="5x5") {
					modelo = new DefaultTableModel(5,5);
					tabla.setModel(modelo);
				}
				
			}
			
		});
		

		aleatorio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread hilo = new Thread(new Runnable() {

					@Override
					public void run() {
						int t = 0;
						sel.enable(false);
						while (t<3000) {	
						
						for(int i =0; i<modelo.getColumnCount();i++) {
							for(int j = 0; j< modelo.getRowCount(); j++) {
								modelo.setValueAt(rnd(), i, j);
							}
						}
						
						
						try {
							Thread.sleep(1);
							t++;
						}catch(Exception e) {
							
						}}
						sel.enable(true);
					}
					
				});
				hilo.start();
				
			}
			
		});
		
	}
	
	
	
	
	public static void main(String[] args) {
		VentanaAleatoria v = new VentanaAleatoria();
		v.setVisible(true);
		
	}

}