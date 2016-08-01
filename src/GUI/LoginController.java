/*
  * To change this license header, choose License Headers in Project Properties.
  * To change this template file, choose Tools | Templates
  * and open the template in the editor.
  */
 package GUI;
 
import java.io.IOException;
 import java.net.URL;
import java.sql.SQLException;
 import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
 import javafx.fxml.FXML;
 import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import kaixo.Main;
import static utils.CryptMD5.cryptWithMD5;
import static utils.JavaSQL.loginSession;
import static utils.JavaSQL.userLevel;
import utils.RegexMatcher;
 
 /**
  *
  * @author made
  */
 public class LoginController extends Main implements Initializable {
     
     @FXML
     private TextField user;
     @FXML
     private PasswordField pass;
     
    
     @FXML
     private void handleEnter() throws SQLException{
         String userm = user.getText();
         String passmd5 = pass.getText();
         
         if (userm.equals(null) || passmd5.equals(null)){
             passmd5 = "";
         }else{
             passmd5 = cryptWithMD5(pass.getText().concat(userm));
         }
         
        if(notEmpty(userm, passmd5)){
            if (loginSession(actualDB, userm, passmd5)){
                int level = userLevel(actualDB,userm);
                 try {
                     showKaixoMain();
                 } catch (IOException ex) {
                     Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                 }

            }else{
                user.clear();
                pass.clear();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Kaixo Error #1");
                alert.setContentText("La información que ingresaste no es válida. "
                        + "Por favor revisa el usuario y contraseña que escribiste");

                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Kaixo Error #1");
            alert.setContentText("La información que ingresaste no es válida. "
                    + "Por favor revisa el usuario y contraseña que escribiste");

            alert.showAndWait();
        }
        
     }

    private boolean notEmpty(String user, String pass){  
        System.out.println(RegexMatcher.testUser(pass));
        System.out.println(RegexMatcher.testPassword(user));
         return (RegexMatcher.testPassword(pass) && RegexMatcher.testUser(user));
    }
     @FXML
     private void handleExit(){
         primaryStage.close();
         System.exit(0);
     }
     
     @Override
     public void initialize(URL url, ResourceBundle rb) {
         // TODO
     }    
     
 }