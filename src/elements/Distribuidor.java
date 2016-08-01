package elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author made
 */
public class Distribuidor {
    private StringProperty nombre;
    private StringProperty direccion;
    private StringProperty telefono;

    public Distribuidor(String nombre, String direccion, String telefono) {
        this.nombre     = new SimpleStringProperty(nombre);
        this.direccion  = new SimpleStringProperty (direccion);
        this.telefono   = new SimpleStringProperty(telefono);
    }

    public StringProperty getNombre() {
        return nombre;
    }

    public void setNombre(StringProperty nombre) {
        this.nombre = nombre;
    }

    public StringProperty getDireccion() {
        return direccion;
    }

    public void setDireccion(StringProperty direccion) {
        this.direccion = direccion;
    }

    public StringProperty getTelefono() {
        return telefono;
    }

    public void setTelefono(StringProperty telefono) {
        this.telefono = telefono;
    }
}
