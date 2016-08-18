package elements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author made
 */
public class Valoracion {
    private IntegerProperty presion;
    private IntegerProperty glucosa;
    private DoubleProperty peso;

    public Valoracion(int presion, int glucosa, double peso) {
        this.presion = new SimpleIntegerProperty(presion);
        this.glucosa = new SimpleIntegerProperty(glucosa);
        this.peso = new SimpleDoubleProperty(peso);
    }

    public Valoracion() {
        this.presion = new SimpleIntegerProperty(0);
        this.glucosa = new SimpleIntegerProperty(0);
        this.peso = new SimpleDoubleProperty(0);
        
    }

    public IntegerProperty getPresion() {
        return presion;
    }

    public void setPresion(IntegerProperty presion) {
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
