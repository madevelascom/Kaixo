package GUI;

import elements.Medicina;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author made
 */
public class MedicinaController implements Initializable {

    @FXML
    private TextField nombre;
    @FXML
    private TextField concentracion;
    @FXML
    private ChoiceBox presentacion;

    
    ObservableList<String> options = FXCollections.observableArrayList("Pastilla","Jarabe");
    
    private Stage dialogStage;
    private Medicina med;
    private boolean okClicked = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        presentacion.setValue("Pastilla");
        presentacion.setItems(options);
    }    
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setMedicina(Medicina med){
        this.med = med;
        
        nombre.setText(med.getNombre().getValue());
        concentracion.setText(med.getConcentracion().getValue());
        presentacion.setValue(med.getPresentacion().getValue());
        
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {   	
            StringProperty prop = new SimpleStringProperty(nombre.getText());        	
            med.setNombre(prop);
            prop = new SimpleStringProperty(concentracion.getText()); 
            med.setConcentracion(prop);
            prop = new SimpleStringProperty(presentacion.getSelectionModel().getSelectedItem().toString()); 
            med.setPresentacion(prop);
            
            okClicked = true;
            dialogStage.close();
        }
                    
    }
    
    @FXML
        private void handleCancel() {
            dialogStage.close();
        }

    private boolean isInputValid() {
        //@TODO 
        return true;
    }
}
