package GUI;

import elements.Consulta;
import elements.Medicina;
import elements.Paciente;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaixo.Main;
import utils.ConnectedComboBox;
import utils.JavaSQL;
import static utils.JavaSQL.loadMedicinas;
import utils.MedicinaConverter;
import utils.PDF;

/**
 * FXML Controller class
 *
 * @author made
 */
public class RecetaController extends Main implements Initializable {

    @FXML
    private TextField freq_1;
    @FXML
    private TextField freq_2;
    @FXML
    private TextField freq_3;
    @FXML
    private TextField freq_4;
    @FXML
    private TextField freq_5;
    @FXML
    private TextField freq_6;
    @FXML
    private TextField freq_7;
    @FXML
    private TextField freq_8;
    
    @FXML
    private ChoiceBox<Medicina> med_1;
    @FXML
    private ChoiceBox<Medicina> med_2;
    @FXML
    private ChoiceBox<Medicina> med_3;
    @FXML
    private ChoiceBox<Medicina> med_4;
    @FXML
    private ChoiceBox<Medicina> med_5;
    @FXML
    private ChoiceBox<Medicina> med_6;
    @FXML
    private ChoiceBox<Medicina> med_7;
    @FXML
    private ChoiceBox<Medicina> med_8;
    
    private Stage dialogStage;
    private Consulta consulta;
    private boolean okClicked = false;
    ObservableList<Medicina> choices = FXCollections.observableArrayList();
    HashMap<Medicina, String> result = new HashMap<Medicina, String>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            choices.addAll(loadMedicinas(actualDB));
        } catch (SQLException ex) {
            System.out.println("FAILED LOAD MEDICINA RECETA");
        }
        med_1.setConverter(new MedicinaConverter());
        
        ConnectedComboBox<Medicina> connectedComboBox = new ConnectedComboBox<>(choices);
        connectedComboBox.addComboBox(med_1);
        connectedComboBox.addComboBox(med_2);
        connectedComboBox.addComboBox(med_3);
        connectedComboBox.addComboBox(med_4);
        connectedComboBox.addComboBox(med_5);
        connectedComboBox.addComboBox(med_6);
        connectedComboBox.addComboBox(med_7);
        connectedComboBox.addComboBox(med_8);
               
    }    
    
    public void setMedList(HashMap<Medicina, String> result){
        this.result = result;        
    }
    
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() throws SQLException {
        if (isInputValid()) { 
            if (!med_1.getSelectionModel().isEmpty() && !freq_1.getText().isEmpty()){
                result.put(med_1.getSelectionModel().getSelectedItem(), freq_1.getText());
            }
            if (!med_2.getSelectionModel().isEmpty() && !freq_2.getText().isEmpty()){
                result.put(med_2.getSelectionModel().getSelectedItem(), freq_2.getText());
            }
            if (!med_3.getSelectionModel().isEmpty() && !freq_3.getText().isEmpty()){
                result.put(med_3.getSelectionModel().getSelectedItem(), freq_3.getText());
            }
            if (!med_4.getSelectionModel().isEmpty() && !freq_4.getText().isEmpty()){
                result.put(med_4.getSelectionModel().getSelectedItem(), freq_4.getText());
            }
            if (!med_5.getSelectionModel().isEmpty() && !freq_5.getText().isEmpty()){
                result.put(med_5.getSelectionModel().getSelectedItem(), freq_5.getText());
            }
            if (!med_6.getSelectionModel().isEmpty() && !freq_6.getText().isEmpty()){
                result.put(med_6.getSelectionModel().getSelectedItem(), freq_6.getText());
            }
            if (!med_7.getSelectionModel().isEmpty() && !freq_7.getText().isEmpty()){
                result.put(med_7.getSelectionModel().getSelectedItem(), freq_7.getText());
            }
            if (!med_8.getSelectionModel().isEmpty() && !freq_8.getText().isEmpty()){
                result.put(med_8.getSelectionModel().getSelectedItem(), freq_8.getText());
            }
            
            okClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    @FXML
    private void handleReporte() throws SQLException, IOException{
        HashMap<Medicina, String> mapa = new HashMap<Medicina, String>();
        Paciente pac = new Paciente();
        if (isInputValid()) {
            if (!med_1.getSelectionModel().isEmpty() && !freq_1.getText().isEmpty()){
                mapa.put(med_1.getSelectionModel().getSelectedItem(), freq_1.getText());
            }
            if (!med_2.getSelectionModel().isEmpty() && !freq_2.getText().isEmpty()){
                mapa.put(med_2.getSelectionModel().getSelectedItem(), freq_2.getText());
            }
            if (!med_3.getSelectionModel().isEmpty() && !freq_3.getText().isEmpty()){
                mapa.put(med_3.getSelectionModel().getSelectedItem(), freq_3.getText());
            }
            if (!med_4.getSelectionModel().isEmpty() && !freq_4.getText().isEmpty()){
                mapa.put(med_4.getSelectionModel().getSelectedItem(), freq_4.getText());
            }
            if (!med_5.getSelectionModel().isEmpty() && !freq_5.getText().isEmpty()){
                mapa.put(med_5.getSelectionModel().getSelectedItem(), freq_5.getText());
            }
            if (!med_6.getSelectionModel().isEmpty() && !freq_6.getText().isEmpty()){
                mapa.put(med_6.getSelectionModel().getSelectedItem(), freq_6.getText());
            }
            if (!med_7.getSelectionModel().isEmpty() && !freq_7.getText().isEmpty()){
                mapa.put(med_7.getSelectionModel().getSelectedItem(), freq_7.getText());
            }
            if (!med_8.getSelectionModel().isEmpty() && !freq_8.getText().isEmpty()){
                mapa.put(med_8.getSelectionModel().getSelectedItem(), freq_8.getText());
            }
            
            pac = JavaSQL.generarPaciente(actualDB, consulta);
            
           PDF.print(consulta.getFecha().getValue(), pac, mapa);
            //PDF(mapa, consulta , medicina);
            
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Diálogo informativo");
            alert.setHeaderText(null);
            alert.setContentText("Reporte generado correctamente");

            alert.showAndWait();
            
        }
        
    }
    
    
    
    
    
    
    
    
    
    private boolean isInputValid() throws SQLException {
        String errorMessage = "";
        
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


