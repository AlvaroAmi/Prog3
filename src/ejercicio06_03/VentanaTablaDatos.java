package ejercicio06_03;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaTablaDatos extends JFrame {
    protected JPanel pnlBotones;
    protected JPanel pnlTabla;
    protected JTable tabla;
    protected JButton anadir;
    protected JButton borrar;
    protected JScrollPane scroll;
    modeloTabla modelo = new modeloTabla();

    VentanaTablaDatos(){
        pnlBotones = new JPanel();
        pnlTabla = new JPanel();
        anadir = new JButton("Añadir");
        borrar = new JButton("Borrar");


        pnlBotones.add(anadir);
        pnlBotones.add(borrar);
        this.add(pnlBotones, BorderLayout.SOUTH);
        this.add(pnlTabla, BorderLayout.CENTER);

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
    protected void setDatos(DataSetMunicipios dataset){


        tabla = new JTable(modelo);
        TableColumn col = tabla.getColumnModel().getColumn(0);
        col.setMaxWidth(50);
        TableColumn col2 = tabla.getColumnModel().getColumn(2);
        col2.setMaxWidth(150); col2.setMinWidth(150);
        scroll = new JScrollPane(tabla);
        pnlTabla.add(scroll);

        tabla.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == 2){
                    JProgressBar barra = new JProgressBar();
                    barra.setMinimum(0); barra.setMaximum(5000000);
                    barra.setValue((Integer) value);
                    return barra;
                }else {

                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
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

