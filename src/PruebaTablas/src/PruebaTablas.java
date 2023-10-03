package PruebaTablas.src;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PruebaTablas extends JFrame {
    protected JTable tabla;
    protected JPanel panel;
    protected DefaultTableModel modelo;

    public PruebaTablas(){
        this.setSize(400,400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel();
        modelo = new DefaultTableModel(new Object[]{"Nombre","Alta","Rango"},0);
        tabla = new JTable(modelo);
        modelo.addRow(new Object[]{"Pedro",2022,34});

        panel.add(tabla);
        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);

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

                }
                return comp;
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