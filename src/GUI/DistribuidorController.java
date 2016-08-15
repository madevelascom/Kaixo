package GUI;

import elements.Distribuidor;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author made
 */
public class DistribuidorController implements Initializable {

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
    private boolean okClicked = false;
    private Stage dialogStage;
    
    
    public void setStage(Stage dialogStage){
        this.dialogStage = dialogStage;  
    }
    
    public boolean isOkclicked(){
       return okClicked; 
    }
    
    
    public void setDistribuidor(Distribuidor dist){
        this.dist = dist;
        
        direccion.setText(dist.getDireccion().getValue());
        nombre.setText(dist.getNombre().getValue());
        telefono.setText(dist.getTelefono().getValue());
        
    }
    @FXML
    public void handleGuardar(){
        StringProperty nombre = new SimpleStringProperty(this.nombre.getText());
        StringProperty direccion = new SimpleStringProperty(this.direccion.getText());
        StringProperty telefono = new SimpleStringProperty(this.telefono.getText());
        dist.setNombre(nombre);
        dist.setDireccion(direccion);
        dist.setTelefono(telefono);
        
        this.okClicked = true;
        
        dialogStage.close();
        
        
    }
    @FXML
    public void handleCancelar(){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación de cancelar");
                alert.setHeaderText("va a cancelar");
                alert.setContentText("¿Estás seguro?");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK){
                    dialogStage.close();
                } 
    }
          
    
    public boolean isValid(){
        //Aqui debe editarse
        return true;
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
    

 
    
}
