
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
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.util.Duration;

/**
 * FXML Controller class for preferences.fxml
 *
 * @author niket
 */
public class PreferencesController implements Initializable {

    @FXML
    private StackPane root1;
    @FXML
    private Button red;
    @FXML
    private Button yellow;
    @FXML
    private Button lblue;
    @FXML
    private Button orange;
    @FXML
    private Button blue;
    @FXML
    private Button green;
    @FXML
    private Button white;
    @FXML
    private Button magenta;
    @FXML
    private Button cont;

    
    /**
     * loads the page for preferences.fxml
     */
      public void loadSet() {
        
        Parent setting = null;
        try{

            setting= (StackPane)FXMLLoader.load(getClass().getResource("preferences.fxml"));
           
           
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
     * Creates a transition for the window to appear
     */
    private void makeFadeOut() {
         FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(700));
        fade.setNode(root1);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished((ActionEvent event)->
        {
            game.disp();
        });
        fade.play();
        }

    @FXML
    private void setcol(ActionEvent event) 
    {
        int colo = 0;
        Button x = (Button) event.getSource();
        String s = x.getId();
        if(s.equals("blue"))
        {
            colo=0;
        }
        else if(s.equals("red"))
        {
            colo=1;
        }
        else if(s.equals("magenta"))
        {
            colo=2;
        }
        else if(s.equals("yellow"))
        {
            colo=3;
        }
        else if(s.equals("orange"))
        {
            colo=4;
        }
        else if(s.equals("white"))
        {
            colo=5;
        }
        else if(s.equals("green"))
        {
            colo=6;
        }
        else if(s.equals("lblue"))
        {
            colo=7;
        }
        
        for(int i=0;i<8;i++)
        {
            Color temp = game.players[game.player_seti].color;
            PhongMaterial tem = game.players[game.player_seti].ph;
            
            if(game.players[i].color.equals(game.color[colo]))
            {
                game.players[i].color = temp;
                game.players[game.player_seti].color = game.color[colo];
                
                game.players[game.player_seti].ph = game.players[i].ph;
                game.players[i].ph = tem;
                
                game.player_seti=0;
                break;
            }
        }
    }

    @FXML
    private void cont(ActionEvent event) 
    {
        makeFadeOut();
    }
    
    
   
    
    
}
