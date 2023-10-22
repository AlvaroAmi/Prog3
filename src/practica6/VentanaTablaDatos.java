package practica6;

import com.sun.source.tree.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

@SuppressWarnings("serial")
public class VentanaTablaDatos extends JFrame {

	private static final int COL_AUTONOMIA = 4;
	
	private JTable tablaDatos;
	private DataSetMunicipios datosMunis;
	private JLabel mensaje;
	private JTree tree;
	private JPanel panelvisual;
	private DefaultTreeModel modelotree;


	private String autonomiaSeleccionada = "";
	
	public VentanaTablaDatos( JFrame ventOrigen ) {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 900, 600 );
		setLocationRelativeTo( null );
		
		tablaDatos = new JTable();
		add( new JScrollPane( tablaDatos ), BorderLayout.CENTER );

		mensaje = new JLabel("");
		this.add(mensaje,BorderLayout.NORTH);

		tree = new JTree();
		JScrollPane ptree = new JScrollPane(tree);
		this.add(ptree,BorderLayout.WEST);

		panelvisual = new JPanel();
		this.add(panelvisual,BorderLayout.EAST);


		JPanel pInferior = new JPanel();
		JButton bAnyadir = new JButton( "Añadir" );
		JButton bBorrar = new JButton( "Borrar" );
		JButton bOrden = new JButton("Ordenar");
		pInferior.add( bAnyadir );
		pInferior.add( bBorrar );
		pInferior.add(bOrden);
		add( pInferior, BorderLayout.SOUTH );
		
		this.addWindowListener( new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				ventOrigen.setVisible( false );
			}
			@Override
			public void windowClosed(WindowEvent e) {
				ventOrigen.setVisible( true );
			}
		});
	
		bBorrar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaDatos.getSelectedRow();
				if (filaSel >= 0) {
					datosMunis.borraFila( filaSel );
				}
			}
		});
		
		bAnyadir.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaDatos.getSelectedRow();
				if (filaSel>=0) {
					datosMunis.anyadeFila( filaSel, new Municipio( datosMunis.getListaMunicipios().size()+1, "Nombre", 0, 0, "","","" ) );
				}
			}
		});
		
	}
	
	public void setDatos( DataSetMunicipios datosMunis ) {

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Municipios");
		modelotree = new DefaultTreeModel(raiz);
		tree.setModel(modelotree);

		HashMap mapa = (HashMap) datosMunis.getMapa();
		for (int i = 0; i<mapa.keySet().size();i++){
			DefaultMutableTreeNode autonomia = new DefaultMutableTreeNode(mapa.keySet().toArray()[i]);
			modelotree.insertNodeInto(autonomia,raiz,i);
				for (Object o : (ArrayList) mapa.get(mapa.keySet().toArray()[i])) {
					DefaultMutableTreeNode provincia = new DefaultMutableTreeNode(o);
					modelotree.insertNodeInto(provincia,autonomia,autonomia.getChildCount());

				}



		}




		this.datosMunis = datosMunis;
		tablaDatos.setModel( datosMunis );

		tablaDatos.setDefaultRenderer( Integer.class, new DefaultTableCellRenderer() {
			private JProgressBar pbHabs = new JProgressBar( 0, 5000000 ) {
				protected void paintComponent(java.awt.Graphics g) {
					super.paintComponent(g);
					g.setColor( Color.BLACK );
					g.drawString( getValue()+"", 50, 10 );
				}
			};
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// System.out.println( "getTCR " + row + "," + column );
				if (column==2) {
					// Si el dato es un Object o String sería esto
					// int valorCelda = Integer.parseInt( value.toString() );
					// pbHabs.setValue( valorCelda );
					// return pbHabs;
					// Pero si el dato está asegurado ser un Integer se puede castear:
					pbHabs.setValue( (Integer)value );
					return pbHabs;
				}
				JLabel rendPorDefecto = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				return rendPorDefecto;
			}
			
		});
		
		tablaDatos.setDefaultRenderer( String.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column );
				c.setBackground( Color.WHITE );
				if (isSelected) {
					c.setBackground( Color.LIGHT_GRAY );
				}
				if (column==COL_AUTONOMIA) {
					if (autonomiaSeleccionada.equals( (String)value )) {
						c.setBackground( Color.CYAN );
					}
				}
				return c;
			}
		} );
		
		tablaDatos.addMouseMotionListener( new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int filaEnTabla = tablaDatos.rowAtPoint( e.getPoint() );
				int colEnTabla = tablaDatos.columnAtPoint( e.getPoint() );
				if (colEnTabla == 2) {
					int numHabs = datosMunis.getListaMunicipios().get(filaEnTabla).getHabitantes();
					tablaDatos.setToolTipText( String.format( "Población: %,d", numHabs ) );
				} else {
					tablaDatos.setToolTipText( null );  // Desactiva
				}
			}
		});

		tablaDatos.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaEnTabla = tablaDatos.rowAtPoint( e.getPoint() );
				int colEnTabla = tablaDatos.columnAtPoint( e.getPoint() );
				if (colEnTabla == COL_AUTONOMIA && filaEnTabla>=0) {
					autonomiaSeleccionada = datosMunis.getListaMunicipios().get(filaEnTabla).getAutonomia();
				} else {
					autonomiaSeleccionada = "";
				}
				tablaDatos.repaint();
			}
		});

		tablaDatos.setDefaultEditor( Integer.class, new DefaultCellEditor( new JTextField() ) {
			SpinnerNumberModel mSpinner = new SpinnerNumberModel( 200000, 200000, 5000000, 1000 );
			JSpinner spinner = new JSpinner( mSpinner );
			boolean lanzadoSpinner;
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { // Componente que se pone en la tabla al editar una celda
				if (column != 2) {
					lanzadoSpinner = false;
					return super.getTableCellEditorComponent(table, value, isSelected, row, column);
				}
				mSpinner.setValue( (Integer) value );
				lanzadoSpinner = true;
				return spinner;
			}
			@Override
			public Object getCellEditorValue() { // Valor que se retorna al acabar la edición
				if (lanzadoSpinner) {
					return spinner.getValue();
				} else {
					return Integer.parseInt( super.getCellEditorValue().toString() );
				}
			}
		});
		
	}
	
}
