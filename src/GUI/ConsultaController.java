package GUI;

import elements.Consulta;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import kaixo.Main;
import static kaixo.Main.actualDB;
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
    private ChoiceBox<String> conHora;
    @FXML
    private ChoiceBox<String> conEstado;
    
    ObservableList<String> conEstados = FXCollections.observableArrayList(
    "Pendiente", "Cancelada","Asistida","No asistida");
    ObservableList<String> conHoras = FXCollections.observableArrayList(
    "16:00", "16:30","17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00");
    
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDate date = LocalDate.parse(con.getFecha().getValue(), formatter);
        LocalTime time = LocalTime.parse(con.getFecha().getValue(), formatter);
        
        conFecha.setValue(date);
        conHora.setValue(time.toString());
        conEstado.setValue(con.getEstado().getValue());
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
            con.setEstado(new SimpleStringProperty(conEstado.getValue()));
            con.setFecha(new SimpleStringProperty(conFecha.getValue().toString()+ " "+ conHora.getValue()));
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
        String date = conFecha.getValue().toString()+ " "+ conHora.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime consult = LocalDateTime.parse(date, formatter);
        
        if(consult.isBefore(LocalDateTime.now())){
            errorMessage += "La fecha de la consulta es incorrecta. "
                    + "No puede ser menor al día de hoy \n";
        }
    
        if (existsCons(actualDB, date)){
            errorMessage += "Ya tienes una consulta programada para ese día y hora \n";
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
    
    
}
