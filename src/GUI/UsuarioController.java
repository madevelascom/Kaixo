package GUI;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static kaixo.Main.primaryStage;
import static utils.RegexMatcher.testPassword;

/**
 * FXML Controller class
 *
 * @author made
 */
public class UsuarioController implements Initializable {

    @FXML
    private TextField user;
    @FXML
    private PasswordField pass_1;
    @FXML
    private PasswordField pass_2;
    
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean isUpdate = true;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
     private void handleExit(){
         primaryStage.close();
         System.exit(0);
    }
     
    public void disableUsername(){
        user.setDisable(true);
    }
    
    public boolean isOkClicked(){
        return okClicked;
    }
    
    @FXML
    public void handleOk() throws SQLException{
        if(isInputValid()){
                
        }
    }
    
    private boolean isInputValid() throws SQLException{
        String errorMessage = "";
        
        if (user.getText().isEmpty()){
            errorMessage += "El nombre de usuario no debe de estar vacío \n"; 
        }
        
        if (!testPassword(pass_1.getText())){
            errorMessage += "Las contraseñas deben tener mínimo seis caracteres"
                    + " entre números y letras \n"; 
        }
        if (!pass_1.getText().equals(pass_2.getText())){
            errorMessage += "Las contraseñas deben coincidir. \n"; 
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
