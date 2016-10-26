package GUI;

import elements.Consulta;
import elements.Valoracion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author INTEL
 */
public class ValoracionDiagnosticoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField presion;
    @FXML
    private TextField glucosa;
    @FXML
    private TextField peso;
    
    @FXML
    private TextArea diagnostico;
    
    private Stage dialogStage;
    private Valoracion val;
    private Consulta cons;
    private String diag;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setValoracion(Valoracion val){
        this.val = val;  
    }
    
    public void setDiagnostico(String diag){
        this.diag = diag;  
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }

    public void setConsulta(Consulta cons) {
        this.cons = cons;
    }
    
    @FXML
    public void handleAceptar(){
        
            okClicked = true;
            dialogStage.close();
    }
    
    public void setTextos(){
        /*
            presion.setDisable(true);
            glucosa.setDisable(true);
            peso.setDisable(true);
            diagnostico.setDisable(true);
          */  
            System.out.println(val.getPresion().getValue());
                    if (val != null && val !=null){
            presion.setText(val.getPresion().getValue());
            glucosa.setText("" + val.getGlucosa().getValue());
            peso.setText("" + val.getPeso().getValue());
            diagnostico.setText(diag);

            }
        
            else if (val != null){

            presion.setText(val.getPresion().getValue());
            glucosa.setText("" + val.getGlucosa().getValue());
            peso.setText("" + val.getPeso().getValue());
            diagnostico.setText("");
            

            }else{
            
            presion.setText("123");
            glucosa.setText("123");
            peso.setText("123");
            diagnostico.setText("123");
            
        }
        
            
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }    
    
}
