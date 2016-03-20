/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import ejb.IStudentService;
import entities.*;
import enums.Gender;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.*;

/**
 *
 * @author John
 */
@ManagedBean(name = "studentsBean")
@ViewScoped
public class StudentsBean implements Serializable {

    @EJB
    private IStudentService studentService;

    private Student newStudent;
    private Student currentStudent;

    public StudentsBean() {
        newStudent = new Student();
        currentStudent = new Student();
    }

    public Student getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(Student newStudent) {
        this.newStudent = newStudent;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public List<Student> getAllStudents() {
        List<Student> students = studentService.allStudents();
        return students;
    }

    public String addNewStudent() {
        Student student = studentService.saveStudent((Student) newStudent);
        newStudent = new Student();
        return null;
    }
    
    public String editStudent(){
        Student student = studentService.saveStudent((Student)currentStudent);
        currentStudent = new Student();
        return null;
    }

    public String removeStudent(int id) {
        studentService.removeStudent(id);
        return null;
    }
}
