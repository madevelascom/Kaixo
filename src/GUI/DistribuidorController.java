package GUI;

import elements.Distribuidor;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaixo.Main;
import static utils.JavaSQL.distExists;
import static utils.RegexMatcher.testCasa;
import static utils.RegexMatcher.testPaxNomSearch;

/**
 * FXML Controller class
 *
 * @author made
 */
public class DistribuidorController extends Main implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private TextField nombre;
    @FXML
    private TextField telefono;
    @FXML
    private TextArea direccion;
    
    private Distribuidor dist;
    private Stage dialogStage;
    public Distribuidor prev;
    
    private boolean okClicked = false;
    
    
    public void setStage(Stage dialogStage){
        this.dialogStage = dialogStage;  
    }
    
    public boolean isOkClicked(){
        return okClicked;
    }
    
    public void setDistribuidor(Distribuidor dist){
        this.dist = dist;
        
        direccion.setText(dist.getDireccion().getValue());
        nombre.setText(dist.getNombre().getValue());
        telefono.setText(dist.getTelefono().getValue());
        prev = new Distribuidor(dist.getNombre().getValue(), 
                dist.getDireccion().getValue(), dist.getTelefono().getValue());
    }
    
    public Distribuidor getPrev(){
        prev = new Distribuidor(dist.getNombre().getValue(), 
                dist.getDireccion().getValue(), dist.getTelefono().getValue());
        return prev;
    }
            
    @FXML
    public void handleOk() throws SQLException{
        if (isInputValid()){
            dist.setNombre(new SimpleStringProperty(this.nombre.getText()));
            dist.setDireccion(new SimpleStringProperty(this.direccion.getText()));
            dist.setTelefono(new SimpleStringProperty(this.telefono.getText()));

            this.okClicked = true;
            dialogStage.close();
        }                     
    }
    
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
          
    
    public boolean isInputValid() throws SQLException{
        String errorMessage = "";
        
        if (!nombre.getText().equals("")){
            if (!testPaxNomSearch(nombre.getText())){
                errorMessage += "Los nombres sólo tienen letras. \n"; 
            }
        }else{
            errorMessage += "Campo Nombre vacío \n"; 
        }
        
        if (direccion.getText().equals("")){
            errorMessage += "Campo Direccion vacío \n";
        }
        
        if (!telefono.getText().equals("")){
            if (!testCasa(telefono.getText())){
                errorMessage += "Los teléfonos tienen 9 dígitos \n";
            }
        }else{
            errorMessage += "Campo Casa vacío \n";
        }
        
        if (errorMessage.length() == 0) {
            Distribuidor prueba = new Distribuidor(nombre.getText(), 
                    direccion.getText(), telefono.getText());
            if(distExists(actualDB, prueba)){
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
    

 
    
}
