/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaixo;

import GUI.LoginController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.naming.NamingException;

/**
 *
 * @author Administrator
 */
public class Main extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    private BorderPane KaixoInterface;
    private Scene scene;
    
    public static Connection actualDB;
    public static JavaSQL instanceDB = new JavaSQL();
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kaixo");  
        
        /* TODO 
        Image applicationIcon = new Image(getClass().getResourceAsStream("rubik_s_cube.png"));
        this.primaryStage.getIcons().add(applicationIcon);*/
        
        initRootLayout();
        showKaixoInterface(); 
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
    
    public void showKaixoInterface() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource("Login.fxml"));
        KaixoInterface = (BorderPane) loader.load();

        rootLayout.setCenter(KaixoInterface); 
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
            launch(args);
        }
        
    }
    
}
