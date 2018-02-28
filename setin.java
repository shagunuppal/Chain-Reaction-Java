/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * FXML Controller class for loading settingspage.fxml
 *
 * @author niket
 */
public class setin implements Initializable {

    @FXML
    private StackPane root1;
    @FXML
    private Button playersetting;
    @FXML
    private Button sound;
    @FXML
    private Button Back;

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
     * to load the page for settingspage.fxml
     */
    public void loadset()
    {
        Parent setting = null;
        try{

            setting= (StackPane)FXMLLoader.load(getClass().getResource("settingspage.fxml"));
           
           
        }
        catch(IOException e)
        {
        }
        Scene scene =new Scene(setting);
        game.stage12.setScene(scene);
        game.stage12.show();
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
     * @param a 
     */
    private void makeFadeOut(int a) {
         FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(700));
        fade.setNode(root1);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished((ActionEvent event)->{
            if(a==1)
                game.disp();
            else
                {
                    play_seti ob = new play_seti();
                    ob.loadSet();
                }
            
        });
        fade.play();
        }
    

    @FXML
    private void player_setting(ActionEvent event) 
    {
        makeFadeOut(2);
    }

    @FXML
    private void back(ActionEvent event) 
    {
        makeFadeOut(1);
    }
    
}
