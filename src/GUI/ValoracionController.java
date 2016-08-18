package GUI;

import elements.Medicina;
import elements.Valoracion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static utils.RegexMatcher.testGlucosa;
import static utils.RegexMatcher.testPeso;

/**
 * FXML Controller class
 *
 * @author INTEL
 */
public class ValoracionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField presion;
    @FXML
    private TextField glucosa;
    @FXML
    private TextField peso;
    
    private Stage dialogStage;
    private Valoracion val;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setValoracion(Valoracion val){
        this.val = val;   
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    public void handleOk() throws SQLException{
        if(isInputValid()){  
            val.setPresion(new SimpleStringProperty(presion.getText().trim()));
            val.setGlucosa(new SimpleIntegerProperty(Integer.parseInt(glucosa.getText())));
            val.setPeso(new SimpleDoubleProperty(Double.parseDouble(peso.getText().trim())));
            
            okClicked = true;
            dialogStage.close();
        }
    }
    
    public boolean isInputValid() throws SQLException{
        String errorMessage = "";
        System.out.println(presion.getText());
        System.out.println(glucosa.getText());
        System.out.println(peso.getText());
        if (presion.getText().equals("")){
            errorMessage += "Campo presión vacío \n"; 
        }
        
        if (glucosa.getText().equals("")){
            errorMessage += "Campo glucosa vacío \n"; 
        }else{
            if (!testGlucosa(glucosa.getText())){
                errorMessage += "Sólo puedes ingresar números enteros en el campo Glucosa \n"; 
            }
        }
        
        if (peso.getText().equals("")){
            errorMessage += "Campo peso vacío \n"; 
        }else{
            if (!testPeso(peso.getText())){
                errorMessage += "Sólo puedes ingresar números  en el campo peso \n"; 
            }
           
        }
        
        if (errorMessage.length() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Tienes un(os) error(es) al ingresar los datos");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        	
            return false;
        }
        
        return true;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
