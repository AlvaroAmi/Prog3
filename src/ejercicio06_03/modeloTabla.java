package ejercicio06_03;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.io.IOException;

public class modeloTabla implements TableModel {

    protected DataSetMunicipios dataSetMunicipios;

    {
        try {
            dataSetMunicipios = new DataSetMunicipios("municipios200k.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRowCount() {
        return dataSetMunicipios.getListaMunicipios().size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0){
            return "Código";
        } else if (columnIndex == 1) {
            return "Nombre";
        } else if (columnIndex == 2) {
            return "Habitantes";
        } else if (columnIndex == 3) {
            return "Provincia";
        }else{
            return "Autonomía";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex != 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0){
            return dataSetMunicipios.getListaMunicipios().get(rowIndex).getCodigo();
        } else if (columnIndex == 1) {
            return (String)dataSetMunicipios.getListaMunicipios().get(rowIndex).getNombre();
        } else if (columnIndex == 2) {
            return dataSetMunicipios.getListaMunicipios().get(rowIndex).getHabitantes();
        } else if (columnIndex == 3) {
            return dataSetMunicipios.getListaMunicipios().get(rowIndex).getProvincia();
        } else {
            return dataSetMunicipios.getListaMunicipios().get(rowIndex).getAutonomia();
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1){
        dataSetMunicipios.getListaMunicipios().get(rowIndex).setNombre(aValue.toString());
        } else if (columnIndex == 2) {
            dataSetMunicipios.getListaMunicipios().get(rowIndex).setHabitantes(Integer.parseInt(aValue.toString()));
        } else if (columnIndex ==3) {
            dataSetMunicipios.getListaMunicipios().get(rowIndex).setProvincia(aValue.toString());
        } else{
            dataSetMunicipios.getListaMunicipios().get(rowIndex).setAutonomia(aValue.toString());

        }

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    public DataSetMunicipios getDataSetMunicipios() {
        return dataSetMunicipios;
    }
}
