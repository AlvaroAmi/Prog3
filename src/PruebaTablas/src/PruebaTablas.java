package PruebaTablas.src;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

public class PruebaTablas extends JFrame {
    protected JTable tabla;
    protected JPanel panel;
    protected DefaultTableModel modelo;
    protected JLabel etiqueta;
    protected JPanel pnletiqueta;
    protected boolean pintar;
    protected int filaa;


    public PruebaTablas(){
        this.setSize(400,400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel();
        modelo = new DefaultTableModel(new Object[]{"Nombre","Alta","Rango"},0);
        tabla = new JTable(modelo);
        modelo.addRow(new Object[]{"Pedro",2022,34});
        modelo.addRow(new Object[]{"Juan",2003,89});

        etiqueta = new JLabel("");
        pnletiqueta = new JPanel();
        pnletiqueta.add(etiqueta);

        panel.add(tabla);
        this.add(panel, BorderLayout.CENTER);
        this.add(pnletiqueta, BorderLayout.SOUTH);
        this.setVisible(true);



        tabla.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                //Hacer que la etiqueta muestre el valor de la celda
                if (tabla.columnAtPoint(e.getPoint()) == 0){
                    pintar = true;
                    int fila = tabla.rowAtPoint(e.getPoint());
                    filaa = fila;
                    int columna = tabla.columnAtPoint(e.getPoint());
                etiqueta.setText("Nombre: "+tabla.getValueAt(fila,columna)+ ", Año de alta: "+tabla.getValueAt(fila,columna+1) + ", Rango: " + tabla.getValueAt(fila,columna+2));
            }else {
                    etiqueta.setText("");
                    pintar = false;
                }

            }
        });


        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 2) {
                    JLabel etiqueta = (JLabel) comp;
                    JProgressBar barra = new JProgressBar();
                    barra.setMaximum(100); barra.setMinimum(0);
                    barra.setValue(Integer.parseInt(etiqueta.getText()));
                    return barra;
                }else if (column == 0 && pintar==true && row == filaa){
                    JLabel etiqueta = (JLabel) comp;
                    comp.setBackground(Color.magenta);
                    tabla.repaint();
                    return etiqueta;

                }else{
                    comp.setBackground(Color.white);
                    return comp;
                }

            }
        });

        tabla.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()){
            JSpinner sp = new JSpinner();

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                Component comp = super.getTableCellEditorComponent(table, value, isSelected, row, column);
                if (column == 1){

                    JSpinner spin = new JSpinner();
                    spin.setValue(Integer.parseInt(value.toString()));
                    spin.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            sp.setValue(spin.getValue());
                        }
                    });

                return spin;
                }else{
                sp = null;
                return comp;
            }}

            @Override
            public Object getCellEditorValue() {
            if (sp != null){

                return sp.getValue();
            }
                sp = new JSpinner();
                return super.getCellEditorValue();
            }
        });

    }




    public static void main(String[] args) {
    new PruebaTablas();




    }
}