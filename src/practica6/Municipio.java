package practica6;

public class Municipio implements FilaParaJTable,Comparable {  // Especializa un comportamiento de cualquier clase que podamos querer como fila en una JTable
	private int codigo;
	private String nombre;
	private int habitantes;
	private int superficie;
	private String capital;
	private String provincia;
	private String autonomia;
	private int poblacion;

	public Municipio(int codigo, String nombre, int habitantes, int superficie, String capital, String provincia, String autonomia) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.habitantes = habitantes;
		this.superficie = superficie;
		this.capital = capital;
		this.provincia = provincia;
		this.autonomia = autonomia;
		this.poblacion = habitantes;

	}

	public int getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getHabitantes() {
		return habitantes;
	}

	public void setHabitantes(int habitantes) {
		this.habitantes = habitantes;
	}

	public int getSuperficie() {
		return superficie;
	}

	public void setSuperficie(int superficie) {
		this.superficie = superficie;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getAutonomia() {
		return autonomia;
	}

	public void setAutonomia(String autonomia) {
		this.autonomia = autonomia;
	}



	@Override
	public String toString() {
		return "Municipio{" +
				"codigo=" + codigo +
				", nombre='" + nombre + '\'' +
				", habitantes=" + habitantes +
				", superficie=" + superficie +
				", capital=" + capital +
				", provincia='" + provincia + '\'' +
				", autonomia='" + autonomia + '\'' +
				'}';
	}
	// Implementación de FilaParaJTable

	private static final Class<?>[] CLASES_COLS = { Integer.class, String.class, Integer.class,Integer.class, Integer.class, String.class, String.class, String.class};
	private static final String[] CABECERAS_COLS = { "Código", "Nombre", "Población", "Habitantes","Superficie (km2)", "Capital", "Provincia", "Autonomía" };
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return CLASES_COLS[columnIndex];
	}
	
	@Override
	public int getColumnCount() {
		return CLASES_COLS.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return CABECERAS_COLS[columnIndex];
	}

	@Override
	public Object getValueAt(int columnIndex) throws IndexOutOfBoundsException {
		switch (columnIndex) {
			case 0:
				return getCodigo();
			case 1:
				return getNombre();
			case 2:
				return getHabitantes();
			case 3:
				return getPoblacion();
			case 4:
				return getSuperficie();
			case 5:
				return getCapital();
			case 6:
				return getProvincia();
			case 7:
				return getAutonomia();
			default:
				throw new IndexOutOfBoundsException( "Columna incorrecta: " + columnIndex );
		}
	}

	@Override
	public void setValueAt(Object aValue, int columnIndex) throws ClassCastException, IndexOutOfBoundsException {
		switch (columnIndex) {
			case 0:
				setCodigo( (Integer) aValue );
				break;
			case 1:
				setNombre( (String) aValue );
				break;
			case 2:
				setHabitantes( (Integer) aValue );
				setPoblacion((Integer) aValue);
				break;
			case 3:
				setHabitantes((Integer) aValue);
				setPoblacion((Integer) aValue);
				break;
			case 4:
				setSuperficie((Integer) aValue);
				break;
			case 5:
				setCapital((String) aValue);
				break;
			case 6:
				setProvincia( (String) aValue );
				break;
			case 7:
				setAutonomia( (String) aValue );
				break;
			default:
				throw new IndexOutOfBoundsException( "Columna incorrecta: " + columnIndex );
		}
	}


	@Override
	public int compareTo(Object o) {
		return this.getNombre().compareTo(((Municipio)o).getNombre());
	}
}

