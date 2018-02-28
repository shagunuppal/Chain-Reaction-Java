/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grid;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author niket
 */
public class MainpageController implements Initializable {

    @FXML
    private StackPane root;
    @FXML
    private Button playgame;
    @FXML
    private Button resumegame;
    @FXML
    private Button settings;
    
    public Game game;
    public Stage stage;
    static Player[] players=new Player[8];
    
    
    public void set_game(Stage st)
    {
        stage = st;
    }
    /**
     * Initializes the controller class.
     */
     @FXML
    private void handleButtonClick(ActionEvent event) throws IOException, ClassNotFoundException 
    {
        System.out.println("hi");
        game.start_game();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    
    
}
