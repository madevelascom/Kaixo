package GUI;

import elements.Paciente;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import kaixo.Main;
import static utils.RegexMatcher.testEmail;
import static utils.JavaSQL.existsPax;
import static utils.RegexMatcher.testCasa;
import static utils.RegexMatcher.testCel;
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
    private ChoiceBox<String> paxSangre;
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
    
    ObservableList<String> SangreOpt = FXCollections.observableArrayList(
    "O+", "O-","A+", "A-", "B+", "B-", "AB+", "AB-");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paxSangre.setItems(SangreOpt);
        paxSangre.setValue("O+");        
    }    
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setPaciente(Paciente pat){
        this.pat = pat;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(pat.getNacimiento().getValue(), formatter);
        
        paxCI.setText(pat.getCI().getValue());
        paxNombre.setText(pat.getNombres().getValue());
        paxApellido.setText(pat.getApellidos().getValue());
        paxNacimiento.setPromptText(pat.getNacimiento().getValue().toLowerCase());
        paxNacimiento.setValue(date);
        paxSangre.setValue(pat.getSangre().getValue());
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
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            pat.setCI(new SimpleStringProperty(paxCI.getText().trim()));
            pat.setNombres(new SimpleStringProperty(paxNombre.getText().trim()));
            pat.setApellidos(new SimpleStringProperty(paxApellido.getText().trim()));
            pat.setNacimiento(new SimpleStringProperty(paxNacimiento.getValue().format(fmt)));
            pat.setSangre(new SimpleStringProperty(paxSangre.getValue()));
            pat.setCelular(new SimpleStringProperty(paxCelular.getText().trim()));
            pat.setCasa(new SimpleStringProperty(paxCasa.getText().trim()));
            pat.setDireccion(new SimpleStringProperty(paxDi.getText().trim()));
            pat.setEmail(new SimpleStringProperty(paxEmail.getText().trim()));

            okClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
    
    private boolean isInputValid() throws SQLException{
        String errorMessage = "";
        
        if (!paxNombre.getText().equals("")){
            if (!testPaxNomSearch(paxNombre.getText())){
                errorMessage += "Los nombres sólo tienen letras. \n"; 
            }
        }else{
            errorMessage += "Campo Nombre vacío \n"; 
        }
            
        if (!paxApellido.getText().equals("")){
            if (!testPaxNomSearch(paxNombre.getText())){
                errorMessage += "Los apellidos sólo tienen letras. \n"; 
            }
        }else{
            errorMessage += "Campo Apellido vacío \n";
        }
            
        if (!paxCelular.getText().equals("")){
             if (!testCel(paxCI.getText())){
                errorMessage += "Los números celulares tienen 10 dígitos \n";
             }
        }else{
            errorMessage += "Campo Celular vacío \n";
        }
            
        if (!paxCasa.getText().equals("")){
            if (!testCasa(paxCasa.getText())){
                errorMessage += "Los teléfonos tienen 9 dígitos \n";
            }
        }else{
            errorMessage += "Campo Casa vacío \n";
        }
            
        if (paxDi.getText().equals("")){
            errorMessage += "Campo Direccion vacío \n";
        }
        
        if(paxNacimiento.getValue().isAfter(LocalDate.now()) ){
            errorMessage += "La fecha de nacimiento es incorrecta. "
                    + "No puede ser mayor al día de hoy \n";
        }
        
        if (!isUpdatePax()){           
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
