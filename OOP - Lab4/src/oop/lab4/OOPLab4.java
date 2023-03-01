/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.lab4;

import java.io.*;

public class OOPLab4 {

    public static void main(String[] args) {
        // TODO code application logic here
        FileInputStream fstreamBinary, fstreamCSV;
        DataInputStream inputFileBinary, inputFileCSV;
        
        try
        {
            fstreamBinary = new FileInputStream("..\\OOP - Lab4\\src\\oop\\lab4\\doubles.dat");
            inputFileBinary = new DataInputStream(fstreamBinary);
            
            //Reads the binary file
            String strBinary = ReadBinary(inputFileBinary);
            //Creates array using string
            double[] dblBinaryArray = CreateArray(strBinary);
            //Calculates the average of the double array
            double BinaryAvg = getAverage(dblBinaryArray);
            
            fstreamCSV = new FileInputStream ("..\\OOP - Lab4\\src\\oop\\lab4\\doubles.csv");
            inputFileCSV = new DataInputStream(fstreamCSV);
            
            String strCSV = inputFileCSV.readLine();
            double[] dblCSVArray = CreateArray(strCSV);
            double CSVAvg = getAverage(dblBinaryArray);
            
            String str = " The average value of doubles.dat is " + BinaryAvg + 
                    "\n The average value of doubles.csv is " + CSVAvg;
            System.out.println(str);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    } 
    
    public static double getAverage(double[] dblArray)
    {
        double dblAverage = 0;
        double sum = 0;
        int numTerms = 0;
        
        int ArrayLen = dblArray.length;
        for(int i = 0; i < ArrayLen; i++)
        {
            sum = sum + dblArray[i];
            numTerms++;
        }
        dblAverage = sum / numTerms;
        
        return dblAverage;
    }
    
    public static String ReadBinary(DataInputStream inFile){
        boolean endOfFile = false;
        String out = ""; 
        while(!endOfFile)
        {
            try
            {
                out = out + inFile.readDouble()+ ",";
            }
            catch (EOFException e)
            {
                endOfFile = true;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return out;
    }
    
    public static double[] CreateArray (String strIn)
    {
        int ArraySize = getArraySize(strIn);
        double[] dblArray = new double[ArraySize];        
        String[] strArray = strIn.split(",");
        
        int strArrayLen = strArray.length;
        for(int i = 0; i < strArrayLen; i++)
        {
            dblArray[i] = Double.parseDouble(strArray[i]);
        }
        return dblArray;
    }
    
    public static int getArraySize(String strIn)
    {
        boolean runLoop = true;
        int out = 0;
        
        while (runLoop)
        {
            int index = strIn.indexOf(",");
            
            if(index == -1)
            {
                runLoop = false;
            }
            else
            {
                strIn = strIn.substring(index, strIn.length()-1);
                //System.out.print(strIn);
                out++;
            }
        }
        //System.out.println(out);
        return out;
    }
}
