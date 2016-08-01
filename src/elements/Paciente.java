package elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author made
 */
public class Paciente {
    private StringProperty CI;
    private StringProperty apellidos;
    private StringProperty nombres;
    private StringProperty nacimiento;
    private StringProperty sangre;
    private StringProperty celular;
    private StringProperty casa;
    private StringProperty direccion;
    private StringProperty email;

    public Paciente(String CI, String apellidos, String nombres, String nacimiento,
            String sangre, String celular, String casa, String direccion, String email) {
        this.CI = new SimpleStringProperty(CI);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.nombres = new SimpleStringProperty(nombres);
        this.nacimiento = new SimpleStringProperty(nacimiento);
        this.sangre = new SimpleStringProperty(sangre);
        this.celular = new SimpleStringProperty(celular);
        this.casa = new SimpleStringProperty(casa);
        this.direccion = new SimpleStringProperty(direccion);
        this.email = new SimpleStringProperty(email);
    }

    public StringProperty getCI() {
        return CI;
    }

    public void setCI(StringProperty CI) {
        this.CI = CI;
    }

    public StringProperty getApellidos() {
        return apellidos;
    }

    public void setApellidos(StringProperty apellidos) {
        this.apellidos = apellidos;
    }

    public StringProperty getNombres() {
        return nombres;
    }

    public void setNombres(StringProperty nombres) {
        this.nombres = nombres;
    }

    public StringProperty getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(StringProperty nacimiento) {
        this.nacimiento = nacimiento;
    }

    public StringProperty getSangre() {
        return sangre;
    }

    public void setSangre(StringProperty sangre) {
        this.sangre = sangre;
    }

    public StringProperty getCelular() {
        return celular;
    }

    public void setCelular(StringProperty celular) {
        this.celular = celular;
    }

    public StringProperty getCasa() {
        return casa;
    }

    public void setCasa(StringProperty casa) {
        this.casa = casa;
    }

    public StringProperty getDireccion() {
        return direccion;
    }

    public void setDireccion(StringProperty direccion) {
        this.direccion = direccion;
    }

    public StringProperty getEmail() {
        return email;
    }

    public void setEmail(StringProperty email) {
        this.email = email;
    }
    
    
}
