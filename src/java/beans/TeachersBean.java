/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import ejb.ITeacherService;
import entities.*;
import javax.faces.bean.*;
import java.util.*;
import javax.ejb.EJB;

/**
 *
 * @author John
 */
@ManagedBean(name = "teachersBean")
/**
 * With ViewScoped the bean lives as long as user is interacting with the same JSF view in the browser window/tab.
 * It gets created upon a HTTP request and get destroyed once user postback to a different view.
 */
@ViewScoped
public class TeachersBean {
    
    @EJB
    ITeacherService teacherService;
    
    private Person newTeacher;
    private Person currentTeacher;
    
    public TeachersBean(){
        newTeacher = new Teacher();
        currentTeacher = new Teacher();
    }
  

    public Person getNewTeacher() {
        return newTeacher;
    }

    public void setNewTeacher(Person newTeacher) {
        this.newTeacher = newTeacher;
    }

    public Person getCurrentTeacher() {
        return currentTeacher;
    }

    public void setCurrentTeacher(Person currentTeacher) {
        this.currentTeacher = currentTeacher;
    }
    
    public List<Teacher> getAllTeachers(){
        List<Teacher> teachers = teacherService.allTeachers();
        return teachers;
    }
    
    public String addNewTeacher(){
        teacherService.saveTeacher((Teacher)newTeacher);
        newTeacher = new Teacher();
        return null;
    }
    
    public String editTeacher(){
        Teacher teacher = teacherService.saveTeacher((Teacher)currentTeacher);
        currentTeacher = new Teacher();
        return null;
    }
    
    public String removeTeacher(int id){
        teacherService.removeTeacher(id);
        return null;
    }
}
