package ejercicio06_03;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.io.IOException;

public class modeloTablaEj implements TableModel {

    protected DataSetMunicipiosEj dataSetMunicipiosEj;

    {
        try {
            dataSetMunicipiosEj = new DataSetMunicipiosEj("municipios200k.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRowCount() {
        return dataSetMunicipiosEj.getListaMunicipios().size();
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
            return dataSetMunicipiosEj.getListaMunicipios().get(rowIndex).getCodigo();
        } else if (columnIndex == 1) {
            return (String) dataSetMunicipiosEj.getListaMunicipios().get(rowIndex).getNombre();
        } else if (columnIndex == 2) {
            return dataSetMunicipiosEj.getListaMunicipios().get(rowIndex).getHabitantes();
        } else if (columnIndex == 3) {
            return dataSetMunicipiosEj.getListaMunicipios().get(rowIndex).getProvincia();
        } else {
            return dataSetMunicipiosEj.getListaMunicipios().get(rowIndex).getAutonomia();
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1){
        dataSetMunicipiosEj.getListaMunicipios().get(rowIndex).setNombre(aValue.toString());
        } else if (columnIndex == 2) {
            dataSetMunicipiosEj.getListaMunicipios().get(rowIndex).setHabitantes(Integer.parseInt(aValue.toString()));
        } else if (columnIndex ==3) {
            dataSetMunicipiosEj.getListaMunicipios().get(rowIndex).setProvincia(aValue.toString());
        } else{
            dataSetMunicipiosEj.getListaMunicipios().get(rowIndex).setAutonomia(aValue.toString());

        }

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    public DataSetMunicipiosEj getDataSetMunicipios() {
        return dataSetMunicipiosEj;
    }
}
