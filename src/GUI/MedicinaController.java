package GUI;

import elements.Medicina;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static kaixo.Main.actualDB;
import static utils.JavaSQL.medExists;
import static utils.RegexMatcher.testPaxNomSearch;

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
    private ChoiceBox<String> presentacion;

    
    ObservableList<String> options = FXCollections.observableArrayList(
            "Tabletas","Jarabe", "Gotas", "Parche");
    
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
    private void handleOk() throws SQLException {
        if (isInputValid()) {   	     	
            med.setNombre(new SimpleStringProperty(nombre.getText().trim()));
            med.setConcentracion(new SimpleStringProperty(concentracion.getText().trim()));
            med.setPresentacion(new SimpleStringProperty(presentacion.getValue().toString()));
            
            okClicked = true;
            dialogStage.close();
        }                  
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() throws SQLException {
        String errorMessage = "";
        
        if (!nombre.getText().equals("")){
            if (!testPaxNomSearch(nombre.getText())){
                errorMessage += "Los nombres sólo tienen letras. \n"; 
            }
        }else{
            errorMessage += "Campo Nombre vacío \n"; 
        }
        
        if (errorMessage.length() == 0) {
            Medicina prueba = new Medicina(nombre.getText(), 
                    concentracion.getText(), presentacion.getValue().toString());
            if (medExists(actualDB, prueba)){
                errorMessage += "Los datos que vas a ingresar ya existen \n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Tienes un(os) error(es) al ingresar los datos");
        	alert.setContentText(errorMessage);
        	alert.showAndWait();
        	
            return false;
        }
    }
}
