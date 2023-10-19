package ejercicio06_03;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaTablaDatosEj extends JFrame {
    protected JPanel pnlBotones;
    protected JPanel pnlTabla;
    protected JTable tabla;
    protected JButton anadir;
    protected JButton borrar;
    protected JScrollPane scroll;
    modeloTablaEj modelo = new modeloTablaEj();

    VentanaTablaDatosEj(){
        pnlBotones = new JPanel();
        pnlTabla = new JPanel();
        anadir = new JButton("Añadir");
        borrar = new JButton("Borrar");


        pnlBotones.add(anadir);
        pnlBotones.add(borrar);
        this.add(pnlBotones, BorderLayout.SOUTH);
        this.add(pnlTabla, BorderLayout.CENTER);

        anadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cod = modelo.getDataSetMunicipios().getListaMunicipios().size()+1;
                modelo.getDataSetMunicipios().anyadir(new MunicipioEj(cod,"",0,"",""));
                fireTableChanged(new TableModelEvent( modelo, 1, modelo.getDataSetMunicipios().getListaMunicipios().size()+1 ));

            }
        });



        borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tabla.getSelectedRow();
                if(fila!= -1){
                    modelo.getDataSetMunicipios().quitar(modelo.getDataSetMunicipios().getListaMunicipios().get(fila).getCodigo());
                    borrarFila(fila);
                    tabla.repaint();
                }

            }
        });




    }
    protected void setDatos(DataSetMunicipiosEj dataset){


        tabla = new JTable(modelo);
        tabla.setToolTipText("");

        TableColumn col = tabla.getColumnModel().getColumn(0);
        col.setMaxWidth(50);
        TableColumn col2 = tabla.getColumnModel().getColumn(2);
        col2.setMaxWidth(150); col2.setMinWidth(150);
        scroll = new JScrollPane(tabla);
        pnlTabla.add(scroll);

        tabla.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){
            JProgressBar barra = new JProgressBar(){
                protected void paintComponent(java.awt.Graphics g){
                    super.paintComponent(g);
                    g.setColor(Color.black);
                    g.drawString(getValue()+"", 50, 10);
                }
            };
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 2){

                    barra.setMinimum(0); barra.setMaximum(5000000);
                    barra.setValue((Integer) value);


                    return barra;
                }
                if (column == 4){
                    int fila = tabla.getSelectedRow();
                    if (fila > 0 && tabla.getSelectedColumn() == 4){
                        if(row == tabla.getSelectedRow() || tabla.getValueAt(row,4).equals(tabla.getValueAt(fila,4)) )
                            label.setBackground(Color.cyan);
                            tabla.repaint();
                        return label;
                    }else if(row == tabla.getSelectedRow()&& tabla.getSelectedColumn() == 4)
                     label.setBackground(Color.cyan);
                        tabla.repaint();
                    return label;
                }
                    label.setBackground(Color.white);
                    tabla.repaint();
                    return label;


            }
        });

        tabla.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                int col; int fil;
                col = tabla.columnAtPoint(e.getPoint());
                fil = tabla.rowAtPoint(e.getPoint());
                if (col == 2){
                    tabla.setToolTipText("Población: " +tabla.getValueAt(fil,col).toString());
                }else{
                    tabla.setToolTipText(null);
                }



            }


        });



    }



    ArrayList<TableModelListener> listaEsc = new ArrayList<>();
    public void addTableModelListener(TableModelListener l){
        listaEsc.add(l);
    }
    public void removeTableModelListener(TableModelListener l){
        listaEsc.remove(l);
    }
    public void fireTableChanged(TableModelEvent e){
        for(TableModelListener l : listaEsc){
            l.tableChanged(e);
        }
    }
    public void borrarFila(int fila){
        fireTableChanged(new TableModelEvent(modelo, fila, modelo.getDataSetMunicipios().getListaMunicipios().size()));
    }



}

