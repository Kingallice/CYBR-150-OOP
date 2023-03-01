/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.lab3;

public class Employee implements EmployeeRules{
    
    private String strEmployeeName;
    private double decSalary;
    
    public Employee(){
        strEmployeeName = "";        
        decSalary = 0;
    }
    public Employee(String Name, double Salary){
        strEmployeeName = Name;
        decSalary = Salary;
    }
    public String toString(){
        String strOut = strEmployeeName + " has a salary of $" + decSalary;
        return strOut;
    }
    
    @Override
    public String getName(){
        return strEmployeeName;
    }
    @Override 
    public double getSalary(){
        return decSalary;
    }
}
