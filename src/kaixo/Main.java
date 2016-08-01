/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaixo;

import utils.JavaSQL;
import GUI.LoginController;
import GUI.MedicinaController;
import elements.Distribuidor;
import elements.Medicina;
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

/**
 *
 * @author made
 */
public class Main extends Application {
    
    public static Stage primaryStage;
    private BorderPane rootLayout;
    private BorderPane KaixoLogin;
    private BorderPane KaixoInterface;
    private Scene scene;
    
    public static Connection actualDB;
    public static JavaSQL instanceDB = new JavaSQL();
    
    public static ObservableList<Medicina> medData = FXCollections.observableArrayList();
    public static ObservableList<Distribuidor> distData = FXCollections.observableArrayList();
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kaixo Login");  
        
        Image applicationIcon = new Image(getClass().getResourceAsStream("/resources/icon.png"));
        this.primaryStage.getIcons().add(applicationIcon);
        
        initRootLayout();
        showKaixoLogin(); 
    }
    
    public void initRootLayout() throws IOException {
        // Load root layout from fxml file.
        String css = LoginController.class.getResource("style.css").toExternalForm();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();    

        // Show the scene containing the root layout.
        scene = new Scene(rootLayout);           
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);         
        primaryStage.show();        
    }
    
    public void showKaixoLogin() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource("Login.fxml"));
        KaixoLogin = (BorderPane) loader.load();

        rootLayout.setCenter(KaixoLogin); 
    }
    
    public void showKaixoMain() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource("KaixoMain.fxml"));
        KaixoInterface = (BorderPane) loader.load();

        rootLayout.setCenter(KaixoInterface);
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

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

	    return controller.isOkClicked();
        }catch (IOException e){
            return false;
        }
    }
    
    public static ObservableList<Medicina> getMedData() {
        return medData;
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
