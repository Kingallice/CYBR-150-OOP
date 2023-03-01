
package randomlines;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class RandomLines extends Application
{
    int intNumScreens = 0, NumLines = 10000;
    Rectangle2D bounds;
    Button btnStart, btnStop;
    ChoiceBox cboChoices, cboColors;
    Screen[] ScreensArr;
    String[] ScreensArrTXT;
    Scene sceneStart, sceneShow;
    Random rand;
    double X1,X2,Y1,Y2;
    Line[] lines;
    KeyFrame keyFrame;
    Timeline timeline;
    Duration timepoint;
    Duration pause;
    Pane pane;
    Node[] DrawItems;
    public static void main(String[] args) 
    {
        launch(args);
    }

    int choice = -1;
    @Override
    public void start(Stage stage)
    {
        rand = new Random();
        btnStart = new Button("Start");
        btnStart.setPrefSize(100, 100);
        
        ScreensArr = getScreens();
        ScreensArrTXT = getScreensTXT(ScreensArr);
        
        cboColors = new ChoiceBox();
        cboColors.getItems().addAll("Random", "Reds", "Greens", "Blues");
        cboColors.setValue("Random");

        Canvas canvas = new Canvas(300, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D(); 
        gc.fill();
        
        btnStop = new Button("Stop");
        pane = new Pane();
        
        timeline = new Timeline();
        timepoint = Duration.ZERO ;
        pause = Duration.seconds(.5);
        
        DrawItems = new Node[2];
        DrawItems[0] = pane;
        DrawItems[1] = btnStop;
        
        btnStart.setOnAction(event -> {
            lines(stage);
            stage.setMaximized(true);
        });
        btnStop.setOnAction(event -> {
            timeline.stop();
            timeline.getKeyFrames().remove(0,timeline.getKeyFrames().size());
            stage.setScene(sceneStart);
        });
        timeline.setOnFinished(event ->{
            lines(stage);
        });
        VBox vbox = new VBox(new Label("Settings"),new HBox(new Label("Colors: "),cboColors));
        vbox.setSpacing(4);        
        
        GridPane gridpane = new GridPane();
        gridpane.add(btnStart, 0 ,0);
        gridpane.add(vbox, 1, 0);
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setVgap(2);   
        gridpane.setHgap(10);
        
        sceneStart = new Scene(gridpane);
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        stage.setScene(sceneStart);
        stage.setMaximized(true);
        stage.show();
    }
    public String randomColor(String seed)
    {
        int intRed = 0;
        int intGreen = 0;
        int intBlue = 0;
        switch(seed)
        {
            case "Random":
                intRed = rand.nextInt(256);
                intGreen = rand.nextInt(256);
                intBlue = rand.nextInt(256);
                break;
            case "Reds":
                intRed = 256 - rand.nextInt(64);
                intGreen = rand.nextInt(128);
                intBlue = rand.nextInt(128);
                break;
            case "Greens":
                intRed = rand.nextInt(128);
                intGreen = 256 - rand.nextInt(64);
                intBlue = rand.nextInt(128);
                break;
            case "Blues":
                intRed = rand.nextInt(128);
                intGreen = rand.nextInt(128);
                intBlue = 256 - rand.nextInt(64);
                break;
        }
        String str = "-fx-stroke: rgb(" + intRed + "," + intGreen + "," + intBlue + ");";
        return str;
    }
    public void lines(Stage stage){
        lines = new Line[4];
        pane.getChildren().remove(0, pane.getChildren().size());
        timeline.getKeyFrames().remove(0, timeline.getKeyFrames().size());
        for(int i = 0; i < NumLines; i++)
        {
            if (i == 0)
            {
            X1 = (stage.getWidth()-100)*rand.nextDouble();
            Y1 = (stage.getHeight()-100)*rand.nextDouble();
            }
            else
            {
                X1 = lines[i%lines.length-1].getEndX();
                Y1 = lines[i%lines.length].getEndY();
            }
            X2 = (stage.getWidth()-100)*rand.nextDouble();
            Y2 = (stage.getHeight()-100)*rand.nextDouble();

            Line line = new Line();
            line.setStartX(X1);
            line.setStartY(Y1);
            line.setEndX(X2);
            line.setEndY(Y2);

            String seed = (String) cboColors.getValue();

            int strokesize = (int) (20*rand.nextDouble()) + 1;
            line.setStyle(randomColor(seed) + "-fx-stroke-width: "+strokesize+";");

            timepoint = timepoint.add(pause);
            keyFrame = new KeyFrame(timepoint, e -> pane.getChildren().add(line));
            timeline.getKeyFrames().add(keyFrame);
        }
        stage.setScene(drawScene(DrawItems));
        timeline.playFromStart();
    }
    public Screen[] getScreens()
    {
        int NumScreens = Screen.getScreens().size();
        Screen[] Available_Screens = new Screen[NumScreens];
        for (int i = 0; i < NumScreens; i++) {
            Available_Screens[i] = Screen.getScreens().get(i);
        }
        return Available_Screens;
    }
    public String[] getScreensTXT (Screen[] Available_Screens)
    {
        String[] txtAvailable_Screens = new String[Available_Screens.length];
        for (int i = 0; i < Available_Screens.length; i++) {
            txtAvailable_Screens[i] = "Display " + i;
        }
        return txtAvailable_Screens;
    }
    public Line nextLine(Stage stage)
    {
        Line line = new Line();
        
        double X = rand.nextDouble();
        double Y = rand.nextDouble();
        
        X = stage.getWidth()/X;
        Y = stage.getHeight()/Y;
        
        line.setEndX(X);
        line.setEndY(Y);
        
        return line;
    }
    public Scene drawScene(Node[] objects)
    {
        VBox vbox = new VBox();
        for(Node object : objects)
        {
            vbox.getChildren().add(object);
        }
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setPadding(new Insets(25));
        Scene scene = new Scene(vbox);
        return scene;
    }
}