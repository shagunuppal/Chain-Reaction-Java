
package grid;

import static grid.general_grid3D.game;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class for PlayerSetting.fxml
 *
 * @author niket
 */
public class play_seti implements Initializable {

    @FXML
    private Button p1;
    @FXML
    private Button p2;
    @FXML
    private Button p3;
    @FXML
    private Button p4;
    @FXML
    private Button p5;
    @FXML
    private Button p6;
    @FXML
    private Button p7;
    @FXML
    private Button p8;
    @FXML
    private StackPane root1;

    /**
     *  loads the page for PlayerSetting.fxml 
     */
      public void loadSet() {
        
        Parent setting = null;
        try{

            setting= (StackPane)FXMLLoader.load(getClass().getResource("PlayerSetting.fxml"));
           
           
        }
        catch(IOException e)
        {
        }
        Scene scene =new Scene(setting);
        game.stage12.setScene(scene);
        game.stage12.show();
    } 
      
      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        root1.setOpacity(0);
        makeFadeInTransition();
    }    
    
    /**
     * Creates a transition for the window to appear
     */
     private void makeFadeInTransition() {
        FadeTransition fd = new FadeTransition();
        fd.setDuration(Duration.millis(700));
        fd.setNode(root1);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.play();
    }
     /**
     * Creates a transition for the window to disappear
     */
     private void makeFadeOut() {
         FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(700));
        fade.setNode(root1);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished((ActionEvent event)->{
            PreferencesController ob = new PreferencesController();
            ob.loadSet();
            
        });
        fade.play();
        }
    

    @FXML
    private void seti(ActionEvent event) 
    {
        Button x = (Button)event.getSource();
        String s = x.getId();
        if(s.equals("p1"))
        {
            game.player_seti=0;
        }
        else if(s.equals("p2"))
        {
            game.player_seti=1;
        }
        else if(s.equals("p3"))
        {
            game.player_seti=2;
        }
        else if(s.equals("p4"))
        {
            game.player_seti=3;
        }
        else if(s.equals("p5"))
        {
            game.player_seti=4;
        }
        else if(s.equals("p6"))
        {
            game.player_seti=5;
        }
        else if(s.equals("p7"))
        {
            game.player_seti=6;
        }
        else if(s.equals("p8"))
        {
            game.player_seti=7;
        }
        
        makeFadeOut();
        
    }
    
}
