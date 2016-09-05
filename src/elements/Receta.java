package elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author INTEL
 */
public class Receta {
    private StringProperty id_consulta;
    private StringProperty id_medicina;
    private StringProperty frecuencia;
    private StringProperty nombre;
    private StringProperty concentracion;

    public Receta(String id_consulta, String id_medicina, String frecuencia, String nombre, String concentracion) {
        this.id_consulta = new SimpleStringProperty(id_consulta);
        this.id_medicina = new SimpleStringProperty(id_medicina);
        this.frecuencia =  new SimpleStringProperty(frecuencia);
        this.nombre = new SimpleStringProperty(frecuencia);
        this.concentracion = new SimpleStringProperty(concentracion);
    }

    public Receta() {
        this.id_consulta = new SimpleStringProperty("");
        this.id_medicina = new SimpleStringProperty("");
        this.frecuencia =  new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.concentracion = new SimpleStringProperty("");
    }

    public StringProperty getId_consulta() {
        return id_consulta;
    }

    public StringProperty getId_medicina() {
        return id_medicina;
    }

    public StringProperty getFrecuencia() {
        return frecuencia;
    }

    public void setId_consulta(StringProperty id_consulta) {
        this.id_consulta = id_consulta;
    }

    public void setId_medicina(StringProperty id_medicina) {
        this.id_medicina = id_medicina;
    }

    public void setFrecuencia(StringProperty frecuencia) {
        this.frecuencia = frecuencia;
    }

    public StringProperty getNombre() {
        return nombre;
    }

    public void setNombre(StringProperty nombre) {
        this.nombre = nombre;
    }

    public StringProperty getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(StringProperty concentracion) {
        this.concentracion = concentracion;
    }

    @Override
    public String toString() {
        return "Receta{" + "id_consulta=" + id_consulta + ", id_medicina=" + id_medicina + ", frecuencia=" + frecuencia + ", nombre=" + nombre + ", concentracion=" + concentracion + '}';
    }
    
    
    
    
    
}
