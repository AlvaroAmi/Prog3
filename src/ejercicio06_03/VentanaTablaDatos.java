package ejercicio06_03;

import javax.swing.*;
import java.awt.*;

public class VentanaTablaDatos extends JFrame {
    protected JPanel pnlBotones;
    protected JPanel pnlTabla;
    protected JTable tabla;
    protected JButton anadir;
    protected JButton borrar;
    protected JScrollPane scroll;

    VentanaTablaDatos(){
        pnlBotones = new JPanel();
        pnlTabla = new JPanel();
        anadir = new JButton("Añadir");
        borrar = new JButton("Borrar");


        pnlBotones.add(anadir);
        pnlBotones.add(borrar);
        this.add(pnlBotones, BorderLayout.SOUTH);
        this.add(pnlTabla, BorderLayout.CENTER);

    }
    protected void setDatos(DataSetMunicipios dataset){

        modeloTabla modelo = new modeloTabla();
        tabla = new JTable(modelo);
        scroll = new JScrollPane(tabla);
        pnlTabla.add(scroll);



    }



}
