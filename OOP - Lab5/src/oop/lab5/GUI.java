/*
  Project: Calculator with GUI
     Name: Noah Mosel   
      Lab: Lab 5
*/
package oop.lab5;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.event.*;

public class GUI extends Application {
    Label lblOutResult;
    TextField txtNumber1;
    TextField txtNumber2;
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Calculator");
        stage.setMinHeight(175);
        stage.setMinWidth(500);
        
        Label lblNumber1 = new Label("Number 1:");
        txtNumber1 = new TextField();
        Label lblNumber2 = new Label("Number 2:");
        txtNumber2 = new TextField();
        Button btnAdd = new Button("+");
        Button btnSubtract = new Button("-");
        Button btnMultiply = new Button("*");
        Button btnDivide = new Button("/");
        Label lblResult = new Label("Result:");
        lblOutResult = new Label("");
        
        
        GridPane gridControls = new GridPane();
        gridControls.setPadding(new Insets(10));
        gridControls.setHgap(10);
        gridControls.setVgap(20);
        
        gridControls.add(lblNumber1,0,0);
        gridControls.add(txtNumber1,1,0);
        gridControls.add(lblNumber2,2,0);
        gridControls.add(txtNumber2,3, 0);
        gridControls.add(btnAdd, 0, 1);
        gridControls.add(btnSubtract, 1, 1);
        gridControls.add(btnMultiply, 2, 1);
        gridControls.add(btnDivide, 3, 1);
        gridControls.add(lblResult,0,2);
        gridControls.add(lblOutResult,1,2);
        
        btnAdd.setOnAction(new AddHandle());
        btnSubtract.setOnAction(new SubtractHandle());
        btnMultiply.setOnAction(new MultiplyHandle());
        btnDivide.setOnAction(new DivideHandle());
        
        Scene mainScene = new Scene(gridControls);
        stage.setScene(mainScene);
        stage.show();
    }
    class AddHandle implements EventHandler<ActionEvent>{    
        @Override    
        public void handle(ActionEvent event){
            String str1 = txtNumber1.getText();
            String str2 = txtNumber2.getText();
            double num1, num2, out;
            num1 = Double.parseDouble(str1);
            num2 = Double.parseDouble(str2);

            out = num1 + num2;
            lblOutResult.setText("" + out);
        }
    }
    class SubtractHandle implements EventHandler<ActionEvent>{    
        @Override    
        public void handle(ActionEvent event){
            String str1 = txtNumber1.getText();
            String str2 = txtNumber2.getText();
            Double num1, num2, out;
            num1 = Double.parseDouble(str1);
            num2 = Double.parseDouble(str2);

            out = num1 - num2;
            lblOutResult.setText("" + out);
        }
    }
    class MultiplyHandle implements EventHandler<ActionEvent>{    
        @Override    
        public void handle(ActionEvent event){
            String str1 = txtNumber1.getText();
            String str2 = txtNumber2.getText();
            Double num1, num2, out;
            num1 = Double.parseDouble(str1);
            num2 = Double.parseDouble(str2);

            out = num1 * num2;
            lblOutResult.setText("" + out);
        }
    }
    class DivideHandle implements EventHandler<ActionEvent>{    
        @Override    
        public void handle(ActionEvent event){
            String str1 = txtNumber1.getText();
            String str2 = txtNumber2.getText();
            Double num1, num2, out;
            num1 = Double.parseDouble(str1);
            num2 = Double.parseDouble(str2);
            
            if(num2 == 0)
            {
                lblOutResult.setText("Can not divide by zero!");
            }
            else
            {
                out = num1 / num2;
                lblOutResult.setText("" + out);
            }
        }
    }
}


