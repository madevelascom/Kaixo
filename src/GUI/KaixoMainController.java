    package GUI;

import elements.Consulta;
import elements.Distribuidor;
import elements.Medicina;
import elements.Paciente;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import kaixo.Main;
import static kaixo.Main.actualDB;
import utils.JavaSQL;
import static utils.JavaSQL.deleteDist;
import static utils.JavaSQL.deleteMed;
import static utils.JavaSQL.distExists;
import static utils.JavaSQL.errorMsg;
import static utils.JavaSQL.insertNewPax;
import static utils.RegexMatcher.testPaxCISearch;
import static utils.RegexMatcher.testPaxNomSearch;
import static utils.JavaSQL.searchPaxCI;
import static utils.JavaSQL.searchPaxNom;
import static utils.JavaSQL.updatePax;
import static utils.JavaSQL.existsPax;
import static utils.JavaSQL.insertConsulta;
import static utils.JavaSQL.insertNewDist;
import static utils.JavaSQL.insertNewMed;
import static utils.JavaSQL.medExists;
import static utils.JavaSQL.selecPaxNameConcat;
import static utils.JavaSQL.updateDist;
import static utils.JavaSQL.updateMed;

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
    
    /*Pacientes*/
    @FXML
    private Label paxCI;
    @FXML
    private Label paxNombre;
    @FXML
    private Label paxApellido;
    @FXML
    private Label paxNacimiento;
    @FXML
    private Label paxSangre;
    @FXML
    private Label paxCelular;
    @FXML
    private Label paxCasa;
    @FXML
    private Label paxDi;
    @FXML
    private Label paxEmail;
    @FXML
    private TextField paxCISearch;
    @FXML
    private TextField paxNomSearch;

    //Consultas de hoy
    @FXML
    private TableView<Consulta> distConHoy;
    @FXML
    private TableColumn<Consulta, String> horaConHoy;
    @FXML
    private TableColumn<Consulta, String> paxConHoy;
    @FXML
    private TableColumn<Consulta, String> estConHoy;
    
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
    
    /*Distribuidores*/
    private void showDistDetails (Distribuidor dist){
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
    private void handleNewDestribuidor() throws SQLException{
        Distribuidor dist = new Distribuidor();
        boolean onClicked = Main.showDistDialog(dist);
        if (onClicked){
            Main.getDistData().add(dist);
            insertNewDist(actualDB, dist);
        }
    }
    
            
    public void buscarDistribuidor(){
        ObservableList<Distribuidor> masterDistData = Main.getDistData();
        nomDist.setCellValueFactory ( cellData -> cellData.getValue().getNombre()); 	
        FilteredList<Distribuidor> filteredData = new FilteredList<>(masterDistData, p -> true);	
        distSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(dist -> {               
                if (newValue == null || newValue.isEmpty()) {
                    return true;}
                String lowerCaseFilter = newValue.toLowerCase();
                if (( dist.getNombre().getValue()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }else 
            return false; // Does not match.
            });
        });
            SortedList<Distribuidor> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind((ObservableValue<? extends Comparator<? super Distribuidor>>) distTable.comparatorProperty());		
            distTable.setItems(sortedData);

    }
    
    
    @FXML
    private void handleEditDistribuidor() throws SQLException{
        int selectedIndex = distTable.getSelectionModel().getSelectedIndex();	
        if (selectedIndex >= 0) {			
            Distribuidor temp = new Distribuidor(distNom.getText(), 
                    distDir.getText(), distTel.getText() );
            Distribuidor prev = new Distribuidor(distNom.getText(), 
                    distDir.getText(), distTel.getText() );
            if (distExists(actualDB, temp)) {
                boolean okClicked = Main.showDistDialog(temp);
                if (okClicked) {
                    updateDist(actualDB, temp, prev);
                                       
                    distData = JavaSQL.loadDistribuidor(actualDB);
                    distConHoy.setItems(Main.getTodayConData());
                    distTable.setItems(Main.getDistData());
                    showDistDetails(temp);
                }
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Kaixo Error #4");
                alert.setContentText(errorMsg(actualDB, 4));

                alert.showAndWait();
            }
        }    
    }
    
    @FXML
    private void handleDelDist() throws SQLException{
        int selectedIndex = distTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setTitle("Confirmación de borrado");
            alert.setHeaderText("Vas a borrar el distribuidor que escogiste");
            alert.setContentText("¿Estás seguro?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                Distribuidor dist =  distTable.getItems().get(selectedIndex); 
                
                deleteDist(actualDB, dist);
                distData = JavaSQL.loadDistribuidor(actualDB);
                distConHoy.setItems(Main.getTodayConData());
                distTable.setItems(Main.getDistData());
            }  
        }
   }

    /*Medicinas*/
    
    @FXML
    private void handleNewMedicina() throws SQLException{
       Medicina temp = new Medicina();
       boolean onClicked = Main.showMedicinaDialog(temp);
       if (onClicked){    
           Main.getMedData().add(temp);
           insertNewMed(actualDB, temp);
           
       }
    }
    
    @FXML
    private void handleEditMedicina() throws SQLException{
        Medicina temp = new Medicina(medNom.getText(), medCon.getText(), medPres.getText());
        Medicina prev = new Medicina(medNom.getText(), medCon.getText(), medPres.getText());
        
        if (medExists(actualDB, temp)){
            boolean okClicked = Main.showMedicinaDialog(temp);
            if (okClicked) {
                updateMed(actualDB, temp, prev);
                
                medData = JavaSQL.loadMedicinas(actualDB);
                medTable.setItems(Main.getMedData());
                nomMed.setCellValueFactory(cellData -> cellData.getValue().getNombre());
                showMedDetails(temp);            
            }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Kaixo Error #4");
            alert.setContentText(errorMsg(actualDB, 4));

            alert.showAndWait();
        }
           
    }
    
    @FXML
    private void handleDelMed() throws SQLException{
        int selectedIndex = medTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setTitle("Confirmación de borrado");
            alert.setHeaderText("Vas a borrar la medicina que escogiste");
            alert.setContentText("¿Estás seguro?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){ 
                Medicina med =  medTable.getItems().get(selectedIndex);                   
                deleteMed(actualDB, med);
                medData = JavaSQL.loadMedicinas(actualDB);
                medTable.setItems(Main.getMedData());
                nomMed.setCellValueFactory(cellData -> cellData.getValue().getNombre());

            }  
        }
   }
    
    public void buscarMedicina(){
        nomMed.setCellValueFactory ( cellData -> cellData.getValue().getNombre()); 	
        FilteredList<Medicina> filteredData = new FilteredList<>(masterMedData, p -> true);	
        medSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(med -> {               
                if (newValue == null || newValue.isEmpty()) {
                    return true;}
                String lowerCaseFilter = newValue.toLowerCase();
                return ( med.getNombre().getValue()).toLowerCase().contains(lowerCaseFilter); // Does not match.
            });
        });
            SortedList<Medicina> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind((ObservableValue<? extends Comparator<? super Medicina>>) medTable.comparatorProperty());		
            medTable.setItems(sortedData);

    }

    /*PACIENTES*/
    private void showPaxDetails (Paciente pax){
        if (pax != null){
           paxCI.setText(pax.getCI().getValue());
           paxNombre.setText(pax.getNombres().getValue());
           paxApellido.setText(pax.getApellidos().getValue());
           paxNacimiento.setText(pax.getNacimiento().getValue());
           paxSangre.setText(pax.getSangre().getValue());
           paxCelular.setText(pax.getCelular().getValue());
           paxCasa.setText(pax.getCasa().getValue());
           paxDi.setText(pax.getDireccion().getValue());
           paxEmail.setText(pax.getEmail().getValue());
        }else{
           paxCI.setText("");
           paxNombre.setText("");
           paxApellido.setText("");
           paxNacimiento.setText("");
           paxSangre.setText("");
           paxCelular.setText("");
           paxCasa.setText("");
           paxDi.setText("");
           paxEmail.setText("");
        }
    }
    
    @FXML 
    private void handleSearchCI() throws SQLException{
        String searchCI = paxCISearch.getText();
        if (!searchCI.equals("")){
            if(testPaxCISearch(searchCI)){
                Paciente pac = searchPaxCI(actualDB, searchCI);
                showPaxDetails(pac);
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Kaixo Error #5");
                alert.setContentText("Ingresa datos válidos");
                alert.showAndWait();
            }
        }else{
            paxCISearch.clear();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Kaixo Error #4");
            alert.setContentText("Campo vacío");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSearchNom() throws SQLException{
        String searchNom = paxNomSearch.getText();
        if(!searchNom.equals("")){
            if (testPaxNomSearch(searchNom)){
                Paciente pac = searchPaxNom(actualDB, searchNom);
                showPaxDetails(pac);
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Kaixo Error #5");
                alert.setContentText("Ingresa datos válidos");
                alert.showAndWait();
            }
        }else{
            paxNomSearch.clear();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Kaixo Error #4");
            alert.setContentText("Campo vacío");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewPax() throws SQLException{
        Paciente temp = new Paciente();
        boolean okClicked = Main.showPaxNewDialog(temp);
        if (okClicked){
            insertNewPax(actualDB, temp);
        }
    }
    
    @FXML 
    private void handleEditPax() throws SQLException{
        if (existsPax(actualDB, paxCI.getText())){
            Paciente temp = searchPaxCI(actualDB, paxCI.getText());
            boolean okClicked = Main.showPaxEditDialog(temp);
            if (okClicked){
                updatePax(actualDB, temp);
            }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Kaixo Error #4");
            alert.setContentText(errorMsg(actualDB, 4));

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewConsult() throws SQLException{
        if (existsPax(actualDB, paxCI.getText())){
            Consulta temp = new Consulta(paxCI.getText());
            boolean okClicked = Main.showPaxNewConsulta(temp);
            if (okClicked){
                insertConsulta(actualDB, temp);
            }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Kaixo Error #4");
            alert.setContentText(errorMsg(actualDB, 4));

            alert.showAndWait();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        medTable.setItems(Main.getMedData());
        nomMed.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        distConHoy.setItems(Main.getTodayConData());
        horaConHoy.setCellValueFactory(cellData -> cellData.getValue().getFecha());
        horaConHoy.setCellFactory(column -> {
            return new TableCell<Consulta, String>(){
                @Override
                protected void updateItem(String date, boolean empty){
                    super.updateItem(date, empty);
                    if(date == null || empty){
                        setText(null);
                    }else{
                        String[] parts = date.split(" ");  
                        setText(parts[1]);
                    }
                }
            };
        });
        paxConHoy.setCellValueFactory(cellData-> cellData.getValue().getPaciente());      
        paxConHoy.setCellFactory(column -> {
            return new TableCell<Consulta, String>(){
                @Override
                protected void updateItem(String CI, boolean empty){
                    super.updateItem(CI, empty);
                    
                    if(CI == null || empty){
                        setText(null);
                    }else{
                        try {
                            setText(selecPaxNameConcat(actualDB, CI));
                        } catch (SQLException ex) {
                            Logger.getLogger(KaixoMainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
        });
        estConHoy.setCellValueFactory(cellData -> cellData.getValue().getEstado());
        
        distTable.setItems(Main.getDistData());
        nomDist.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        


        buscarMedicina();
        buscarDistribuidor();

        showMedDetails(null);
        showPaxDetails(null);
        showDistDetails(null);
        

        medTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showMedDetails(newValue));
        
        distTable.getSelectionModel().selectedItemProperty().addListener((observable , oldValue, newValue) -> {
            showDistDetails(newValue);
        });

    }    
    
}
