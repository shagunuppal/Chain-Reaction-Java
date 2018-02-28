package grid;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeDebug.getClass;

/**
 * FXML controller for settingspage.fxml
 * @author niket
 */

public class load_settings implements Initializable {
    
    @FXML
    private StackPane root;
    @FXML
    private Button playersetting;
    @FXML
    private Button sound;
    @FXML
    private Button Back;
    
    @FXML
    private void handleButtonClick(ActionEvent event) 
    {
        makeFadeOut();
        
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Creates a transition for the window to disappear
     */
    private void makeFadeOut() 
    {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.seconds(1));
        fade.setNode(root);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished((ActionEvent event)->{
            loadSetting();
        });
        fade.play();
    }
    /**
     * to load the page for settingspage.fxml
     */
    private void loadSetting() {
        try{
           Parent setting = (StackPane)FXMLLoader.load(getClass().getResource("settingspage.fxml"));
           Scene scene =new Scene(setting);
           Stage stage = (Stage) root.getScene().getWindow();
           stage.setScene(scene);
        }
        catch(Exception e)
        {
            
        }
      
    }
    
}
