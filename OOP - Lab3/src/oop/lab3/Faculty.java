/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.lab3;


public class Faculty extends Employee{
    private String[] Courses;
    
    public Faculty(String Name, double Salary, String[] CourseList){
        super(Name, Salary);
        Courses = CourseList.clone();
    }
    public Faculty(){
        super();
        Courses = new String[0];
    }
    public String toString(){
        String out = getName() + " has a salary of $" + getSalary() + " and teaches " + getCourseNames();
        return out;
    }
    
    @Override
    public String getName(){
        String out = "Professor " + super.getName();
        return out;
    }
    public String[] getCourses(){
        return Courses.clone();
    }
    public void setCourses(String[] CourseArray){
        Courses = CourseArray.clone();
    }
    public String getCourseNames(){
        int NumOfCourses = Courses.length;
        String CourseNames = Courses[0];
        
        for (int i = 1; i < NumOfCourses; i++) {
            CourseNames += ", " + Courses[i];
        }
        return CourseNames;
    }
    
}
