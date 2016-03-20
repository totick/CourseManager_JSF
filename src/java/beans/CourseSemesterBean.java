/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static com.sun.faces.context.flash.ELFlash.getFlash;
import ejb.ICourseSemesterService;
import ejb.IStudentService;
import ejb.ITeacherService;
import entities.CourseSemester;
import entities.Student;
import entities.Teacher;
import javax.ejb.EJB;
import java.util.*;
import javax.faces.bean.*;

/**
 *
 * @author User
 */
@ManagedBean(name = "semesterBean")
@ViewScoped
public class CourseSemesterBean {

    @EJB
    private ICourseSemesterService semesterService;
    @EJB
    private IStudentService studentService;
    @EJB
    private ITeacherService teacherService;
    
    private CourseSemester semester;

    private List<String> selectedStudents = new ArrayList<>();
    private List<String> selectedTeachers = new ArrayList<>();

    public List<Student> getSubscribedStudents() {
        if (this.getSemester() != null && this.getSemester().getStudents() != null) {
            return this.getSemester().getStudents();
        }
        return new ArrayList<Student>();
    }
    
    public List<Teacher> getSubscribedTeachers(){
        if(this.getSemester() != null && this.getSemester().getTeachers() != null){
            return this.getSemester().getTeachers();
        }
        return new ArrayList<Teacher>();
    }

    public List<String> getSelectedTeachers() {
        return selectedTeachers;
    }

    public void setSelectedTeachers(List<String> selectedTeachers) {
        this.selectedTeachers = selectedTeachers;
    }

    public List<String> getSelectedStudents() {
        return selectedStudents;
    }

    public void setSelectedStudents(List<String> selectedStudents) {
        this.selectedStudents = selectedStudents;
    }

    public CourseSemester getSemester() {
        if (semester == null) {
            semester = (CourseSemester) getFlash().get("currentSemester");
        }
        return semester;
    }

    public void setSemester(CourseSemester semester) {
        this.semester = semester;
    }

    public List<Student> getUnsubscribedStudents() {
        //Making sure not to send an empty list to the service, otherwise it causes an SQLException (wrong sql-syntax would be generated)
        if(this.getSubscribedStudents() != null && this.getSubscribedStudents().size() > 0){
            return semesterService.getUnsubscribedStudents(this.getSubscribedStudents());          
        }
        return studentService.allStudents();
    }

    public List<Teacher> getUnsubscribedTeachers() {
        //Making sure not to send an empty list to the service, otherwise it causes an SQLException (wrong sql-syntax would be generated)
        if(this.getSubscribedTeachers() != null && this.getSubscribedTeachers().size() > 0){
            return semesterService.getUnsubscribedTeachers(this.getSubscribedTeachers());    
        }
        return teacherService.allTeachers();
    }

    public String addSelectedStudents() {
        //The instance of semester gets updated (pass by reference).
        semesterService.subscribeStudents(this.getSemester(), selectedStudents);
        selectedStudents.clear();
        return null;
    }

    public String addSelectedTeachers() {
        //The instance of semester gets updated (pass by reference).
        semesterService.subscribeTeachers(this.getSemester(), selectedTeachers);
        selectedTeachers.clear();
        return null;
    }

    public String removeTeacher(Teacher teacher) {
        this.getSemester().getTeachers().remove(teacher);
        semesterService.saveCourseSemester(this.getSemester());        
        return null;
    }

    public String removeStudent(Student student) {
        this.getSemester().getStudents().remove(student);
        semesterService.saveCourseSemester(this.getSemester());
        return null;
    }
    
    public String getTeacherLabel(Teacher teacher){
        return teacher.getId() + ": " + teacher.getFirstName() + " " + teacher.getLastName();
    }
    
    public String getStudentLabel(Student student){
        return student.getId() + ": " + student.getFirstName() + " " + student.getLastName();
    }
}
