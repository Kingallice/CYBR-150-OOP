/*
Project: Lab 6
   Name: Noah Mosel
    Lab: Lab 6
 */
package oop.lab6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JOptionPane;
        
public class Lab6 extends Application {
    ImageView Penny,Nickel,Dime,Quarter;
    Label lblPennies,lblNickels,lblDimes,lblQuarters;
    int intPennies = 0,intNickels = 0,intDimes = 0,intQuarters = 0;
    double dblValue = 0;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage)
    {      
        Label lblChange = new Label("Value: ");
        //Quarter
        Image imgQuarter = new Image("file:..\\OOP - Lab6\\src\\oop\\lab6\\Quarter.png");
        Quarter = new ImageView(imgQuarter);
        lblQuarters = new Label("Quarters: ");
        Quarter.setOnMouseClicked(event ->{
            intQuarters++;
            dblValue = dblValue + 0.25;
            lblChange.setText("Value: $" + dblValue);
            lblQuarters.setText("Quarters: " + intQuarters);
        });
        //Dime
        Image imgDime = new Image("file:..\\OOP - Lab6\\src\\oop\\lab6\\Dime.png");
        Dime = new ImageView(imgDime);
        lblDimes = new Label("Dimes: ");
        Dime.setOnMouseClicked(event ->{
            intDimes++;
            dblValue += 0.1;
            lblChange.setText("Value: $" + dblValue);
            lblDimes.setText("Dimes: " + intDimes);
        });
        //Nickel
        Image imgNickel = new Image("file:..\\OOP - Lab6\\src\\oop\\lab6\\Nickel.png");
        Nickel = new ImageView(imgNickel);
        lblNickels = new Label("Nickels: ");
        Nickel.setOnMouseClicked(event ->{
            intNickels++;
            dblValue = dblValue + 0.05;
            lblChange.setText("Value: $" + dblValue);
            lblNickels.setText("Nickels: " + intNickels);
        });
        //Penny
        Image imgPenny = new Image("file:..\\OOP - Lab6\\src\\oop\\lab6\\Penny.png");
        Penny = new ImageView(imgPenny);
        lblPennies = new Label("Pennies: ");
        Penny.setOnMouseClicked(event ->{
            intPennies++;
            dblValue = dblValue + 0.01;
            lblChange.setText("Value: $" + dblValue);
            lblPennies.setText("Pennies: " + intPennies);
        });
        //Button Count Change
        Button btnCountChange = new Button("Count Change");
        btnCountChange.setOnAction(event ->{
            if(dblValue >= 1)
            {
                JOptionPane.showMessageDialog(null, "You Win!");
            }
            else
            {
                String message = "You Lose!" + "\n\n" + "To win you have more than $1.00";
                JOptionPane.showMessageDialog(null, message);
            }
        });
        
        //Button Clear
        Button btnClear = new Button("Clear");
        btnClear.setOnAction(event ->{
            intPennies = 0;
            intNickels = 0;
            intDimes = 0;
            intQuarters = 0;
            dblValue = 0;
            
            lblPennies.setText("Pennies: ");
            lblNickels.setText("Nickels: ");
            lblDimes.setText("Dimes: ");
            lblQuarters.setText("Quarters: ");
            lblChange.setText("Value: ");
        });
        
        //Menu
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem itemSave = new MenuItem("Save");
        MenuItem itemLoad = new MenuItem("Load");
        menu.getItems().add(itemSave);
        menu.getItems().add(itemLoad);
                
        menuBar.getMenus().add(menu);
        //Save File
        itemSave.setOnAction(event ->{
            try
            {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Binary Files", "*.dat"));
                File saveFile = fileChooser.showSaveDialog(stage);
                FileOutputStream fstream = new FileOutputStream(saveFile);
                DataOutputStream outFile = new DataOutputStream(fstream);
                outFile.writeUTF(intPennies + "," + intNickels + "," +
                        intDimes + "," + intQuarters + "," + dblValue);
            }
            catch(Exception e)
            {
            JOptionPane.showMessageDialog(null,e.getMessage());
            }
        });
        //Load File
        itemLoad.setOnAction(event ->{
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Binary Files", "*.dat"));
                File loadFile = fileChooser.showOpenDialog(stage);
                FileInputStream fstream = new FileInputStream(loadFile);
                DataInputStream inFile = new DataInputStream(fstream);
                String strFile = inFile.readUTF();
                String[] strArray = strFile.split(",");
                intPennies = Integer.parseInt(strArray[0]);
                intNickels = Integer.parseInt(strArray[1]);
                intDimes = Integer.parseInt(strArray[2]);
                intQuarters = Integer.parseInt(strArray[3]);
                dblValue = Double.parseDouble(strArray[4]);
                
                lblPennies.setText("Pennies: " + intPennies);
                lblNickels.setText("Nickels: " + intNickels);
                lblDimes.setText("Dimes: " + intDimes);
                lblQuarters.setText("Quarters: " + intQuarters);
                lblChange.setText("Value: $" + dblValue);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
        
        //Layout
        GridPane gridCoins = new GridPane();
        gridCoins.add(Penny, 0, 0);
        gridCoins.add(Nickel, 1, 0);
        gridCoins.add(Dime, 2, 0);
        gridCoins.add(Quarter, 3, 0);
        gridCoins.add(lblPennies, 0, 1);
        gridCoins.add(lblNickels, 1, 1);
        gridCoins.add(lblDimes, 2, 1);
        gridCoins.add(lblQuarters, 3, 1);
        gridCoins.add(lblChange, 0, 2);
        gridCoins.add(btnCountChange, 2, 2);
        gridCoins.add(btnClear, 3, 2);
        
        VBox vboxGame = new VBox(menuBar,gridCoins);
        
        //Setting Scene
        Scene sceneGame = new Scene(vboxGame);
        stage.setTitle("Coin Game");
        stage.setScene(sceneGame);
        stage.show();
    }
}
