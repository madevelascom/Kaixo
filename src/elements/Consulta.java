package elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author made
 */
public class Consulta {
    private StringProperty fecha;
    private StringProperty paciente;
    private StringProperty estado;
    private StringProperty diag;
    private IntegerProperty valoracion;

    public Consulta(String fecha, String paciente, String estado, String diag, int valoracion) {
        this.fecha      = new SimpleStringProperty(fecha);
        this.paciente   = new SimpleStringProperty(paciente);
        this.estado     = new SimpleStringProperty(estado);
        this.diag       = new SimpleStringProperty(diag);
        this.valoracion = new SimpleIntegerProperty(valoracion);
    }

    public Consulta(String fecha, String paciente, String estado) {
        this.fecha      = new SimpleStringProperty(fecha);
        this.paciente   = new SimpleStringProperty(paciente);
        this.estado     = new SimpleStringProperty(estado);
    }
    
    public Consulta(String paciente){
        this.fecha      = new SimpleStringProperty(new 
            SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));
        this.paciente   = new SimpleStringProperty(paciente);
        this.estado     = new SimpleStringProperty("Pendiente");
    }
    
    public Consulta(){
        this.fecha      = new SimpleStringProperty("");
        this.paciente   = new SimpleStringProperty("");
        this.estado     = new SimpleStringProperty("");
        
    }

    public StringProperty getFecha() {
        return fecha;
    }

    public void setFecha(StringProperty fecha) {
        this.fecha = fecha;
    }

    public StringProperty getPaciente() {
        return paciente;
    }

    public void setPaciente(StringProperty paciente) {
        this.paciente = paciente;
    }

    public StringProperty getEstado() {
        return estado;
    }

    public void setEstado(StringProperty estado) {
        this.estado = estado;
    }

    public StringProperty getDiag() {
        return diag;
    }

    public void setDiag(StringProperty diag) {
        this.diag = diag;
    }

    public IntegerProperty getValoracion() {
        return valoracion;
    }

    public void setValoracion(IntegerProperty valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "Consulta{" + "fecha=" + fecha + ", paciente=" + paciente + ", estado=" + estado + '}';
    }
    
    
    
    
}


