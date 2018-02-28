package grid;

import static grid.general_grid3D.game;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeDebug.getClass;

/**
 * This is used to as a controller for PlayGame.fxml
 * @author niket
 */
public class transsetting implements Initializable {
    
    @FXML
    private StackPane root1;
    @FXML
    private MenuItem but2;
    @FXML
    private MenuItem but3;
    @FXML
    private MenuItem but4;
    @FXML
    private MenuItem but5;
    @FXML
    private MenuItem but6;
    @FXML
    private MenuItem but7;
    @FXML
    private MenuItem but8;
    @FXML
    private MenuItem gri96;
    @FXML
    private MenuItem gri1510;
    @FXML
    private Button conti;
    @FXML
    private Button conti1;
    /**
     * Loads the fxml page for the main menu
     */
    public void loadSet() 
    {
        
        Parent setting = null;
        try{

            setting= (StackPane)FXMLLoader.load(getClass().getResource("PlayGame.fxml"));
           
           
        }
        catch(IOException e)
        {
        }
        Scene scene =new Scene(setting);
        game.stage12.setScene(scene);
        game.stage12.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
     * @param a decides whether continue button is calling or back button is. 
     */
    private void makeFadeOut(int a) {
         FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(700));
        fade.setNode(root1);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished((ActionEvent event)->{
            
            //game.stage12.close();
            if(a==1)
                game.start_game();
            else
                game.disp();
            
        });
        fade.play();
        }
    
    @FXML
    private void set_playercount(ActionEvent event) {
        MenuItem x = (MenuItem) event.getSource();
        String s = x.getId();
        if(s.equals("but2"))
        {
            game.no_of_players=2;
        }
        else if(s.equals("but3"))
        {
            game.no_of_players=3;
        }
        else if(s.equals("but4"))
        {
            game.no_of_players=4;
        }
        else if(s.equals("but5"))
        {
            game.no_of_players=5;
        }
        else if(s.equals("but6"))
        {
            game.no_of_players=6;
        }
        else if(s.equals("but7"))
        {
            game.no_of_players=7;
        }
        else if(s.equals("but8"))
        {
            game.no_of_players=8;
        }
        
    }

    @FXML
    private void set_gridsize(ActionEvent event) 
    {
        MenuItem x = (MenuItem) event.getSource();
        String s = x.getId();
        if(s.equals("gri96"))
        {
            game.rows=10;
            game.columns=7;
            game.x=9;
            game.y=6;
        }
        else if(s.equals("gri1510"))
        {
            game.rows=16;
            game.columns=11;
            game.x=15;
            game.y=10;
        }
        
        
    }

    @FXML
    private void cont(ActionEvent event) 
    {
        makeFadeOut(1);
    }

    @FXML
    private void back(ActionEvent event) 
    {
        makeFadeOut(2);
    }
    
    
}
