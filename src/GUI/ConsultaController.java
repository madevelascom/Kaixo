package GUI;

import elements.Consulta;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaixo.Main;
import static kaixo.Main.actualDB;
import utils.JavaSQL;
import static utils.JavaSQL.existsCons;

/**
 * FXML Controller class
 *
 * @author made
 */
public class ConsultaController extends Main implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private DatePicker conFecha;
    @FXML
    private ChoiceBox conHora;
    @FXML
    private ChoiceBox conEstado;
    /*
    @FXML
    private TextField conNombres;
    
    @FXML
    private TextField conApellidos;
    */
    
    ObservableList<String> conEstados = FXCollections.observableArrayList(
    "pendiente", "cancelada","asistio","no asistio");
    ObservableList<String> conHoras = FXCollections.observableArrayList(
    "11:00", "12:00","13:00");
    
    private Stage dialogStage;
    private Consulta con;
    private boolean okClicked = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conEstado.setItems(conEstados);
        conHora.setItems(conHoras);
        conEstado.setValue("Pendiente");
    }    
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setConsulta(Consulta con) throws SQLException{
        this.con = con;
        /*
        debe editarse
        conFecha.setValue(LocalDate.MAX);
        */
        conHora.setValue(con.getFecha().getValue());
        conEstado.setValue(con.getEstado().getValue());
        
        /*
        conNombres.setText(JavaSQL.selecPaxName(actualDB, con.getPaciente().getValue()));
        conApellidos.setText(JavaSQL.selecPaxName(actualDB, con.getPaciente().getValue())); 
        */
    }
    
    public void disableEstado(){
        conEstado.setDisable(true);
    }
    
    public boolean isOkClicked(){
        return okClicked;
    }
    
    @FXML
    public void handleOk() throws SQLException{
        if(isInputValid()){   
            String S;
            //S = JavaSQL.getCIByName(actualDB, conNombres.getText(), conApellidos.getText());
            
            //con.setPaciente(new SimpleStringProperty(S));
            con.setEstado(new SimpleStringProperty(conEstado.getValue().toString()));
            con.setFecha(new SimpleStringProperty(conFecha.getValue().toString()+ " "+ conHora.getValue()));
            System.out.println(new SimpleStringProperty(conFecha.getValue().toString()));
            okClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
    
    public boolean isInputValid() throws SQLException{
        String errorMessage = "";
        
        if(conFecha.getValue().isBefore(LocalDate.now())){
            errorMessage += "La fecha de la consulta es incorrecta. "
                    + "No puede ser menor al día de hoy \n";
        }
        
        String date = conFecha.getValue().toString()+ " "+ conHora.getValue();
        
        if (existsCons(actualDB, date)){
            errorMessage += "Ya tienes una consulta programada para ese día y hora \n";
        }
        /*
        if (!JavaSQL.existsPaxName(actualDB, conNombres.getText(), conApellidos.getText())){
            errorMessage += "El paciente solicitado no existe, por favor ingresarlo \n";
        }
*/
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
    
    
}
