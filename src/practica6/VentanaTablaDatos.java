package practica6;

import com.sun.source.tree.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.*;

@SuppressWarnings("serial")
public class VentanaTablaDatos extends JFrame {

	private static final int COL_AUTONOMIA = 4;
	private static final int COL_CODIGO = 0;
	
	private JTable tablaDatos;
	private DataSetMunicipios datosMunis = new DataSetMunicipios( "municipios.txt" );
	private JLabel mensaje;
	private JTree tree;
	private JPanel panelvisual;
	private DefaultTreeModel modelotree;
	private ModeloTabla modeloTabla;
	private int seleccion = -1;
	private DefaultMutableTreeNode sel;
	private int orden = 1; //1 -> Alfabeticamente 2-> Habitantes
	private String autonomiaSeleccionada = "";


	public VentanaTablaDatos(JFrame ventOrigen ) throws IOException {
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

		bOrden.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			if (orden == 1){
				orden = 2;
			}else{
				orden = 1;
			}
				setMap(datosMunis);
				modeloTabla = new ModeloTabla(datosMunis, sel.toString(),orden);
				tablaDatos.setModel(modeloTabla);

			}
		});
	
		bBorrar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmar = JOptionPane.showOptionDialog(null, "¿Estás seguro?", "Confirmar", JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,null,JOptionPane.NO_OPTION);
				if (confirmar == 0){
				int filaSel = tablaDatos.getSelectedRow();
				if (filaSel >= 0) {
					int codigo = (int) tablaDatos.getModel().getValueAt(filaSel, COL_CODIGO);
					datosMunis.getListaMunicipios().remove(getIndex(codigo));
					setMap(datosMunis);
					modeloTabla = new ModeloTabla(datosMunis, sel.toString(),orden);
					tablaDatos.setModel(modeloTabla);}
				}
			}
		});

		bAnyadir.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sel.getParent().toString() != "Municipios"){
				try{
					datosMunis.anyadir(new Municipio(datosMunis.getListaMunicipios().get(datosMunis.getLista().size()-1).getCodigo()+1,"",50000,0,"",sel.toString(),sel.getParent().toString()));
					setMap(datosMunis);
					modeloTabla = new ModeloTabla(datosMunis, sel.toString(),orden);
					tablaDatos.setModel(modeloTabla);}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Debes seleccionar una autonomía", "Error", JOptionPane.ERROR_MESSAGE);
				}}

			}}
		);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {

				sel = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				modeloTabla = new ModeloTabla(datosMunis, sel.toString(),orden);
				tablaDatos.setModel(modeloTabla);
			}
		});
	}

	public int getIndex(int codigo){
		int i = 0;
		for (Municipio mun:datosMunis.getListaMunicipios()){
			if (mun.getCodigo()==codigo){
				return i;
			}
			i++;
		}
        return 0;
    }

	public int gpoblacion(String value,boolean leaf){
		int suma = 0;
		if(datosMunis != null){
		if (value.equals("Municipios")){
			for (Municipio m: datosMunis.getListaMunicipios()){
				suma += m.getHabitantes();
			}

		} else if (!leaf){
		for (Municipio m: datosMunis.getListaMunicipios()){
			if (m.getAutonomia().equals(value.toString())){
				suma += m.getHabitantes();
			}
		}}
		}

        return suma;
    }

    public void setMap(DataSetMunicipios datosMunis) {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Municipios");
		modelotree = new DefaultTreeModel(raiz);
		tree.setModel(modelotree);

		HashMap mapa = (HashMap) datosMunis.getMapa();
		for (int i = 0; i < mapa.keySet().size(); i++) {
			DefaultMutableTreeNode autonomia = new DefaultMutableTreeNode(mapa.keySet().toArray()[i]);
			modelotree.insertNodeInto(autonomia, raiz, i);
			for (Object o : (ArrayList) mapa.get(mapa.keySet().toArray()[i])) {
				DefaultMutableTreeNode provincia = new DefaultMutableTreeNode(o);
				modelotree.insertNodeInto(provincia, autonomia, autonomia.getChildCount());

			}
		}
		TreePath pathToExpand = new TreePath(raiz.getPath());
		tree.expandPath(pathToExpand);
		for (int i = 0; i < raiz.getChildCount(); i++) {
			TreePath childPath = pathToExpand.pathByAddingChild(raiz.getChildAt(i));
			tree.expandPath(childPath);
		}


	}
		public void setDatos( DataSetMunicipios datosMunis) {
		setMap(datosMunis);

			tree.setCellRenderer(new DefaultTreeCellRenderer() {
				@Override
				public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                    JLabel defaul = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                    JPanel panel = new JPanel();
                    if (value.toString().equals("Municipios")) {
                        return defaul;
                    } else if (!leaf && !value.toString().equals("Municipios")) {
                        System.out.println(value.toString());
                        int valor = gpoblacion(value.toString(), leaf);
                        JProgressBar barra = new JProgressBar(0, gpoblacion("Municipios", leaf));
                        barra.setValue(valor);
                        panel.add(defaul);
                        panel.add(barra);
                        System.out.println(gpoblacion("Municipios", leaf));
                        return panel;
                    }
                    return defaul;
                }
			});

		tablaDatos.setDefaultRenderer( Integer.class, new DefaultTableCellRenderer() {
			private JProgressBar pbHabs = new JProgressBar( 0, 1000000 ) { //No utilizo 50k - 5M porque la mayoría de municipios tienen muy pocos habitantes (para que se vea mejor la barra)
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
				if ((column == 1 && seleccion == -1)||(column != 1)){
					lbl.setBackground(Color.white);
					return lbl;
				}if(column == 1 && seleccion != -1){
					int val = (int) tablaDatos.getValueAt(row,3);
					if(val>= seleccion){
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
			SpinnerNumberModel mSpinner = new SpinnerNumberModel( 200000, 0, 5000000, 1000 );
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
			public Object getCellEditorValue() {
				seleccion = -1;
				if (lanzadoSpinner) {
					return spinner.getValue();
				} else {
					return Integer.parseInt( super.getCellEditorValue().toString() );
				}
			}
		});
		

	
}}
