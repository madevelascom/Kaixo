package GUI;

import elements.Medicina;
import elements.Valoracion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author INTEL
 */
public class ValoracionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private Stage dialogStage;
    private Valoracion val;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setValoracion(Valoracion val){
        this.val = val;
        
     
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
