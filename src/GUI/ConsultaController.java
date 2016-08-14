package GUI;

import elements.Consulta;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author made
 */
public class ConsultaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private DatePicker conFecha;
    @FXML
    private ChoiceBox conHora;
    @FXML
    private ChoiceBox conEstado;
    ObservableList<String> conEstados = FXCollections.observableArrayList(
    "Pendiente", "Cancelada","Asistio");
    ObservableList<String> conHoras = FXCollections.observableArrayList(
    "1", "2","3");
    
    private Stage dialogStage;
    private Consulta con;
    private boolean okClicked = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conEstado.setItems(conEstados);
        conHora.setItems(conHoras);
        conEstado.setValue("Asistio");
    }    
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setConsulta(Consulta con){
        this.con = con;
        /*SET*/
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
             /*SET CON VALUES*/
            okClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
    
    private boolean isInputValid() throws SQLException{
        /*TODO*/
        return true;
    }
    
    
}
