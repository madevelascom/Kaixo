/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaixo;

import GUI.ConsultaController;
import GUI.DistribuidorController;
import utils.JavaSQL;
import GUI.LoginController;
import GUI.MedicinaController;
import GUI.PacienteController;
import GUI.RecetaController;
import GUI.UsuarioController;
import GUI.ValoracionController;
import elements.Consulta;
import elements.Distribuidor;
import elements.Medicina;
import elements.Paciente;
import elements.Receta;
import elements.Usuario;
import elements.Valoracion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.NamingException;
import static kaixo.Main.medFrecuencies;

/**
 *
 * @author made
 */
public class Main extends Application {
    
    public static Stage primaryStage;
    private BorderPane rootLayout;
    private BorderPane KaixoLogin;
    private BorderPane KaixoInterface;
    private Scene sceneLogin;
    private Scene sceneMain;
    
    public static Connection actualDB;
    public static JavaSQL instanceDB = new JavaSQL();
    
    public static ObservableList<String> medDataNames = FXCollections.observableArrayList();
    public static ObservableList<Medicina> medData = FXCollections.observableArrayList();
    public static ObservableList<Distribuidor> distData = FXCollections.observableArrayList();
    public static ObservableList<Consulta> conHoyData = FXCollections.observableArrayList();
    
    public static ObservableList<Receta> medFrecuencies = FXCollections.observableArrayList();
    
    public String usuario;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kaixo");  
        
        Image applicationIcon = new Image(getClass().getResourceAsStream("/resources/icon.png"));
        this.primaryStage.getIcons().add(applicationIcon);
        
        initRootLayout();
        showKaixoLogin(); 
    }
    
    public void initRootLayout() throws IOException {
        String css = LoginController.class.getResource("style.css").toExternalForm();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();    

        // Show the scene containing the root layout.
        sceneLogin = new Scene(rootLayout);           
        sceneLogin.getStylesheets().clear();
        sceneLogin.getStylesheets().add(css);
        primaryStage.setScene(sceneLogin);         
        primaryStage.show();        
    }
    
    public void showKaixoLogin() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource("Login.fxml"));
        KaixoLogin = (BorderPane) loader.load();

        rootLayout.setCenter(KaixoLogin); 
    }
    
    public void showKaixoMain( String usuariod) throws IOException{
        
        this.usuario = usuariod;
        String css = LoginController.class.getResource("style.css").toExternalForm();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource("KaixoMain.fxml"));
        KaixoInterface = (BorderPane) loader.load();
        
        sceneMain = new Scene(KaixoInterface);           
        sceneMain.getStylesheets().clear();
        sceneMain.getStylesheets().add(css);
        primaryStage.setScene(sceneMain);         
        primaryStage.show();  

    }
    
    
    public static boolean showConsultaDialog(Consulta cons) throws SQLException{
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Consulta.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
	    dialogStage.setTitle("Crear/Editar Consulta");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    dialogStage.getIcons().add(new Image("file:resources/icon.png"));
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);
            
            ConsultaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setConsulta(cons);

            dialogStage.showAndWait();

	    return controller.isOkClicked();
        }catch (IOException e){
            return false;
        }
        
    }
    public static boolean showMedicinaDialog(Medicina med, List<String> resultDist){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Medicina.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
	    dialogStage.setTitle("Crear/Editar Medicina");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    dialogStage.getIcons().add(new Image("file:../resources/icon.png"));
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);
            
            MedicinaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMedicina(med, resultDist);

            dialogStage.showAndWait();

	    return controller.isOkClicked();
        }catch (IOException e){
            return false;
        }
    }
    
    public static boolean showDistDialog(Distribuidor dist){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Distribuidor.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
	    dialogStage.setTitle("Distribuidor");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    dialogStage.getIcons().add(new Image("file:resources/icon.png"));
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);
            
            
            DistribuidorController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setDistribuidor(dist);
            
            dialogStage.showAndWait();

	    return controller.isOkClicked();
        }catch (IOException e){
            return false;
        }
    }
    
    public static boolean showValDialog( Valoracion vat){
        try{
             // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Valoracion.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Valoracion");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:resources/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            ValoracionController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setValoracion(vat);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){
            return false;
        }
        
    }
    
    public static boolean showRecetaDialog( Receta rec, Consulta cons, ObservableList<Receta> recetas){
        try{
            
            
             // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Receta.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Receta");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:resources/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            RecetaController controller;
            
            controller = loader.getController();
            controller.setMedicinasFrecuencias(recetas);
            controller.setDialogStage(dialogStage);
            controller.setReceta(rec);
            controller.setConsulta(cons);
            
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){
            return false;
        }
        
    }
    
    public static boolean showPaxNewDialog(Paciente pat){
        try{
             // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Paciente.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Paciente");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:resources/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            PacienteController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUpdate(false);
            controller.setPaciente(pat);
            controller.disableCI();
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){
            return false;
        }
    }
    
    public static boolean showPaxNewConsulta(Consulta con) throws SQLException{
        try{
             // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Consulta.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Consulta");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:resources/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            ConsultaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setConsulta(con);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){
            return false;
        }
    }
    
    public static boolean showPaxEditDialog(Paciente pat){
        try{
             // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Paciente.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Paciente");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:resources/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            PacienteController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUpdate(true);
            controller.setPaciente(pat);
            controller.disableCI();
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){
            return false;
        }
    }
    
    public static boolean showUserNewDialog(Usuario us, boolean bandera){
        try{
             // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Usuario.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Usuario");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:resources/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            UsuarioController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.disableUsername(bandera);
            controller.setUsuario(us);
            //controller.disableCI();
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){
            return false;
        }
    }
    
    
    public static ObservableList<Medicina> getMedData() {
        return medData;
    }
    
    public static ObservableList<Consulta> getTodayConData(){
        return conHoyData;
    }
    
    public static ObservableList<Distribuidor> getDistData(){
        return distData;
    }
    
    public static Connection getConn(){
        return actualDB;
    }

    public static ObservableList<String> getMedDataNames() {
        return medDataNames;
    }

    public static ObservableList<Receta> getMedFrecuencies() {
        return medFrecuencies;
    }

    public static void setMedFrecuencies(ObservableList<Receta> medFrecuencies) {
        Main.medFrecuencies = medFrecuencies;
    }
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args)throws NamingException, SQLException {
        
        actualDB = instanceDB.openConnection();
        if(actualDB != null){
            System.out.println("Successful connection"); 
            medData = JavaSQL.loadMedicinas(actualDB);
            distData = JavaSQL.loadDistribuidor(actualDB);
            conHoyData = JavaSQL.loadConsultasHoy(actualDB);
            medDataNames = JavaSQL.loadMedicinasNameCon(actualDB);
            launch(args);
        }else{
            Alert no_conn = new Alert(AlertType.ERROR);
            no_conn.setTitle("Error");
            no_conn.setHeaderText("Hubo en error al conectar la base de datos");
            no_conn.setContentText("Verifica que esté la base de datos activa. "
                    + "En caso contrario, contacta con uno de los desarrolladores.");
        }
        
    }
    
}
