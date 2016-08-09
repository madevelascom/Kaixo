package GUI;

import elements.Paciente;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaixo.Main;
import static utils.RegexMatcher.testEmail;
import static utils.JavaSQL.existsPax;
import static utils.RegexMatcher.testPaxCISearch;
import static utils.RegexMatcher.testPaxNomSearch;

/**
 * FXML Controller class
 *
 * @author made
 */
public class PacienteController extends Main implements Initializable {
    
    @FXML
    private TextField paxCI;
    @FXML
    private TextField paxNombre;
    @FXML
    private TextField paxApellido;
    @FXML
    private DatePicker paxNacimiento;
    @FXML
    private ChoiceBox paxSangre;
    @FXML
    private TextField paxCelular;
    @FXML
    private TextField paxCasa;
    @FXML
    private TextArea paxDi;
    @FXML
    private TextField paxEmail;
    
    private Stage dialogStage;
    private Paciente pat;
    private boolean okClicked = false;
    private boolean isUpdate = true;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setPaciente(Paciente pat){
        this.pat = pat;
        paxCI.setText(pat.getCI().getValue());
        paxNombre.setText(pat.getNombres().getValue());
        paxApellido.setText(pat.getApellidos().getValue());
        //paxNacimiento.set
        //paxSangre
        paxCelular.setText(pat.getCelular().getValue());
        paxCasa.setText(pat.getCasa().getValue());
        paxDi.setText(pat.getDireccion().getValue());
        paxEmail.setText(pat.getEmail().getValue());                     
    }
    
    public void disableCI(){
        paxCI.setDisable(isUpdate);
    }
    
    public boolean isOkClicked(){
        return okClicked;
    }
    
    public boolean isUpdatePax(){
        return isUpdate;
    }
    
    public void setUpdate(boolean setNew){
        this.isUpdate = setNew;
    }
    
    @FXML
    public void handleOk() throws SQLException{
        if(isInputValid()){
            /*paxCI.setText(pat.getCI().getValue());
        paxNombre.setText(pat.getNombres().getValue());
        paxApellido.setText(pat.getApellidos().getValue());
        //paxNacimiento.set
        //paxSangre
        paxCelular.setText(pat.getCelular().getValue());
        paxCasa.setText(pat.getCasa().getValue());
        paxDi.setText(pat.getDireccion().getValue());
        paxEmail.setText(pat.getEmail().getValue()); */
                  
        }
    }
    
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
    
    private boolean isInputValid() throws SQLException{
        String errorMessage = "";
        
        if (isUpdatePax()){
            
        }else{
            if (!paxCI.getText().equals("")){
                if (testPaxCISearch(paxCI.getText())){
                    if (existsPax(actualDB, paxCI.getText())){
                        errorMessage += "Ya existe un paciente con esa CI. \n"; 
                    }
                }else{
                    errorMessage += "Las cédulas tienen 10 dígitos. \n"; 
                }                
            }else{
                errorMessage += "Campo CI vacío \n"; 
            }
            if (!paxNombre.getText().equals("")){
                if (!testPaxNomSearch(paxNombre.getText())){
                    errorMessage += "Los nombres sólo tienen letras. \n"; 
                }
            }else{
                errorMessage += "Campo Nombre vacío \n"; 
            }
            if (!paxApellido.getText().equals("")){
                if (!testPaxNomSearch(paxNombre.getText())){
                    errorMessage += "Los nombres sólo tienen letras. \n"; 
                }
            }else{
                errorMessage += "Campo Apellido vacío \n";
            }
            if (!paxCelular.getText().equals("")){

            }else{
                errorMessage += "Campo Celular vacío \n";
            }
            if (!paxCasa.getText().equals("")){

            }else{
                errorMessage += "Campo Casa vacío \n";
            }
            if (!paxDi.getText().equals("")){

            }else{
                errorMessage += "Campo Direccion vacío \n";
            }
        }
        
        
        
        if (!paxEmail.getText().equals("")){
            if (!testEmail(paxEmail.getText())){
                errorMessage += "Cantidad de créditos te�ricos vac�o \n"; 
            }
        }
            
        if (errorMessage.length() == 0) {
            return true;
        } else {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Tienes un(os) error(es) al ingresar los datos");
        	alert.setContentText(errorMessage);
        	alert.showAndWait();
        	
            return false;
        }
    }
    
}
