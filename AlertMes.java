package grid;

import static grid.general_grid3D.game;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.util.Duration;

/**
 * FXML controller class for win.fxml
 * @author niket
 */

public class AlertMes implements Initializable
{
    static int tem=0;    
     static boolean ans;
     static Stage win;
     static String s;
     
     @FXML
     private Label lab;
     
     /**
      *  loads the page for win.fxml
      * @throws IOException  
      */
     
     public void win_display() throws IOException
     {
         win = new Stage();
            win.initModality(Modality.APPLICATION_MODAL);    
        
            
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("win.fxml"));;
            //lab.setText("player");
            
        //root.setTranslateX(100);
        Scene scene = new Scene(root);
       // stage.setScene(scene);
        
       
        /*win.setOnHidden(evt -> stage.show());
        win.close();
          */
        win.setScene(scene);
        win.show();  
     }
     /**
      * To create a new grid and window to start a new game
      * @param event Mouse click
      * @throws ClassNotFoundException
      * @throws IOException 
      */
     public void playAgain(ActionEvent event) throws ClassNotFoundException, IOException
     {
        
    	
        win.setOnHidden(evt -> {
           /* general_grid3D.game.reset();
            general_grid3D.game.start_game();
            */
           makeFadeOut(1);
                });
        win.close();
        
     }
     /**
      *  To return to the main page of the game to see the initial options
      * @param event  Mouse click
      */
     public void mainMenu(ActionEvent event)
     {
        win.setOnHidden(evt -> {
            
            makeFadeOut(2);
                });
        win.close();
     }
     
     
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
    }
     /**
      * Creates a transition for the window to disappear
      * @param a  to decide whether to play again or return to the main menu
      */
       private void makeFadeOut(int a) {
         FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(700));
        fade.setNode(game.pane);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished((ActionEvent event)->{
            
            //game.stage12.close();
            if(a==1)
            {
                game.reset();
                game.start_game();
            }
            else
            {
                game = new Game();
            }
            win.close();
        });
        fade.play();
        }
      
    
}