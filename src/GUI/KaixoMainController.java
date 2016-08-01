package GUI;

import elements.Distribuidor;
import elements.Medicina;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import kaixo.Main;

/**
 * FXML Controller class
 *
 * @author made
 */
public class KaixoMainController extends Main implements Initializable {
    
    private ObservableList<Medicina> masterMedData = Main.getMedData();
    //Medicina
    @FXML
    private TableView<Medicina> medTable;
    @FXML
    private TableColumn<Medicina, String> nomMed;
    @FXML
    private TextField medSearch;
    @FXML
    private Label medNom;
    @FXML
    private Label medCon;
    @FXML
    private Label medPres;
    @FXML
    private Label medDist;
    
    //Distribuidores
    @FXML
    private TableView<Distribuidor> distTable;
    @FXML
    private TableColumn<Distribuidor, String> nomDist;
    @FXML
    private TextField distSearch;
    @FXML
    private Label distNom;
    @FXML
    private Label distTel;
    @FXML
    private Label distDir;
    
    private void showMedDetails(Medicina med){
        if (med != null){
            medNom.setText(med.getNombre().getValue());
            medCon.setText(med.getConcentracion().getValue());
            medPres.setText(med.getPresentacion().getValue());
        }else{
            medNom.setText("");
            medCon.setText("");
            medPres.setText("");
        }
    }
    
    private void ShowDistDetails (Distribuidor dist){
        if (dist != null){
            distNom.setText(dist.getNombre().getValue());
            distTel.setText(dist.getTelefono().getValue());
            distDir.setText(dist.getDireccion().getValue());
        }else{
            distNom.setText("");
            distTel.setText("");
            distDir.setText("");
        }
    }
    
    @FXML
    private void handleNewMedicina(){
       Medicina temp = new Medicina();
       boolean onClicked = Main.showMedicinaDialog(temp);
       if (onClicked){
           Main.getMedData().add(temp);
       }
    }
    
    @FXML
    private void handleEditMedicina(){
        int selectedIndex = medTable.getSelectionModel().getSelectedIndex();	
            if (selectedIndex >= 0) {			
                    Medicina selected = medTable.getItems().get(selectedIndex);
                    if (selected != null) {
                    boolean okClicked = Main.showMedicinaDialog(selected);
                    if (okClicked) {
                        showMedDetails(selected);
                    }
                } 
            }    
    }
    
    @FXML
    private void handleDelMed(){
        int selectedIndex = medTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
                Alert alert = new Alert(AlertType.CONFIRMATION);

                alert.setTitle("Confirmación de borrado");
                alert.setHeaderText("Vas a borrar la medicina que escogiste");
                alert.setContentText("¿Estás seguro?");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK){ 
                    //SQL STATEMENT ABOUT DELETE HERE
                    medTable.getItems().remove(selectedIndex);
                    
                }  
        }
   }
    
    public void buscarMedicina(){
        nomMed.setCellValueFactory ( cellData -> cellData.getValue().nomProperty()); 	
        FilteredList<Medicina> filteredData = new FilteredList<>(masterMedData, p -> true);	
        medSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(med -> {               
                if (newValue == null || newValue.isEmpty()) {
                    return true;}
                String lowerCaseFilter = newValue.toLowerCase();
                if (( med.getNombre().getValue()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }else 
            return false; // Does not match.
        });
    });
            SortedList<Medicina> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind((ObservableValue<? extends Comparator<? super Medicina>>) medTable.comparatorProperty());		
            medTable.setItems(sortedData);

	}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        medTable.setItems(Main.getMedData());
        nomMed.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        
        buscarMedicina();
        
        showMedDetails(null);
        
        medTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> showMedDetails(newValue));
        
    }    
    
}
