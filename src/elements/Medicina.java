package elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author made
 */
public class Medicina {
    private StringProperty nombre;
    private StringProperty concentracion;
    private StringProperty presentacion;

    public Medicina(String nombre, String concentracion, String presentacion) {
        this.nombre         = new SimpleStringProperty(nombre);
        this.concentracion  = new SimpleStringProperty(concentracion);
        this.presentacion   = new SimpleStringProperty(presentacion)  ;
    }

    public Medicina() {
        this.nombre         = new SimpleStringProperty("");
        this.concentracion  = new SimpleStringProperty("");
        this.presentacion   = new SimpleStringProperty("Pastilla")  ;
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

    public StringProperty getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(StringProperty presentacion) {
        this.presentacion = presentacion;
    }

    @Override
    public String toString() {
        return nombre.getValue() + "" + concentracion.getValue() + '}';
    }
    
    

}
