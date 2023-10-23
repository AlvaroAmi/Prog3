package practica6;

import com.sun.source.tree.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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
	private DataSetMunicipios datosMunis = new DataSetMunicipios( "municipios.txt" );
	private JLabel mensaje;
	private JTree tree;
	private JPanel panelvisual;
	private DefaultTreeModel modelotree;
	private ModeloTabla modeloTabla;
	private int seleccion = -1;

	private String autonomiaSeleccionada = "";
	
	public VentanaTablaDatos( JFrame ventOrigen ) throws IOException {
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

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				String sel = e.getPath().getLastPathComponent().toString();
				modeloTabla = new ModeloTabla(datosMunis, sel);
				tablaDatos.setModel(modeloTabla);
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


		tablaDatos.setDefaultRenderer( Integer.class, new DefaultTableCellRenderer() {
			private JProgressBar pbHabs = new JProgressBar( 0, 1000000 ) { //No utilizo 50k - 5M porque la mayoría de municipios tienen muy pocos habitantes.

			};

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (column==2) {
					pbHabs.setValue( (Integer)value );
					if ((Integer)value < 1000000) {
						float color = (float) (0.3 - (0.3 / 1000000) * ((Integer) value).floatValue());
						pbHabs.setForeground(Color.getHSBColor(color, 1, 1));
					}else pbHabs.setForeground(Color.getHSBColor(0.0F, 1, 1));
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
				JLabel lbl = (JLabel) c;
				if (column == 1 && seleccion == -1){
					lbl.setBackground(Color.white);
					return lbl;
				}if(column == 1 && seleccion != -1){
					if(Integer.parseInt((String) value)>= seleccion){ //TODO: Que compruebe la población y no el nombre
						lbl.setBackground(Color.red);
						return lbl;
					}else{
						lbl.setBackground(Color.green);
						return lbl;
					}
				}

				return c;
			}
		} );

		tablaDatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3){
					Point p = e.getPoint();
					if (tablaDatos.columnAtPoint(p) == 1){
					seleccion = (int) tablaDatos.getValueAt(tablaDatos.rowAtPoint(p),3);
					System.out.println(seleccion);
					}else{
						seleccion = -1;
					}
				}
			}
		});




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
