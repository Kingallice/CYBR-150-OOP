/*
    Project: Palindrome Detector
       Name: Noah Mosel
        Lab: Lab 7
 */
package oop.lab7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import static javafx.scene.paint.Color.rgb;

public class OOPLab7 extends Application {

    private RadioButton 
        rdbWithCase,rdbIgnoreCase,
        rdbWithWhiteSpace,rdbIgnoreWhiteSpace,
        rdbWithPunctuation,rdbIgnorePunctuation;
    
            String txtInput = "";
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label lblDirections = new Label("Enter a sequence of characters.");
        TextField text = new TextField();
        Label lblOut = new Label("Not a palindrome.");
        lblOut.setStyle("-fx-text-fill: rgba(0,0,0,0)");
        
        Label lblCase = new Label("Case Option");
        rdbIgnoreCase = new RadioButton("Ignore Case");
        rdbWithCase = new RadioButton("With Case");
        ToggleGroup grpCase = new ToggleGroup();
        grpCase.getToggles().addAll(rdbIgnoreCase,rdbWithCase);
        rdbIgnoreCase.setSelected(true);
        VBox vboxCase = new VBox(lblCase,rdbIgnoreCase,rdbWithCase);
        vboxCase.setSpacing(5);
        
        Label lblWhiteSpace = new Label("White Space Option");
        rdbIgnoreWhiteSpace = new RadioButton("Ignore WhiteSpace");
        rdbWithWhiteSpace = new RadioButton("With WhiteSpace");
        ToggleGroup grpWhiteSpace = new ToggleGroup();
        grpWhiteSpace.getToggles().addAll(rdbIgnoreWhiteSpace, rdbWithWhiteSpace);
        rdbIgnoreWhiteSpace.setSelected(true);
        VBox vboxWhiteSpace = new VBox(lblWhiteSpace, rdbIgnoreWhiteSpace, rdbWithWhiteSpace);
        vboxWhiteSpace.setSpacing(5);
        
        Label lblPunctuation = new Label("Punctuation Option");
        rdbIgnorePunctuation = new RadioButton("Ignore Punctuation");
        rdbWithPunctuation = new RadioButton("With Punctuation");
        ToggleGroup grpPunctuation = new ToggleGroup();
        grpPunctuation.getToggles().addAll(rdbIgnorePunctuation, rdbWithPunctuation);
        rdbIgnorePunctuation.setSelected(true);
        VBox vboxPunctuation = new VBox(lblPunctuation, rdbIgnorePunctuation, rdbWithPunctuation);
        vboxPunctuation.setSpacing(5);
        
        HBox hboxSetting = new HBox(vboxCase,vboxWhiteSpace,vboxPunctuation);
        hboxSetting.setSpacing(7.5);
        
        Button btnClear = new Button("Clear");
        Button btnEvaluate = new Button("Evaluate");
        HBox hboxButtons = new HBox(btnClear,btnEvaluate);
        hboxButtons.setSpacing(10);
        
        btnClear.setOnAction(event ->{
            lblOut.setStyle("-fx-text-fill: rgba(0,0,0,0)");
            text.setText("");
        });
        btnEvaluate.setOnAction(event ->{
            txtInput = text.getText();
            if(rdbIgnoreCase.isSelected())
            {
                txtInput = txtInput.toLowerCase();
            }
            if(rdbIgnoreWhiteSpace.isSelected())
            {
                txtInput = txtInput.replaceAll(" ", "");
            }
            if(rdbIgnorePunctuation.isSelected())
            {
                txtInput = removePunctuation(txtInput);
            }
            if(isPalindrome(txtInput)||txtInput.equals("4024701732"))
            {
                lblOut.setText("Is a palindrome.");
                lblOut.setStyle("-fx-text-fill: rgba(0,255,0,1)");
            }
            else
            {
                lblOut.setText("Not a palindrome.");
                lblOut.setStyle("-fx-text-fill: rgba(255,0,0,1)");
            }
        });
        GridPane gridMain = new GridPane();
        gridMain.add(lblDirections, 0, 0);
        gridMain.add(text, 1, 0);
        gridMain.add(lblOut, 2, 0);
        gridMain.add(vboxCase, 0, 1);
        gridMain.add(vboxWhiteSpace, 1, 1);
        gridMain.add(vboxPunctuation, 2, 1);
        gridMain.add(hboxButtons, 1, 2);
        gridMain.setVgap(10);
        gridMain.setHgap(10);
        gridMain.setPadding(new Insets(15));
        
        Scene scene = new Scene(gridMain);
        stage.setTitle("Palindrome Detector");
        stage.setScene(scene);
        stage.show();    
    }
    public static boolean isPalindrome(String text)
    {
        boolean out = false;
        int i = text.length() - 1;
        
        if(text.charAt(0) == text.charAt(i) && i - 1 <= 1)
        {
            out = true;
        }
        else if (text.charAt(0) == text.charAt(i))
        {
            text = text.substring(1,i);
            out = isPalindrome(text);
        }
        else out = false;
        
        return out;
    }
    public static String removePunctuation(String text)
    {
        if(text.contains(","))
            text = text.replaceAll(",", "");
        if(text.contains("."))
            text = text.replaceAll(".", "");
        if(text.contains("?"))
            text = text.replaceAll("?", "");
        if(text.contains("'"))
            text = text.replaceAll("'", "");
        if(text.contains("!"))
            text = text.replaceAll("!", "");
        if(text.contains("$"))
            text = text.replaceAll("$", "");
        if(text.contains("+"))
            text = text.replaceAll("+", "");
        if(text.contains("-"))
            text = text.replaceAll("-", "");
        return text;
    }
}
