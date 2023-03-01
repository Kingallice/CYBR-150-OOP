/*
 Assignment: Assignment 2
       Name: Noah Mosel
    Project: Encryption/Decryption Program
 */
package assignment2;

import java.awt.Color;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.*;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

public class Assignment2 extends Application 
{
    public static void main(String[] args) {
        launch(args);
    }
    //Sets up program
    @Override
    public void start(Stage stage)
    {
        //Sets up the menu
        MenuBar menuBar = new MenuBar();
        
        //Menu File
        Menu menuFile = new Menu("_File");
        CustomMenuItem itemOpen = new CustomMenuItem(new Label("Open"));
        CustomMenuItem itemSave = new CustomMenuItem(new Label("Save"));
        CustomMenuItem itemExit = new CustomMenuItem(new Label("Exit"));
        SeparatorMenuItem itemSeparator = new SeparatorMenuItem();
        menuFile.getItems().add(itemOpen);
        menuFile.getItems().add(itemSave);
        menuFile.getItems().add(itemSeparator);
        menuFile.getItems().add(itemExit);
        Tooltip.install(itemOpen.getContent(), new Tooltip("Open encrypted file."));
        Tooltip.install(itemSave.getContent(), new Tooltip("Save encrypted message."));
        Tooltip.install(itemExit.getContent(), new Tooltip("Exit the application."));
        menuFile.setMnemonicParsing(true);
        
        //Menu Edit
        Menu menuEdit = new Menu("_Edit");
        CustomMenuItem itemColor = new CustomMenuItem(new Label("Color"));
        menuEdit.getItems().add(itemColor);
        Tooltip.install(itemColor.getContent(), new Tooltip("Change color of text."));
        menuEdit.setMnemonicParsing(true);
        
        //Menu Help
        Menu menuHelp = new Menu("_Help");
        CustomMenuItem itemHelp = new CustomMenuItem(new Label("View Help"));
        CustomMenuItem itemAbout = new CustomMenuItem(new Label("About"));
        menuHelp.getItems().add(itemHelp);
        menuHelp.getItems().add(itemAbout);
        Tooltip.install(itemHelp.getContent(), new Tooltip("Get help."));
        Tooltip.install(itemAbout.getContent(), new Tooltip("View licensing information."));
        menuHelp.setMnemonicParsing(true);
        
        menuBar.getMenus().add(menuFile);
        menuBar.getMenus().add(menuEdit);
        menuBar.getMenus().add(menuHelp);
        
        //Creates the text area
        TextArea txtInput = new TextArea();
        
        //Radio Buttons
        RadioButton rdbReverse = new RadioButton("Method 1");
        RadioButton rdbValueChange = new RadioButton("Method 2");
        ToggleGroup grpRadioButtons = new ToggleGroup();
        grpRadioButtons.getToggles().add(rdbReverse);
        grpRadioButtons.getToggles().add(rdbValueChange);
        HBox hboxRadioButtons = new HBox(new Label("Encryption Method:"),rdbReverse,rdbValueChange);
        hboxRadioButtons.setSpacing(10);
        
        //Setting up the GUI
        GridPane gridMain = new GridPane();
        gridMain.add(txtInput, 0, 0);
        gridMain.add(hboxRadioButtons, 0, 1);
        gridMain.setVgap(10);
        gridMain.setPadding(new Insets(5));
        
        VBox vboxMain = new VBox(menuBar,gridMain);
        
        //Exits the program
        itemExit.setOnAction(event ->{
            try 
            {
                System.exit(0);
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        });
        
        //Encrypts the message and saves it in a binary file
        itemSave.setOnAction(event ->{
            try
            {
                String strOut = "";
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add
                    (new FileChooser.ExtensionFilter("Binary Files", "*.dat"));
                File saveFile = fileChooser.showSaveDialog(stage);
                FileOutputStream fstream = new FileOutputStream(saveFile);
                DataOutputStream outFile = new DataOutputStream(fstream);
                if (rdbReverse.isSelected()) {
                    strOut = (reverseEncryption(txtInput.getText()));
                } else if (rdbValueChange.isSelected()) {
                    strOut = (ASCIIvalueEncryption(txtInput.getText()));
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose a method of encryption!");
                }
                outFile.writeUTF(strOut);
            }
            catch (FileNotFoundException e) 
            {
                JOptionPane.showMessageDialog(null, "Please select a file.");
            } catch (NullPointerException e) 
            {
                JOptionPane.showMessageDialog(null, "Please select a file.");
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        });
        
        //Opens a file and decrypts the messsage. Then, sets the txtInput text to the message
        itemOpen.setOnAction(event ->{
            try 
            {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary Files", "*.dat"));
                File openFile = fileChooser.showOpenDialog(stage);
                FileInputStream fstream = new FileInputStream(openFile);
                DataInputStream inFile = new DataInputStream(fstream);
                String strIn = inFile.readUTF();
                if (rdbReverse.isSelected()) {
                    txtInput.setText(reverseEncryption(strIn));
                } else if (rdbValueChange.isSelected()) {
                    txtInput.setText(ASCIIvalueDecryption(strIn));
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose a method of decryption!");
                }
            } 
            catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "Please select a file.");
            }
            catch (NullPointerException e)
            {
                JOptionPane.showMessageDialog(null, "Please select a file.");
            }
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        });
        
        //Changes the font color to the color the user selects in JColorChooser
        itemColor.setOnAction(event ->{
            JColorChooser colorChooser = new JColorChooser();
            Color colorSelected = JColorChooser.showDialog(null,"Select a color.",null);
            int intRed = colorSelected.getRed();
            int intGreen = colorSelected.getGreen();
            int intBlue = colorSelected.getBlue();
            
            txtInput.setStyle("-fx-text-fill: rgb(" + intRed + "," + intGreen + "," + intBlue + ");");
        });
        //Displays information on how to use the program
        itemHelp.setOnAction(event ->{
            JOptionPane.showMessageDialog(null,"Encryption:"+"\n"+
                    "1) Enter text into the text area."+"\n"+
                    "2) Select a method of encryption"+"\n"+
                    "3) Using File>Save, the mesage will be encrypted and saved in your choosen file"+"\n\n"+
                    "Decryption:"+"\n"+
                    "1) Select a method of encryption"+"\n"+
                    "2) Using File>Open, select a file and the message will be decrypted and placed in the text area.");
        });
        //Displays registration information
        itemAbout.setOnAction(event ->{
            JOptionPane.showMessageDialog(null, "Encryption Program" + "\n\n"+
                    "Company: Mosel Inc." + "\n"+
                    "Programmer: Noah Mosel");   
        });
        
        //Sets up the stage and shows it to the user
        Scene sceneMain = new Scene(vboxMain);
        stage.setTitle("Encryption");
        stage.setScene(sceneMain);
        stage.show();      
    }
    
    //Encrypts a string by adding 1 to every ASCII value
    public static String ASCIIvalueEncryption(String text)
    {
        String out = "";
        text = text.trim();
        try
        {
            byte[] byteStringArray = text.getBytes();
            int[] intValueArray = new int[byteStringArray.length];
            for(int i = byteStringArray.length - 1; i >= 0; i--)
            {
                intValueArray[i] = byteStringArray[i];
                intValueArray[i] = intValueArray[i] + 1;
            }
            for(int i = 0; i <= intValueArray.length - 1; i++)
            {
                out = out + Character.toString((char)intValueArray[i]);
            }   
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return out;
    }
    //Decrypts a string that was encrypted by adding 1 to the ASCII values
    public static String ASCIIvalueDecryption(String text) {
        String out = "";
        text = text.trim();
        try {
            byte[] byteStringArray = text.getBytes();
            int[] intValueArray = new int[byteStringArray.length];
            for (int i = byteStringArray.length - 1; i >= 0; i--) {
                intValueArray[i] = byteStringArray[i];
                intValueArray[i] = intValueArray[i] - 1;
            }
            for (int i = 0; i <= intValueArray.length - 1; i++) 
            {
                out = out + Character.toString((char) intValueArray[i]);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return out;
    }
    //returns the string passed, but rewrites words in the string backwards
    public static String reverseEncryption(String text)
    {
        String out = "";
        text = text.trim();
        try
        {
            String[] strWordArray = text.split(" ");
            
            for(int i=0; i < strWordArray.length; i++)
            {
                String strWord = strWordArray[i];
                char[] charArray = strWord.toCharArray();
                for(int j = (charArray.length - 1); j >= 0; j--)
                {
                    out = out + charArray[j];
                }
                out = out + " ";
            }
        }
        catch(Exception e)
        {
        JOptionPane.showMessageDialog(null, e.toString());
        }
        return out;
    }   
}
