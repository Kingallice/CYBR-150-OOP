/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.lab1;

import javax.swing.JOptionPane;

public class OOPLab1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        int HIGH = 100;
        int LOW = 0;
        
        int n1 = Integer.parseInt(JOptionPane.showInputDialog("Enter a value within the range: [" + LOW + ", " + HIGH + "]"));
        
        while (n1 < LOW || n1 > HIGH)
        {
            JOptionPane.showMessageDialog(null, "Input out of range! Please input a value within the range: [" + LOW + ", " + HIGH + "]");
            n1 = Integer.parseInt(JOptionPane.showInputDialog("Enter a value within the range: [" + LOW + ", " + HIGH + "]"));
        }
        
        int n2 = Integer.parseInt(JOptionPane.showInputDialog("Enter a value within the range: [" + n1 + ", " + (n1 * 5) + "]"));
        
        if (n2 < (n1) || n2 > (n1 * 5)){
            JOptionPane.showMessageDialog(null, "Input was out of range! Instead I will use: " + (n1 * 2));
            n2 = n1 * 2;
        }
        int sum = sumMultiplesOf5(n1,n2);
        printAnswers (n1, n2, sum);
        
    }
    
    static int sumMultiplesOf5 (int n1, int n2){
        int sum = 0;
        
        for(int i = n1; i <= n2; i++){
        if ((i % 5) == 0){
            sum += i;
        }
    }
        
        return sum;
    }
    
    static void printAnswers (int n1, int n2, int sumMultiplesOf5){
        System.out.println("The sum of multiples of five within the range of "
                + "[" + n1 + ", " + n2 + "] is " + sumMultiplesOf5);
    }
}
