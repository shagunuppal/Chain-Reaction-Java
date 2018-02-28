package grid;
import grid.AlertMes;


import grid.transsetting;

import static grid.general_grid3D.game;
import java.applet.AudioClip;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;



/**
 * @author shagun uppal
 *main class of the project which handles and manages everything
 */
public class general_grid3D extends Application implements Initializable
{
    
    @FXML
    private StackPane root;
    @FXML
    private Button playgame;
    @FXML
    
    private Button resumegame;
    @FXML
    private Button settings;
    
    /**
     * object of the class game
     */
    public static Game game;
    /**
     * object of stage which displays the game 
     */
    public Stage stage;
    public int j;
   
     @FXML
    private void handleButtonClick(ActionEvent event) 
    {
        makeFadeOut(1);
    }
    /**
     * main method of the game 
     * @param args
     */
    public static void main(String args[])
    {
	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        game = new Game();
        j=10;
    }
       
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        root.setOpacity(0);
        makeFadeInTransition();
    }
    
    /**for better display of the GUI
     * @param a - decides which particular GUI transition is to be shown as per requirement
     */
    public void makeFadeOut(int a) 
    {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(700));
        fade.setNode(root);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished((ActionEvent event)->{
            if(a==1)
            {
                transsetting ob = new transsetting();
                ob.loadSet();
            }
            else if(a==2)
            {
                   setin ob = new setin();
                   ob.loadset();
            }
        });
        fade.play();
    }
    
    /**
     * for better display of the GUI
     */
    private void makeFadeInTransition()
    {
        FadeTransition fd = new FadeTransition();
        fd.setDuration(Duration.millis(700));
        fd.setNode(root);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.play();
    }

    /**
     * specifies what is to be done on clicking the resume button
     * @param event - resume button of the game
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @FXML
    private void res_game(ActionEvent event) throws ClassNotFoundException, IOException 
    {
    	if(game.first==1)
    	{
    	 game.cell1 = game.deserialize();     	
	    	 game.des(game.cell1);
	    	 game.first=0;
	    	 game.check_grid(game.cell1[0][0].get_rowcount(),game.cell1[0][0].get_columncount());
    	}
    	else
    	{
    	Alert alert = new Alert(AlertType.INFORMATION, "Resume is not available ", ButtonType.CLOSE);
            alert.getDialogPane().setMinHeight(100);
     	alert.show();
    	
    	}
    }

    /** 
     * defines what is to be done when the settings option is called by the user .
     * @param event - click on the settings button
     */
    @FXML
    private void setting(ActionEvent event) 
    {
        makeFadeOut(2);
    }
  
}

/**
 * @author shagun uppal
 * The Game class is responible for the chain reaction game to start and set up everything for playing.
 */
class Game
{
    
	/**
	 * used for undo 
	 */
	Button b= new Button();
	/**
	 * flag used to check when the undo option is feasible 
	 */
	int first=0;
	/**
	 * number of rows in the grid
	 */
	int rows;
	/**
	 * number of columns in the grid
	 */
	int columns;
	/**
	 * color  of the current player who has his turn
	 */
	int present_color=0;
	/**
	 * represents the number of valid clicks in the present game 
	 */
	int turn_number=0;
	/**
	 * number of players playing the game 
	 */
	int no_of_players;
	/**
	 * new count of the spheres
	 */
	int new_count;
	/**
	 * flag to chekc the winning condition
	 */
	int win=0;
    /**
     * display window of the game
     */
    static Stage window=null;
    /**
     * stage for new game display
     */
    Stage stage12;
    /**
     * rows in the grid
     */
    static int x;
	/**
	 * columns in the grid
	 */
	static int y;
    /**
     * variables initialized for grid coordinates
     */
    int vx,vx1,vx2,vx12;
    /**
     * variables initialized for grid coordinates
     */
    int vy,vy1,vy2,vy12;
    /**
     * variables initialized for grid coordinates
     */
    int j1,j2,j3,j4;
    /**
     * variables initialized for grid coordinates
     */
    int x_inc,y_inc;
    /**
     * variables initialized for grid coordinates
     */
    int x_len,y_len;
    /**
     * check which one of the players is accessing the settings of the game
     */
    int player_seti=0;
	/**
	 * ArrayList in order to store which all players are now out from the game
	 */
	ArrayList<Integer> loss = new ArrayList<Integer>();
        
	/**
	 * Array for storing the phongmaterials of the spheres
	 */
	PhongMaterial[] colors = new PhongMaterial[8];
	/**
	 * array for creating the required phongmaterials
	 */
	Phong_Material[] material_color = new Phong_Material[8];
	/**
	 * array storing the players
	 */
	Player[] players = new Player[8];
	/**
	 * declared spheres to be added
	 */
	Sphere sp;
    Sphere sp1;     
	Sphere sp2;
	/**
	 * initialized spheres to be added
	 */
	Sphere sphere = new Sphere();
	Sphere sphere1 =new Sphere();
	/**
	 * array storing the colors available for the players
	 */
	Color color[] = { Color.BLUE, Color.RED , Color.MAGENTA, Color.YELLOW, Color.ORANGE, Color.IVORY, Color.GREEN , Color.LIGHTBLUE };
	
	/**
	 * current_player whose tuen is on
	 */
	String current_player;
	/**
	 * cuurent index signifies the index of the current player in the players array
	 */
	int current_index=0;

	/**
	 * Initialized the animation objects to be used for rotation and translation of the spheres
	 */
	RotateTransition anim1;
	RotateTransition anim2;
    TranslateTransition anim3;
	
	/**
	 * array of buttons added to the cells of the grid
	 */
	Button [][] b_array ;
    /**
     * declared the arrays required for storing the cell information
     */
    Cell[][] cell;
    Cell[][] cell_tem;
    /**
     * declared the arrays required for serialization / deserialization purpose
     */
    Cell_Serial[][] cell1;
    Cell_Serial[][] cell_s;
    Cell_Serial[][] cell_undo;
    StackPane pane;
    
    /**
     * declared the groups required for animation in the game 
     */
    Group g;	
    Group g1;
	
    
    /**
     * Constructor of the class Game 
     */
    public Game()
    {
      
        x=9;
        y=6;
        rows=10;
        columns=7;
        no_of_players=2;
        first=0;
        Phong_Material m1 = new Phong_Material(Color.BLUE);
        Phong_Material m2 = new Phong_Material(Color.RED);
        Phong_Material m3 = new Phong_Material(Color.MAGENTA);
        Phong_Material m4 = new Phong_Material(Color.YELLOW);
        Phong_Material m5 = new Phong_Material(Color.ORANGE);
        Phong_Material m6 = new Phong_Material(Color.IVORY);
        Phong_Material m7 = new Phong_Material(Color.GREEN);
        Phong_Material m8 = new Phong_Material(Color.LIGHTBLUE);
        material_color[0]=m1;
        material_color[1]=m2;
        material_color[2]=m3;
        material_color[3]=m4;
        material_color[4]=m5;
        material_color[5]=m6;
        material_color[6]=m7;
        material_color[7]=m8;
        colors[0]=m1.color_material();
        colors[1]=m2.color_material();
        colors[2]=m3.color_material();
        colors[3]=m4.color_material();
        colors[4]=m5.color_material();
        colors[5]=m6.color_material();
        colors[6]=m7.color_material();
        colors[7]=m8.color_material();

        Player player1 = new Player("player1", color[0], 0 , colors[0] );
        Player player2 = new Player("player2", color[1], 1 , colors[1]);
        Player player3 = new Player("player3", color[2], 2 , colors[2]);
        Player player4 = new Player("player4", color[3], 3 , colors[3]);
        Player player5 = new Player("player5", color[4], 4 , colors[4]);
        Player player6 = new Player("player6", color[5], 5 , colors[5]);
        Player player7 = new Player("player7", color[6], 6 , colors[6]);
        Player player8 = new Player("player8", color[7], 7 , colors[7]);
        players[0]=player1;
        players[1]=player2;
        players[2]=player3;
        players[3]=player4;
        players[4]=player5;
        players[5]=player6;
        players[6]=player7;
        players[7]=player8;

        if(window==null)
        	window= new Stage();
        disp();
       
    }
	/**
	 * Displays the main page of the game
	 */
	public void disp()
        {
            Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
                } 
                catch (IOException ex) 
                {
                }
               
                Scene scene = new Scene(root); 

           
           window.setScene(scene);
           window.show();
           stage12=window;
           
        }
        /**
         * Resets the values to create the initial state of the game. 
         */
        public void reset()
        {
            turn_number=0;
            current_index=0;
            win=0;
            loss = new ArrayList<Integer>();
        }
	
	/**
	 * This method initializes the game, setting the scene and all the basic functionalities needed  to start and play the game
	 */
	public void start_game()
	{
	pane = new StackPane();
	pane.setStyle("-fx-background-color: black;");
	g = new Group();
	
	
                
	current_player = players[current_index].name;
	x=rows-1;
                y=columns-1;
	Make_Grid(players[0].color);
	buttons_and_action(rows,columns);
	    Button button = new Button();
	button.setText("Undo");
	button.setTranslateY(410);
	button.setTranslateX(-60);
	button.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
	button.setTextFill(Color.BLACK);
	button.setStyle("fx-background-color: black;");
	
	button.setOnAction(e -> {
	
	if(turn_number==1)
	{
	turn_number=0;
	first = 0;
	current_index=0;
	start_game();
	}
	else
	{
	    	 try {
	    	 if(first==1)
	    	 game.des(game.cell_undo);
	    	 else
	    	 throw new UndoNotSupportedException();
	check_grid(rows,columns);
	} catch (IOException e1) {
	e1.printStackTrace();
	}
	    	 catch(UndoNotSupportedException e2)
	    	 {
	    	e2.getMessage(); 
	    	 }
	}
	    	 
        });	  	
	
	Button bu = new Button();
	bu.setTranslateY(410);
	bu.setTranslateX(60);
	bu.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
	bu.setTextFill(Color.WHITE);
	bu.setMinWidth(80);
	bu.setStyle("fx-background-color: black;");
	 
	MenuButton menuButton = new MenuButton("...");
	MenuItem main_menu = new MenuItem("Main Menu");
	MenuItem new_game = new MenuItem("New Game");
	menuButton.getItems().add(main_menu);
	menuButton.getItems().add(new_game);
	menuButton.setTranslateY(410);
	menuButton.setTranslateX(60);
	menuButton.setTextFill(Color.WHITE);
	menuButton.setFont(Font.font("Courier New", FontWeight.BOLD, 15));
	menuButton.setStyle("-fx-mark-color: white");
	menuButton.setStyle("-fx-background-color: black");
	
	main_menu.setOnAction(e ->
	{
	general_grid3D.game = new Game();
	});
	
	new_game.setOnAction(e ->
	{
	reset();
	start_game();
	});
	
	pane.getChildren().add(g);
	pane.getChildren().add(button);
	pane.getChildren().add(bu);
	pane.getChildren().add(menuButton);
	
	Scene scene = new Scene(pane, 711, 900);
	window.setScene(scene);
        window.show(); 
        pane.setOpacity(0);
        FadeTransition fd = new FadeTransition();
        fd.setDuration(Duration.millis(1000));
        fd.setNode(pane);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.play();

	}
	
	/**
	 * Defines the Rotation of the group assigned to it.
	 * @param gg -group, whose elements are to be rotated
	 * @param anim - object of animation to be used
	 * @param x - number of rows in the grid
	 * @param y - number of columns in the grid
	 * @return - returns the group with rotation animation being imposed on it 
	 */
	public Group rotate(Group gg, RotateTransition anim, int x, int y)
	{
          Rotate rotate = new Rotate();
	      rotate.setPivotX(0);
	      rotate.setPivotY(500);
	      rotate.setPivotZ(50);
	      gg.getTransforms().add(rotate);
	      
	      
	      anim.setCycleCount(Animation.INDEFINITE);
	      anim.setAutoReverse(false);
	      anim.setDuration(Duration.millis(1400));
	      anim.setByAngle(360);
	      anim.setInterpolator(Interpolator.LINEAR);
	      
	      gg.setTranslateX(x);
	      gg.setTranslateY(y);
	      return gg;
	}
	
	/**
	 * create a sphere 
	 * @param x - number of rows in the grid
	 * @param a - row number of the cell
	 * @param y - number of columns in the grid
	 * @param b - column number of the cell
	 * @param material - PhongMaterial of the color of the sphere to be created 
	 * @return - returns the sphere that is created at the required positions
	 */
	public Sphere Make_Sphere(int x,int a,int y,int b, PhongMaterial material)
	{
	float radius=0;
	if(this.x==9 && this.y==6)
	{
	radius=14.0f;
	}
	else
	{
	radius=12.0f;
	}
	Sphere s11 = new Sphere(); 
	s11.setTranslateX( X(y-1,a) ); 
	s11.setTranslateY( Y(x-1,b) );
	s11.setRadius(radius); 
	s11.setMaterial(material);

	return s11;
	}

	/**
	 * Makes the 3D grid for the game 
	 * @param c - The color of the grid as per the player color who is playing at present
	 */
	public void Make_Grid(Color c)
	{
	
	 if(x==9 && y==6)
	{
	vx=100; vy=0; vx1=100; vy1=0; vx2=120; vy2=20; vx12=120; vy12=20; j1=100; j2=0; j3=120; j4=20; //x_inc=90; y_inc=90; x_len=810; y_len=540;
	x_len=630;y_len=420;x_inc=70;y_inc=70;
	}
	else if(x==15 && y==10)
	{
	vx=100; vy=0; vx1=100; vy1=0; vx2=110; vy2=10; vx12=110; vy12=10; j1=100; j2=0; j3=110; j4=10; x_inc=55; y_inc=50; x_len=750; y_len=550;
	
        }
	
	for(int i = 0;i < y+1; i++)
	{
	
	Line line = new Line();
	line.setStartX(vx);
	line.setStartY(vy);
	line.setEndX(vx);
	line.setEndY(vy+x_len);
	line.setStroke(c);
	vx=vx+x_inc;
	ObservableList l = g.getChildren();
	l.add(line); 
	}
	
	
	for(int i = 0;i < x+1; i++)
	{
	
	Line line1 = new Line();
	line1.setStartX(vx1);
	line1.setStartY(vy1);
	line1.setEndX(vx1+y_len);
	line1.setEndY(vy1);
	line1.setStroke(c);
	vy1=vy1+y_inc;
	ObservableList l1 = g.getChildren();
	l1.add(line1); 
	}
	
	
	
	for(int i = 0;i < y+1; i++)
	{
	Line line2 = new Line();
	line2.setStartX(vx2);
	line2.setStartY(vy2);
	line2.setEndX(vx2);
	line2.setEndY(vy2+x_len);
	line2.setStroke(c);
	vx2=vx2+x_inc;
	ObservableList l2 = g.getChildren();
	l2.add(line2); 
	}
	
	
	for(int i = 0;i < x+1; i++)
	{
	
	Line line12 = new Line();
	line12.setStartX(vx12);
	line12.setStartY(vy12);
	line12.setEndX(vx12+y_len);
	line12.setEndY(vy12);
	line12.setStroke(c);
	vy12=vy12+y_inc;
	ObservableList l12 = g.getChildren();
	l12.add(line12); 
	}
	
	
	int j11,j13,j12,j14;
	j12=j2;
	j14=j4;
	
	for(int i=0;i <x+1;i++)
	{
	j11=j1;
	j13=j3;
	
	for(int j=0;j<y+1;j++)
	{
	Line join = new Line();
	join.setStartX(j11);
	join.setStartY(j12);
	join.setEndX(j13);
	join.setEndY(j14);
	j11+=x_inc;
	j13+=x_inc;
	join.setStroke(c);
	ObservableList join1 = g.getChildren();
	join1.add(join);
	}
	
	j12+=y_inc;
	j14+=y_inc;
	
	}
}
        
	    
	
	/**
	 * assigns buttons to each cell and associates them with the necessary action on clicking
	 * @param rows - number of rows in the grid
	 * @param columns - number of columns in the grid
	 */
	public void buttons_and_action(int rows, int columns)
	{
	int st_x=0;
	int st_y=0;
	int b_w=0;
	int b_h=0;
	int x_=0;
	int y_=0;
	int startx=0;
	int starty=0;
	if(x==9 && y==6)
	{
	st_x=-175;
	st_y=-280;
	b_w=45;
	b_h=45;
	x_=70;
	y_=70;
	starty=st_y;
	startx=st_x;
	}
	else if(x==15 && y==10)
	{
	st_x=-250;
	st_y=-350;
	b_w=45;
	b_h=40;
	x_=55;
	y_=50;
	starty=st_y;
	startx=st_x;
	}
	
	b_array = new Button[(rows-1)][(columns-1)];
	cell = new Cell[(rows-1)][(columns-1)];
	cell_s = new Cell_Serial[(rows-1)][(columns-1)];
	  
	  	for(int i=0;i<rows-1;i++)
	  	{
	  	startx=st_x;
	for(int j=0;j<columns-1;j++)
	  	{
	b = new Button();
	  	b_array[i][j]=b;
	  	b.setTranslateX(startx);
	  	b.setTranslateY(starty);
	b.setMinWidth(b_w);
	  	b.setMaxHeight(b_h);
	  	b.setStyle("-fx-background-color: transparent;");
	  	b.setId((i+1)+" "+(j+1));
	  	
	  	startx+=x_;
	  	cell[i][j]=new Cell(i+1,j+1);
	cell_s[i][j]=new Cell_Serial(i+1,j+1);
	  	
	  	pane.getChildren().add(b);
	  	
	  	b_array[i][j].setOnAction(e -> {
	  	
	  	first = 1;
	  	try
	  	{
	cell_undo=deserialize();
	//System.out.println(cell_undo[0][0].get_currind()+" "+current_index);
	  	}
	  	catch (ClassNotFoundException | IOException e2)
	  	{
	e2.printStackTrace();
	}
	  	
                    String[] splited = ((Button) e.getSource()).getId().split(" ");
	  	int a = Integer.parseInt(splited[0]);
	  	int b = Integer.parseInt(splited[1]);
	  	
	  	if(cell[a-1][b-1].sp_list.size()!=0)
	  	{
	  	//PhongMaterial ss = (PhongMaterial)((Shape3D) cell[a-1][b-1].sp_list.get(0)).getMaterial();
	  	//if(ss.getSpecularColor()==colors[current_index%no_of_players].getSpecularColor())
	  	if(players[current_index%no_of_players].color.equals(cell[a-1][b-1].get_sphere_color()))
	  	{
	  	try
	  	{
	  	turn_number+=1;
	add_sphere(a,b,b_array[a-1][b-1], players[current_index%no_of_players].ph,0);
	}
	  	catch (IOException e1)
	  	{
	e1.printStackTrace();
	}

	  	
	  	System.out.println("turn "+turn_number);
	  	while(loss.contains(turn_number%no_of_players))
	  	{
	  	turn_number+=1;
	  	}
	  	current_index=turn_number%no_of_players;
	  	current_player=players[current_index].name;
	  	int node=0;
	  	Line dummy= new Line();
	  	while(node!=g.getChildren().size())
	  	{
	  	if(g.getChildren().get(node).getClass().equals(dummy.getClass()))
	  	{
	  	((Shape) g.getChildren().get(node)).setStroke(players[current_index%no_of_players].color);
	  	}
	  	node++;
	  	}
	  	}
	  	
	  	}
	  	else
	  	{
	  	try {
	  	turn_number+=1;
	add_sphere(a,b,b_array[a-1][b-1], players[current_index%no_of_players].ph,0);
	} catch (IOException e1) {
	e1.printStackTrace();
	}
	  	
	  	System.out.println("turn "+turn_number);
	  	
	  	while(loss.contains(turn_number%no_of_players))
	  	{
	  	turn_number+=1;
	  	}
	  	
	  	current_index=turn_number%no_of_players;
	  	current_player=players[current_index].name;
	  	
	  	int node=0;
	  	Line dummy1= new Line();
	  	
	  	while(node!=g.getChildren().size())
	  	{
	  	if(g.getChildren().get(node).getClass().equals(dummy1.getClass()))
	  	{
	  	((Shape) g.getChildren().get(node)).setStroke(players[current_index%no_of_players].color);
	  	}
	  	node++;
	  	}
	  	}
	  	
	  	
	  	
                });	  	
	  	}	
	starty+=y_;
	  }

	}

	/**
	 * adds the spheres to the cell on the button click and ensures plitting, whenever feasible
	 * @param a - row number of the cell in the grid
	 * @param b - column number of the cell in the grid
	 * @param e - button of the cell
	 * @param material - PhongMaterial of the color of the sphere to be added in the cell
	 * @param pass - Halts the increment of the turn number whenever required 
	 * @throws IOException
	 */
	public void add_sphere(int a,int b, Button e, PhongMaterial material,int pass) throws IOException
	{
	new_count=cell[a-1][b-1].get_count()+1;
	cell[a-1][b-1].set_count(new_count);
	cell_s[a-1][b-1].set_count(new_count);

	   
	if(cell[a-1][b-1].get_count()>cell[a-1][b-1].get_maxcount())
      	{
          	cell[a-1][b-1].set_count(0);
          	cell_s[a-1][b-1].set_count(0);
          	
           	while(cell[a-1][b-1].sp_list.size()!=0)
           	{
                cell[a-1][b-1].sp_list.remove(0);
            }
           	
           	Group tem= new Group();
           	ObservableList spl = tem.getChildren();
           	tem.setTranslateX( X(b-1,0) );
	tem.setTranslateY( Y(a-1,0) ); 
	int shift = 0;
	if(x==9 && y==6)
	{
	shift=70;
	}
	else if(x==15 && y==10)
	{
	shift=55;
	}
            if(b-1>0)
            {
                Sphere sph=Make_Sphere(a,0,b,0,colors[(current_index)%no_of_players]);
                sph.setMaterial(material);
                spl.add(sph);
                Translate tra=new Translate();
             	sph.getTransforms().add(tra);
                TranslateTransition t = new TranslateTransition(Duration.millis(500),sph);
              	t.setToX( X(b-1,-shift));
         	pane.getChildren().add(sph);
         	
         	t.setOnFinished(x ->{
         	
         	int ind=0;
         	
         	for(int i=0; i<cell[a-1][b-2].sp_list.size(); i++)
         	{
          	((Shape3D) cell[a-1][b-2].sp_list.get(i)).setMaterial(material);
         	}
         	
         	try {
	add_sphere(a, b-1, b_array[a-1][b-2], material,1);
	check_grid(rows,columns);
	} catch (IOException e1) {
	e1.printStackTrace();
	}
                    cell[a-1][b-2].set_sphere_color(color[ind]);
       	  	pane.getChildren().remove(sph);
       	  	
         	});
             
              	t.play();
              	
            }

            if(b+1<=y)
            {
            	Sphere sph=Make_Sphere(a,0,b,0,colors[(current_index)%no_of_players]);
                sph.setMaterial(material);
                spl.add(sph);
                Translate tra=new Translate();
              	sph.getTransforms().add(tra);
                TranslateTransition t = new TranslateTransition(Duration.millis(500),sph);
              	t.setToX( X(b-1,shift) );
            	pane.getChildren().add(sph);
            	
            	t.setOnFinished(x ->{
            	
            	int ind=0;
                  	for(int g=0;g<colors.length;g++)
                  	{
                  	if(colors[g].getSpecularColor()==material.getSpecularColor())
                  	ind=g;
                  	break;
                  	}
            	for(int i=0; i<cell[a-1][b].sp_list.size(); i++)
         	{
         	((Shape3D) cell[a-1][b].sp_list.get(i)).setMaterial(material);
         	}
            	try {
	add_sphere(a, b+1, b_array[a-1][b], material,1);
	check_grid(rows,columns);
	} catch (IOException e1) {
	e1.printStackTrace();
	}
	pane.getChildren().remove(sph);
	cell[a-1][b].set_sphere_color(color[ind]);
	
            	});

              t.play();
            }

           	if(a-1>0)
            {
            	Sphere sph=Make_Sphere(a,0,b,0,colors[(current_index)%no_of_players]);
                sph.setMaterial(material);
                spl.add(sph);
                
                Translate tra=new Translate();
              	sph.getTransforms().add(tra);
                TranslateTransition t = new TranslateTransition(Duration.millis(500),sph);
              	t.setToY( Y(a-1,-shift) );
            	pane.getChildren().add(sph);
            	
            	t.setOnFinished(x ->{
            	int ind=0;
                  	for(int g=0;g<colors.length;g++)
                  	{
                  	if(colors[g].getSpecularColor()==material.getSpecularColor())
                  	ind=g;
                  	break;
                  	}
            	
            	for(int i=0; i<cell[a-2][b-1].sp_list.size(); i++)
         	{
         	((Shape3D) cell[a-2][b-1].sp_list.get(i)).setMaterial(material);
         	}
          	  	pane.getChildren().remove(sph);
          	  	try {
	add_sphere(a-1, b, b_array[a-2][b-1], material,1);
	check_grid(rows,columns);
	} catch (IOException e1) {
	e1.printStackTrace();
	}
          	  	cell[a-2][b-1].set_sphere_color(color[ind]);
          	  	try {
	check_grid(rows,columns);
	} catch (IOException e1) {
	e1.printStackTrace();
	}
            	});
              
              	t.play();
              	
            }

            if(a+1<=x)
            {
             	Sphere sph=Make_Sphere(a,0,b,0,colors[(current_index)%no_of_players]);
                sph.setMaterial(material);
                spl.add(sph);
                Translate tra=new Translate();
                sph.getTransforms().add(tra);
              	TranslateTransition t = new TranslateTransition(Duration.millis(500),sph);
              	t.setToY( Y(a-1,shift) );  // 55,50 90,90
            	pane.getChildren().add(sph);
              	t.setOnFinished(x ->{
              	
              	int ind=0;
                  	for(int g=0;g<colors.length;g++)
                  	{
                  	if(colors[g].getSpecularColor()==material.getSpecularColor())
                  	ind=g;
                  	break;
                  	}
              	
              	for(int i=0; i<cell[a][b-1].sp_list.size(); i++)
         	{
         	((Shape3D) cell[a][b-1].sp_list.get(i)).setMaterial(material);
         	}
              	try {
	add_sphere(a+1, b, b_array[a][b-1], material,1);
	check_grid(rows,columns);
	} catch (IOException e1) {
	e1.printStackTrace();
	}
            	pane.getChildren().remove(sph);
            	cell[a][b-1].set_sphere_color(color[ind]);
            	try {
	check_grid(rows,columns);
	} catch (IOException e1) {
	e1.printStackTrace();
	}
              	});
              
              	t.play();
            } 

        }
        else if(cell[a-1][b-1].get_count()==1)
	{
	
	sp = Make_Sphere(a,0,b,0,colors[(current_index)%no_of_players]);
	sp.setMaterial(material);                             
	  	cell[a-1][b-1].sp_list.add(sp);
	  	cell[a-1][b-1].grp.setTranslateX( X(b-1,0) );
	  	cell[a-1][b-1].grp.setTranslateY( Y(a-1,0) );
                        int tem=current_index-1;
            if(pass==0)
	  	cell[a-1][b-1].set_sphere_color(color[(turn_number)%no_of_players]);
	  	
	  	if(!pane.getChildren().contains(cell[a-1][b-1].grp))
            	pane.getChildren().add(cell[a-1][b-1].grp);

	b_array[a-1][b-1].toFront();
	check_grid(rows,columns);

	  	}

	  	else if(cell[a-1][b-1].get_count()==2)
	  	{   
	  	sp1 = Make_Sphere(a,-5,b,-10,colors[(current_index)%no_of_players]);
	      	sp1.setMaterial(material);
	      	cell[a-1][b-1].sp_list.add(sp1);
	      	anim1 = new RotateTransition(Duration.seconds(0), cell[a-1][b-1].grp);
	      	cell[a-1][b-1].grp = rotate(cell[a-1][b-1].grp, anim1, X(b-1,2), Y(a-1,0));
            if(pass==0)
            	cell[a-1][b-1].set_sphere_color(color[(turn_number)%no_of_players]);
	  	

	      	b_array[a-1][b-1].toFront();
            check_grid(rows,columns);
            anim1.play();
                        
                              
	  }
	  else if(cell[a-1][b-1].get_count()==3)
	  {
                             
	  sp2 = Make_Sphere(a,10,b,-5,colors[(current_index)%no_of_players]);
	  cell[a-1][b-1].set_sphere_color(color[(turn_number)%no_of_players]);
	      sp2.setMaterial(material);
	      cell[a-1][b-1].sp_list.add(sp2);
	      anim2 = new RotateTransition(Duration.seconds(0), cell[a-1][b-1].grp);
	      cell[a-1][b-1].grp = rotate(cell[a-1][b-1].grp, anim2, X(b-1,2), Y(a-1,0));
	      if(pass==0)
	      	cell[a-1][b-1].set_sphere_color(color[(turn_number)%no_of_players]);
	      
	      b_array[a-1][b-1].toFront();
          check_grid(rows,columns);
          anim2.play();
         
	   }
	
	}
	
	/**
	 * puts the required number of spheres obtained from deserialization into the particular cells of the grid
	 * @param a - row number of the cell in the grid
	 * @param b - column number of the cell in the grid
	 * @param e - button of the cell
	 * @param material - PhongMaterial of the color of the spheres in the cell
	 * @param pass - halts the turn number whenever required
	 * @throws IOException
	 */
	public void new_sphere(int a,int b, Button e, PhongMaterial material,int pass) throws IOException
	{
	if(cell[a-1][b-1].get_count()==1)
	{
	
	sp = Make_Sphere(a,0,b,0,colors[(current_index)%no_of_players]);
	sp.setMaterial(material);                             
	  	cell[a-1][b-1].sp_list.add(sp);
	  	cell[a-1][b-1].grp.setTranslateX( X(b-1,0) );
	  	cell[a-1][b-1].grp.setTranslateY( Y(a-1,0) );
            int tem=current_index-1;
            if(pass==0)
	  	cell[a-1][b-1].set_sphere_color(color[(turn_number)%no_of_players]);
	  	
	  	if(!pane.getChildren().contains(cell[a-1][b-1].grp))
            	pane.getChildren().add(cell[a-1][b-1].grp);

	b_array[a-1][b-1].toFront();

	  	}

	  	else if(cell[a-1][b-1].get_count()==2)
	  	{   
	  	sp = Make_Sphere(a,0,b,0,colors[(current_index)%no_of_players]);
	sp.setMaterial(material);                             
	  	cell[a-1][b-1].sp_list.add(sp);
	sp1 = Make_Sphere(a,-5,b,-10,colors[(current_index)%no_of_players]);
	      	sp1.setMaterial(material);
	      	cell[a-1][b-1].sp_list.add(sp1);
	      	anim1 = new RotateTransition(Duration.seconds(0), cell[a-1][b-1].grp);
	      	cell[a-1][b-1].grp = rotate(cell[a-1][b-1].grp, anim1, X(b-1,2), Y(a-1,0));
            if(pass==0)
            	cell[a-1][b-1].set_sphere_color(color[(turn_number)%no_of_players]);
	  	
	      	b_array[a-1][b-1].toFront();
	anim1.play();
	if(!pane.getChildren().contains(cell[a-1][b-1].grp))
            	pane.getChildren().add(cell[a-1][b-1].grp);
                        
                              
	  }
	  else if(cell[a-1][b-1].get_count()==3)
	  {
                             
	  sp = Make_Sphere(a,0,b,0,colors[(current_index)%no_of_players]);
	  sp.setMaterial(material);                             
	  cell[a-1][b-1].sp_list.add(sp);
	  sp1 = Make_Sphere(a,-5,b,-10,colors[(current_index)%no_of_players]);
	  sp1.setMaterial(material);
	  cell[a-1][b-1].sp_list.add(sp1);
	  sp2 = Make_Sphere(a,10,b,-5,colors[(current_index)%no_of_players]);
	      sp2.setMaterial(material);
	      cell[a-1][b-1].sp_list.add(sp2);
	      anim2 = new RotateTransition(Duration.seconds(0), cell[a-1][b-1].grp);
	      cell[a-1][b-1].grp = rotate(cell[a-1][b-1].grp, anim2, X(b-1,2), Y(a-1,0));
	      if(pass==0)
	      	cell[a-1][b-1].set_sphere_color(color[(turn_number)%no_of_players]);
	      
	      b_array[a-1][b-1].toFront();
          //check_grid(rows,columns);
          anim2.play();
      	if(!pane.getChildren().contains(cell[a-1][b-1].grp))
        	pane.getChildren().add(cell[a-1][b-1].grp);
         
	   }
	
	for(int u=0;u<rows-1;u++)
	{
	for(int v=0;v<columns-1;v++)
	{
	b_array[a-1][b-1].toFront();
	}
	}

	}
	
	
	/**
	 * Method in order to renew the state of the game and update it in the array
	 * @param rows - number of rows in the grid
	 * @param columns - number of columns in the grid
	 * @throws IOException
	 */
	public void check_grid(int rows , int columns) throws IOException
	{
	int flag = 0, w=0 , i, j ,we=0;
	Color c =Color.rgb(0,0,0);
        int winner=0;
        int cc=0;
        
        for(i=0;i<rows-1;i++)
	{
        	for(j=0;j<columns-1;j++)
        	{
        	if(cell[i][j].get_count()!=0)
        	{
        	for(int r=0;r<color.length;r++)
        	{
        	if(((PhongMaterial) ((Shape3D) cell[i][j].sp_list.get(0)).getMaterial()).getDiffuseColor().equals(color[r]))
        	{
        	cell_s[i][j].set_color_index(r);
        	cell[i][j].set_sphere_color(color[r]);
        	break;
        	}
        	}
        	}
        	else
        	{
        	cell_s[i][j].set_color_index(0);
	                cell[i][j].set_sphere_color(Color.BLACK);
        	}
        	}
	}
        for(i=0;i<rows-1;i++)
        {
        	for(j=0;j<columns-1;j++)
        	{	
        	if(flag==0)
        	{
        	if(!cell[i][j].get_sphere_color().equals(Color.rgb(0,0,0)))
        	{
	flag=1;
	c=cell[i][j].get_sphere_color();
	for(int k=0;k<8;k++)
                        {
	if(c.equals(players[k].color))
                            {
                                winner=k+1;
                                break;
                            }
                        }
        	}
        	}
        	else
        	{
        	if(!cell[i][j].get_sphere_color().equals(c) && !cell[i][j].get_sphere_color().equals(Color.rgb(0,0,0)))
        	{
        	w=10;
        	}
        	}
	
	if(cell[i][j].get_count()!=0)
	{
	for(int r=0;r<color.length;r++)
	{
	if(cell[i][j].get_sphere_color().equals(color[r]))
	{
	cell_s[i][j].set_color_index(r);
	
	break;
	}
	}
	}
	
	cell_s[i][j].set_count(cell[i][j].get_count());
	cell_s[i][j].set_currind(turn_number);
	
	}
	}
	if(turn_number>no_of_players)
	{
	for(i=0;i<no_of_players;i++)
	{
	int ui=0;
	for(j=0;j<rows-1;j++)
	{
	for(int k=0;k<columns-1;k++)
	{
	if(cell[j][k].get_sphere_color().equals(players[i].color))
	{
	ui=1;
	}
	}
	
	
	}
	if(ui==0)
	{
	if(!loss.contains(i))
	loss.add(i);
	if(i==turn_number%no_of_players)
	{
	while(loss.contains(turn_number%no_of_players))
	  	{
	  	turn_number+=1;
	  	}
	  	current_index=turn_number%no_of_players;
	  	current_player=players[current_index].name;
	  	int node=0;
	  	Line dummy= new Line();
	  	while(node!=g.getChildren().size())
	  	{
	  	if(g.getChildren().get(node).getClass().equals(dummy.getClass()))
	  	{
	  	((Shape) g.getChildren().get(node)).setStroke(players[current_index%no_of_players].color);
	  	}
	  	node++;
	  	}
	}
	}
	
	}
	
	}	
	/*for(i=0;i<loss.size();i++)
	{
	System.out.print(loss.get(i)+" ");
	}
	System.out.println();
	
	*/
	
	        serialize();
	        
	         if(turn_number>1)
	         {
	        	if(w!=10 && win!=1 && winner!=0)
	     	{
	                         win=1;
	                         Label bb = new Label();
	                         bb.setText("Player "+winner+" wins!");
	                         bb.setTextFill(Color.WHITE);
	                         bb.setMinSize(30, 30);
	                        // AlertMes.s="Player "+ winner;
	                         AlertMes ob = new AlertMes();
	                         ob.win_display();
	                         
	                         Alert alert = new Alert(AlertType.INFORMATION, "Player "+ winner+ " wins!", ButtonType.OK);
	                         alert.getDialogPane().setMinHeight(100);
	                  	 alert.show();
	                  	 /*alert.setOnHidden(e->{
	                  	 System.exit(0);
	                  	 });*/
	     	}
	         }
	       
	}
	
	/**
	 * @param a - row number of the cell in the grid
	 * @param b - column number of the cell in the grid
	 * @return - returns the required x of the position in the grid to add sphere
	 */
	public int X(int a, int b)
	{
	int ans=0;
	if(x==9 && y==6)
	{
	ans=-175+70*(a)+b;
	}
	else if(x==15 && y==10)
	{
	ans=-250+55*(a)+b;
	}
	return ans;
	}

	/**
	 * @param a - row number of the cell in the grid
	 * @param b - column number of the cell in the grid
	 * @return - returns the required y of the position in the grid to add sphere
	 */
	public int Y(int a,int b)
	{
	int ans1=0;
	if(x==9 && y==6)
	{
	ans1=-285+70*(a)+b;
	}
	else if(x==15 && y==10)
	{
	ans1=-350+50*(a)+b;
	}
	return ans1;
	}
	
	/**
	 * Method used to serialize a particular state of the game for the undo and the resume options
	 * @throws IOException
	 */
	public static void serialize() throws IOException
	{
	
	ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream("out.txt"));
	for(int i=0;i<x;i++)
	{
	for(int j=0;j<y;j++)
	{
	out.writeObject(game.cell_s[i][j]);
	}
	}
	out.close();

	}

	
	/**
	 * @return - returns the 2D array containing information about the deserialized components of the grid
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Cell_Serial[][] deserialize() throws IOException, ClassNotFoundException
	{
	ObjectInputStream in = new ObjectInputStream (new FileInputStream("out.txt")); 
	
	Cell_Serial c =  (Cell_Serial) in.readObject();
	int g=c.get_rowcount()-1;
	int h=c.get_columncount()-1;
	Cell_Serial[][] cellx = new Cell_Serial[g][h];
	cellx[0][0]=c;
	
	for(int i=0;i<g;i++)
	{
	for(int j=0;j<h;j++)
	{
	if(!(i==0 && j==0))
	{
	cellx[i][j] = (Cell_Serial) in.readObject();
	}
	}
	}
	
	in.close();
	return cellx;
	}
	
	
	/**
	 * helps in building the scene of the deserialized state in the grid 
	 * @param cell_t - 2D array containing the deserialized information of the grid
	 * @throws IOException
	 */
	public void des(Cell_Serial[][] cell_t) throws IOException
	{
	turn_number=cell_t[0][0].get_currind();
	no_of_players=cell_t[0][0].get_players_total();
	
	rows=cell_t[0][0].get_rowcount();
	columns=cell_t[0][0].get_columncount();
	current_index=turn_number%no_of_players;
	start_game();
	
	for(int i=0;i<rows-1;i++)
	{
	for(int j=0;j<columns-1;j++)
	{
	cell[i][j].set_count(cell_t[i][j].get_count()); 
	   	 	cell[i][j].set_numberx(cell_t[i][j].get_numberx());
	   	 	cell[i][j].set_numbery(cell_t[i][j].get_numbery());
	   	 	cell[i][j].set_maxcount(cell_t[i][j].get_maxcount());
	   	 	
	   	 	if(cell[i][j].get_count()!=0)
	   	 	cell[i][j].set_sphere_color(color[cell_t[i][j].get_color_index()]);
	   	 	else
	   	 	cell[i][j].set_sphere_color(Color.BLACK);
	
	   	 	if(cell[i][j].get_count()!=0)
	   	 	{
	   	 	if(cell_t[i][j].get_color_index()!=-1)
	   	 	new_sphere(i+1,j+1,b_array[i][j],colors[cell_t[i][j].get_color_index()],0);
	   	 	}
	}
	}
	

	int node=0;
	  	Line dummy1= new Line();
	  	while(node!=g.getChildren().size())
	  	{
	  	if(g.getChildren().get(node).getClass().equals(dummy1.getClass()))
	  	{
	  	((Shape) g.getChildren().get(node)).setStroke(players[turn_number%no_of_players].color);
	  	}
	  	node++;
	  	}
  	
	}
        
}

/**
 * @author niket singh
 * This class represents the 2D array of the cells in the grid containing the javafx components
 */
class Cell
{
	/**
	 * represents the count of the number of spheres present in the grid 
	 */
	private int count=0;
	/**
	 * represents the x coordinate of the cell in the grid 
	 */
	private int number_x;
	/**
	 * represents the y coordinate of the cell in the grid 
	 */
	private int number_y;
    /**
     * represents the maximum permiited count of spheres in the cell
     */
    private int max_count; 
    /**
     * represents the color of the spheres in the cell
     */
    private Color sphere_color;
        
	/**
	 * group containing the spheres of the cell
	 */
	public Group grp = new Group();
	/**
	 * Each cell has a list in which the spheres of the cell are added
	 */
	public ObservableList sp_list = grp.getChildren();
	
	/**
	 * @param number_x
	 * @param number_y
	 *  x and y are passed in the constructor, representing the rows and columns of the grid and the max_count of each cell is set.
	 */
	public Cell(int number_x,int number_y)
	{
	this.number_x=number_x;
	this.number_y=number_y;
	count=0;
	    
	if(number_x==1)
        {
            if(number_y==1 || number_y==game.y)
                max_count=1;
            else
                max_count=2;
        } 
        else if(number_y==1)
        {
            if(number_x==1 || number_x==game.x)
                max_count=1;
            else
                max_count=2;
        }
        else if(number_x==game.x)
        {
            if(number_y==1 || number_y==game.y)
                max_count=1;
            else
                max_count=2;
        }
        else if(number_y==game.y)
        {
            if(number_x==1 || number_x==game.x)
                max_count=1;
            else
                max_count=2;
        }
        else
            max_count=3;
        
        sphere_color=Color.rgb(0,0,0);
	}
	
	/**
	 * setter for max_count
	 * @param o - sets the max_count of the cell to 0 whnever required on splitting
	 */
	public void set_maxcount(int o)
	{
	max_count=o;
	}
	
	/**
	 * getter for count
	 * @return - returns the count of the spheres in the cell
	 */
	public int get_count()
	{
	return count;
	}
	
    /**
     * getter for max_count
     * @return - returns the maximum number of spheres allowed in the cell
     */
    public int get_maxcount()
	{
    	return max_count;
	}
	
	/**
	 * setter for count
	 * @param x - sets the count of the cell same as the parameter passed
	 */
	public void set_count(int x)
	{
	count=x;
    }
	
	/**
	 * getter for number_x
	 * @return - returns the x coordinate of the cell in the grid
	 */
	public int get_numberx()
	{
	return number_x;
	}
	
	/**
	 * getter for number_y
	 * @return - returns the y coordinate of the cell in the grid
	 */
	public int get_numbery()
	{
	return number_y;
	}
	
	/**
	 * setter for number_x
	 * @param o - sets the x coordinate of the cell same as the parameter passed
	 */
	public void set_numberx(int o)
	{
	number_x=o;
	}
	
	/**
	 * setter for number_y
	 * @param o - sets the y coordinate of the cell same as the parameter passed
	 */
	public void set_numbery(int o)
	{
	number_y=o;
	}
	
	/**
	 * setter for sphere_color
	 * @param col - sets the color of the sphere in the cell same as the parameter passed
	 */
	public void set_sphere_color(Color col)
	{
	sphere_color = col;
	}
	
	/**
	 * getter for sphere_color
	 * @return - returns the color of the sphere in the cell
	 */
	public Color get_sphere_color()
	{
	return sphere_color;
	}

}


/**
 * @author shagun uppal
 *
 */
class Cell_Serial implements Serializable
{
	/**
	 * sphere count of the cell
	 */
	private int count=0;
	/**
	 * x coordinate of the cell in the grid
	 */
	private int number_x;
	
	/**
	 * y coordinate of the cell in the grid
	 */
	private int number_y;
    /**
     * maximum count of the spheres permitted for the cell
     */
    private int max_count; 
    /**
     * the index of the color from the color array, assigned to spheres of the cell
     */
    private int color_index;
	/**
	 * current index of the cell in order to specify which player's turn is on 
	 */
	private int curr_ind;
	/**
	 * Total number of players for the game
	 */
	private int players_total;
	/**
	 * number of rows in the grid
	 */
	private int row_count;
	/**
	 * number of columns in the grid 
	 */
	private int column_count;
	    
	
	/**
	 * @param number_x
	 * @param number_y
	 *  x and y are passed in the constructor, representing the rows and columns of the grid and the max_count of each cell is set.
	 */
	public Cell_Serial(int number_x,int number_y)
	{
	
	this.number_x=number_x;
	this.number_y=number_y;
	curr_ind=game.turn_number;
	players_total=game.no_of_players;
	row_count=game.rows;
	column_count=game.columns;
	count=0;
	
	    if(number_x==1)
        {
            if(number_y==1 || number_y==game.y)
                max_count=1;
            else
                max_count=2;
        } 
        else if(number_y==1)
        {
            if(number_x==1 || number_x==game.x)
                max_count=1;
            else
                max_count=2;
        }
        else if(number_x==game.x)
        {
            if(number_y==1 || number_y==game.y)
                max_count=1;
            else
                max_count=2;
        }
        else if(number_y==game.y)
        {
            if(number_x==1 || number_x==game.x)
                max_count=1;
            else
                max_count=2;
        }
        else
        {
            max_count=3;
        }
	}
	
	/**
	 * getter for count
	 * @return - returns the count of the spheres in the cell
	 */
	public int get_count()
	{
	return count;
	}
	
    /** getter for max_count
     * @return - returns th maximum number of spheres allowed for the cell
     */
    public int get_maxcount()
	{
    	return max_count;
	}
	
	/**
	 * setter for curr_ind
	 * @param x - sets the x coordinate of the cell same as the parameter passed
	 */
	public void set_currind(int x)
	{
	curr_ind=x;
    }
	
	/**
	 * getter for cur_ind
	 * @return - returns the index for specifying the current index in the game
	 */
	public int get_currind()
	{
	return curr_ind;
	}
	
	/**
	 * setter for count
	 * @param x - sets the count of the cell same as the parameter passed
	 */
	public void set_count(int x)
	{
	count=x;
    }
	
	/**
	 * getter for player_total
	 * @return - returns the total number of players playing the game
	 */
	public int get_players_total()
	{
	return players_total;
	}
	
	
	/**
	 * getter for number_x
	 * @return - returns the x coordinate of the cell in the grid
	 */
	public int get_numberx()
	{
	return number_x;
	}
	
	/**
	 * getter for number_y
	 * @return - returns the y coordinate of the cell in the grid
	 */
	public int get_numbery()
	{
	return number_y;
	}
	
	/**
	 * getter for row_count
	 * @return - returns the number of rows in the grid
	 */
	public int get_rowcount()
	{
	return row_count;
	}
	
	/**
	 * getter for column_count
	 * @return - returns the umber of columns in the grid
	 */
	public int get_columncount()
	{
	return column_count;
	}
	
	/**
	 * setter for color_index
	 * @param s - sets the color_index of the cell same as the parameter passed
	 */
	public void set_color_index(int s)
	{
	color_index=s;
	}
	
	/**
	 * getter for color_index
	 * @return - returns the index color of the spheres present in the cell
	 */
	public int get_color_index()
	{
	return color_index;
	}
	
}


/**
 * @author shagun uppal
 *
 */
class Player
{
	/**
	 * name of the player 
	 */
	String name;
	
	
	/**
	 * color assigned to the player
	 */
	Color color;
	
	/**
	 * number assigned to the player 
	 */
	int player_number;
	
    /**
     * PhongMaterial of the colors of the sphere, associated to the player
     */
    PhongMaterial ph;
	
    
    
	/**
	 * @param name
	 * @param color
	 * @param player_number
	 * @param p
	 * All the parameters defined above are passed to the constructor to initialize the player
	 */
	public Player(String name, Color color, int player_number , PhongMaterial p)
	{
	this.name=name;
	this.color=color;
	this.player_number=player_number;
        ph = p;
	}
}


/**
 * @author niket singh
 *This class is used for generaling PhongMaterials for the desired color.
 */
class Phong_Material
{
	/**
	 *The required color is passed as an argument to the constructor 
	 */
	Color color;
	
	/**
	 * @param color - to specify the color of the required material 
	 * Constructor for the class
	 */
	public Phong_Material(Color color)
	{
	this.color=color;
	}
	
	/**
	 * Creates the PhongMaterial for the specified color
	 * @return - returns the material for the sphere with the desired color
	 */
	public PhongMaterial color_material()
	{
	PhongMaterial material = new PhongMaterial();
	material.setDiffuseColor(color);
	material.setSpecularColor(color);
	material.setSpecularPower(10);
	return material;
	}
	
	
}



/**
 * @author shagun uppal
 *This class generates an exception whenever undo button is disabled but the player tries to click it.
 */
class UndoNotSupportedException extends Exception
{
	/**
	 * Constructor for the class 
	 */
	UndoNotSupportedException()
	{
	super("Undo is not available");
	}
}