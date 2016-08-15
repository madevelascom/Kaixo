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
import elements.Consulta;
import elements.Distribuidor;
import elements.Medicina;
import elements.Paciente;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
import static utils.CryptMD5.cryptWithMD5;

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
    
    public static ObservableList<Medicina> medData = FXCollections.observableArrayList();
    public static ObservableList<Distribuidor> distData = FXCollections.observableArrayList();
    public static ObservableList<Consulta> conHoyData = FXCollections.observableArrayList();
    
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
    
    public void showKaixoMain() throws IOException{
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
    
    public static boolean showMedicinaDialog(Medicina med){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Medicina.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
	    dialogStage.setTitle("Crear/Editar Medicina");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    dialogStage.getIcons().add(new Image("file:resources/icon.png"));
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);
            
            MedicinaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMedicina(med);

            dialogStage.showAndWait();

	    return controller.isOkClicked();
        }catch (IOException e){
            return false;
        }
    }
    
    public static boolean showDistribuidorDialog(Distribuidor dist){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("Distribuidor.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
	    dialogStage.setTitle("Crear/Editar Distribuidor");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    dialogStage.getIcons().add(new Image("file:resources/icon.png"));
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);
            
            
            DistribuidorController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setDistribuidor(dist);
            
            dialogStage.showAndWait();

	    return controller.isOkclicked();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("2");
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
    
    public static boolean showPaxNewConsulta(Consulta con){
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
    
    public static boolean showPaxeEditDialog(Paciente pat){
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
    public static ObservableList<Medicina> getMedData() {
        return medData;
    }
    
    public static ObservableList<Consulta> getTodayConData(){
        return conHoyData;
    }
    
    public static ObservableList<Distribuidor> getDistData(){
        return distData;
    }
    /**
     * @param args the command line arguments
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args)throws NamingException, SQLException {
        
        actualDB = instanceDB.openConnection();
        /*/System.out.println(cryptWithMD5("123456mon"+"test"));
        System.out.println(cryptWithMD5("vangelis7enposi"+"test2"));*/
        if(actualDB != null){
            System.out.println("Successful connection"); 
            medData = JavaSQL.loadMedicinas(actualDB);
            distData = JavaSQL.loadDistribuidor(actualDB);
            conHoyData = JavaSQL.loadConsultasHoy(actualDB);
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
