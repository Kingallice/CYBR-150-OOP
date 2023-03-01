/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.lab2;

import javax.swing.JOptionPane;

/**
 *
 * @author kinga
 */
public class Test {
    
    public static void main(){
        System.out.println("Two circles will be created using information you provide. Information about the cricles will then be reported.");
        TestCircles();
    }
    public static void TestCircles(){
        Circle Circle1 = null, Circle2 = null;
        double Rad;
        double XCoord;
        double YCoord;
        System.out.println("Circle 1:");
        System.out.println("1.Use default circle.");
        System.out.println("2.Specify radius.");
        System.out.println("3.Specify radius and coordinates.");
        String strMethod1 = JOptionPane.showInputDialog(null, "How would you like to define Circle 1?");
        int C1 = Integer.parseInt(strMethod1);
        switch (C1){
            case 1:{
                Circle1 = new Circle();
                break;
            }
            case 2:{
                Rad = Double.parseDouble(JOptionPane.showInputDialog("Input the radius."));
                Circle1 = new Circle(Rad);
                break;
            }
            case 3:{
                Rad = Double.parseDouble(JOptionPane.showInputDialog("Input the radius."));
                XCoord = Double.parseDouble(JOptionPane.showInputDialog("Input the X coordinate."));
                YCoord = Double.parseDouble(JOptionPane.showInputDialog("Input the Y cordinate"));
                Circle1 = new Circle(XCoord, YCoord, Rad);
                break;
            }
        }
        String strMethod2 = JOptionPane.showInputDialog(null, "How would you like to define Circle 2?");
        int C2 = Integer.parseInt(strMethod2);
        switch (C2){
            case 1:{
                Circle2 = new Circle();
                break;
            }
            case 2:{
                Rad = Double.parseDouble(JOptionPane.showInputDialog("Input the radius."));
                Circle2 = new Circle(Rad);
                break;
            }
            case 3:{
                Rad = Double.parseDouble(JOptionPane.showInputDialog("Input the radius."));
                XCoord = Double.parseDouble(JOptionPane.showInputDialog("Input the X coordinate."));
                YCoord = Double.parseDouble(JOptionPane.showInputDialog("Input the Y cordinate"));
                Circle2 = new Circle(XCoord, YCoord, Rad);
                break;
            }
        }
        System.out.println("Circle 1:");
        System.out.println("X coordinate: " + Circle1.getCordX());
        System.out.println("Y coordinate: " + Circle1.getCordY());
        System.out.println("Radius: " + Circle1.getRadius());
        System.out.println("Area: " + Circle1.getArea());
        System.out.println("Circumference: " + Circle1.getCircumference());
        System.out.println();
        System.out.println("Circle 2:");
        System.out.println("X coordinate: " + Circle2.getCordX());
        System.out.println("Y coordinate: " + Circle2.getCordY());
        System.out.println("Radius: " + Circle2.getRadius());
        System.out.println("Area: " + Circle2.getArea());
        System.out.println("Circumference: " + Circle2.getCircumference());
        
        if(Circle1.getArea()==Circle2.getArea()){
            System.out.println("The area of the circles are equal.");
        }
        else if(Circle1.getArea()>=Circle2.getArea()){
            System.out.println("The area of Circle 1 is greater than the are of Circle 2.");
        }
        else{
            System.out.println("The area of Circle 2 is greater than the are of Circle 1.");
        }
    }
}