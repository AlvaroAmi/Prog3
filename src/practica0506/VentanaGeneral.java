package practica0506;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeSet;

public class VentanaGeneral extends JFrame {
    protected static HashMap<String,UsuarioTwitter> MapaId;
    protected static HashMap<String,UsuarioTwitter> MapaNick = new HashMap<>();
    protected static CSV c = new CSV();
    protected static TreeSet<UsuarioTwitter> setAmigos = new TreeSet<>();

    protected static DefaultTableModel modeloTabla = new DefaultTableModel();
    protected static JTable table = new JTable(modeloTabla);
    static JTextArea textArea = new JTextArea();
    static JTextField textField = new JTextField("Escribe aquí para buscar la etiqueta (enter para buscar)");
    String etq = "";

    protected static void genMapaNick(){
        ArrayList<UsuarioTwitter> usuarios = c.getListaUsuarios();
        for(UsuarioTwitter usuario: usuarios) {
            if(MapaNick.containsKey(usuario.getScreenName())){
                System.out.println(usuario+"repetido");
            }
            MapaNick.put(usuario.getScreenName(),usuario);
        }
    }
    protected static void recorreAmigos(){
        int total = 0;
        for(String clave: MapaNick.keySet()){
            int dentro = 0;
            int fuera = 0;
            ArrayList<String> idAmigos = MapaNick.get(clave).getFriends();
            for(String id: idAmigos){
                if(MapaId.containsKey(id)){
                    dentro++;
                }else{
                    fuera++;
                }
            }
            if(dentro > 0){
                total++;
                MapaNick.get(clave).setAmigosDentro(dentro);
                setAmigos.add(MapaNick.get(clave));
                textArea.append("Usuario: "+clave+" tiene "+fuera+" fuera de nuestro sistema y "+dentro+" dentro"+ "\n");
                if(dentro > 10){
                    String id = MapaNick.get(clave).getId();
                    String screenName = MapaNick.get(clave).getScreenName();
                    Long followersCount = MapaNick.get(clave).getFollowersCount();
                    Long friendsCount = MapaNick.get(clave).getFriendsCount();
                    String lang = MapaNick.get(clave).getLang();
                    Long lastSeen = MapaNick.get(clave).getLastSeen();
                    if(table.getRowCount()!=0){
                        modeloTabla.insertRow(table.getRowCount(), (new String[]{""}));
                    }else{
                        modeloTabla.insertRow(0, (new String[]{""}));
                    }
                        table.setValueAt(id,table.getRowCount()-1,0);
                        table.setValueAt(screenName,table.getRowCount()-1,1);
                        table.setValueAt(followersCount,table.getRowCount()-1,2);
                        table.setValueAt(friendsCount,table.getRowCount()-1,3);
                        table.setValueAt(lang,table.getRowCount()-1,4);
                        table.setValueAt(lastSeen,table.getRowCount()-1,5);
                }
            }

        }
        textArea.append("Hay "+total+" con algunos amigos dentro de nuestro sistema."+ "\n");


    }

    public String gestionarFichero() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos .csv", "csv");
        fileChooser.setFileFilter(filtro);

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/practica0506/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String lf = properties.getProperty("ultimoFichero");
        if (lf != null) {
            fileChooser.setCurrentDirectory(new File(lf));
        }
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archSel = fileChooser.getSelectedFile();
            String rutaSel = archSel.getAbsolutePath();

            properties.setProperty("ultimoFichero", rutaSel);
            try {
                properties.store(new FileOutputStream("src/practica0506/config.properties"), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rutaSel;
        }
        return null;
    }

    protected long contarLineas(String archivo) {
        try {
            return Files.lines(Path.of(archivo)).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    VentanaGeneral(){
        this.setTitle("Gestión Twitter - Álvaro Amilibia Gascón");
        this.setSize(1700,1000);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        String archivo = gestionarFichero();

        textArea.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(textArea);
        this.add(textScrollPane, BorderLayout.WEST);


        String[] columnas = {"id","screenName","followersCount","friendsCount","lang","lastSeen"};
        modeloTabla.setColumnIdentifiers(columnas);
        JScrollPane tableScrollPane = new JScrollPane(table);
        this.add(tableScrollPane, BorderLayout.CENTER);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel def = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(column==0){
                    boolean enc = false;
                    String id = (String) table.getValueAt(row,0);
                    ArrayList<String> tags = MapaId.get(id).getTags();
                    for(String tag:tags){
                        if (etq.equals(tag)){
                            def.setBackground(Color.green);
                            enc = true;
                            break;
                        }
                    }
                    if(!enc){
                        def.setBackground(Color.white);
                    }
                }
                return def;
            }
        });



        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Raíz");
        DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode("Nodo 1");
        DefaultMutableTreeNode nodo2 = new DefaultMutableTreeNode("Nodo 2");
        root.add(nodo1);
        root.add(nodo2);
        JTree tree = new JTree(root);
        JScrollPane treeScrollPane = new JScrollPane(tree);
        this.add(treeScrollPane, BorderLayout.EAST);

        this.add(textField,BorderLayout.NORTH);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                etq = textField.getText();
                table.repaint();
            }
        });
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (textField.getText().equals("Escribe aquí para buscar la etiqueta (enter para buscar)")){
                    textField.setText("");
                }
            }
        });



        int lineas = (int) contarLineas(archivo);
        JProgressBar progressBar = new JProgressBar(0, lineas);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        this.add(progressBar, BorderLayout.SOUTH);

        this.setVisible(true);


        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                int h = 0;
                while(h<lineas-5){
                    progressBar.setValue(h);
                    h=c.getProgreso();
                }
            }
        });

        try {
            hilo.start();
            c.processCSV( new File( archivo ) );

        } catch (Exception e) {
            e.printStackTrace();
        }
        MapaId = c.getMapa();
        genMapaNick();
        recorreAmigos();
        for (UsuarioTwitter x : setAmigos) {
            textArea.append(x + "\n");
        }
        setSize(this.getWidth()+50,this.getHeight());



    }
}
