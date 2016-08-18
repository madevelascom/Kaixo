package elements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author made
 */
public class Valoracion {
    private StringProperty presion;
    private IntegerProperty glucosa;
    private DoubleProperty peso;

    public Valoracion(String presion, int glucosa, double peso) {
        this.presion    = new SimpleStringProperty(presion);
        this.glucosa    = new SimpleIntegerProperty(glucosa);
        this.peso       = new SimpleDoubleProperty(peso);
    }

    public Valoracion() {
        this.presion    = new SimpleStringProperty("0");
        this.glucosa    = new SimpleIntegerProperty(0);
        this.peso       = new SimpleDoubleProperty(0);
        
    }

    public StringProperty getPresion() {
        return presion;
    }

    public void setPresion(StringProperty presion) {
        this.presion = presion;
    }

    public IntegerProperty getGlucosa() {
        return glucosa;
    }

    public void setGlucosa(IntegerProperty glucosa) {
        this.glucosa = glucosa;
    }

    public DoubleProperty getPeso() {
        return peso;
    }

    public void setPeso(DoubleProperty peso) {
        this.peso = peso;
    }
    
    
}
