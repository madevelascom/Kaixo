package elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author made
 */
public class Distribuidor {
    private StringProperty nombre;
    private StringProperty direccion;
    private StringProperty telefono;
    
    public Distribuidor(){
        this.nombre     = new SimpleStringProperty("");
        this.direccion  = new SimpleStringProperty ("");
        this.telefono   = new SimpleStringProperty("");
    
    }

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

    @Override
    public String toString() {
        return "Distribuidor{" + "nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + '}';
    }
    
    
}
