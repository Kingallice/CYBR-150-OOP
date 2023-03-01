/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.lab2;

public class Circle {
    
    private double xCord;
    private double yCord;
    private double intRadius;
    
    public Circle(){
        xCord = 0;
        yCord = 0;
        intRadius = 1;
    }
    public Circle(double Rad){
        xCord = 0;
        yCord = 0;
        intRadius = Rad;
    }
    public Circle(double x, double y, double Rad){
        xCord = x;
        yCord = y;
        intRadius = Rad;
    }
    public double getCordX(){
        return xCord;
    }
    public double getCordY(){
        return yCord;
    }
    public double getRadius(){
        return intRadius;
    }
    public double getArea(){
        double Area;
        Area = intRadius*intRadius*3.14159;
        return Area;
    }
    public double getCircumference(){
        double Circ;
        Circ = (intRadius*2)*3.14159;
        return Circ;
    }
}
