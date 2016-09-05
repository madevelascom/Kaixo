package GUI;

import elements.Consulta;
import elements.Medicina;
import elements.Receta;
import elements.Valoracion;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaixo.Main;
import utils.JavaSQL;

/**
 * FXML Controller class
 *
 * @author made
 */
public class RecetaController implements Initializable  {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML 
    private ComboBox<String>  medicina ;
    
    @FXML 
    private ComboBox<String>  presentacion;
    @FXML 
    private TextField frecuencia;
    
    @FXML
    private TableView<Receta> recTable;
    
    @FXML
    private TableColumn<Receta, String> recMedicina;
        
    @FXML
    private TableColumn<Receta, String> recFrecuencia;
            
    @FXML
    private TableColumn<Receta, String> recConcentracion;
    
    
        
    private Stage dialogStage;
    
    private Receta receta;
    
    private Consulta consulta;
    
    ObservableList<Receta> MedicinasFrecuencias ;
    
    ObservableList<String> Names = Main.getMedDataNames();
    
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public void setConsulta(Consulta cons) {
        this.consulta = cons;
    }

    public void setMedicinasFrecuencias(ObservableList<Receta> MedicinasFrecuencias) {
        this.MedicinasFrecuencias = MedicinasFrecuencias;
    }
    
    

    public boolean isOkClicked() {
        return okClicked;
    }
    
    public void handleAñadir() throws SQLException{
        
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
        System.out.println("entro");
        
        medicina.setItems(Names);
        
        
        

        recTable.setItems(MedicinasFrecuencias);
        recMedicina.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        recFrecuencia.setCellValueFactory(cellData -> cellData.getValue().getFrecuencia());
        recConcentracion.setCellValueFactory(cellData -> cellData.getValue().getConcentracion());

        
        
        
        

        
        
    }    
    
}
