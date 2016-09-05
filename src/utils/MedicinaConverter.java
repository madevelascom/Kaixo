package utils;

import elements.Medicina;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.StringConverter;

/**
 *
 * @author made
 */
public class MedicinaConverter extends StringConverter<Medicina> {
    @Override
    public Medicina fromString(String str) {
        Medicina temp = new Medicina();
        String[] parts = str.split("--");
        temp.setNombre(new SimpleStringProperty(parts[0]));
        temp.setConcentracion(new SimpleStringProperty(parts[1]));
        temp.setPresentacion(new SimpleStringProperty(parts[2]));
        
        return temp;
    }
    
    @Override
    public String toString(Medicina temp) {
        return temp.getNombre().getValue()
                +"--"+temp.getConcentracion().getValue()
                +"--"+temp.getPresentacion().getValue();
    }
}
