import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/*from  ww  w.j  a  v a  2  s. c o m*/
public class Main extends Application {
  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(final Stage primaryStage) {
    primaryStage.setTitle("title");
    Group root = new Group();
    Scene scene = new Scene(root, 400, 300, Color.WHITE);

    MenuBar menuBar = new MenuBar();
    menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

    Menu menu = new Menu("File");
    menu.setOnHidden(new EventHandler<Event>(){
      @Override
      public void handle(Event arg0) {
      }
    });
    MenuItem newItem = new MenuItem("New", null);
    newItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        System.out.println("Action");
      }
    });

    menu.getItems().add(newItem);
    menu.getItems().add(new SeparatorMenuItem());

    menuBar.getMenus().add(menu);
    root.getChildren().add(menuBar);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
