/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import ejb.ICourseSemesterService;
import ejb.ICourseService;
import ejb.ITeacherService;
import entities.Course;
import entities.CourseSemester;
import entities.Teacher;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

/**
 *
 * @author John
 */
@ManagedBean(name = "coursesBean")
@ViewScoped
public class CoursesBean {

    @EJB
    ICourseService courseService;
    @EJB
    ITeacherService teacherService;
    @EJB
    ICourseSemesterService semesterService;

    private Course newCourse;
    private Course currentCourse;
    //Id for the course responsible. failed using Converter for the responsible list
    private int courseResponsibleId;
    private boolean semestersInfoPanel = false;
    private CourseSemester currentSemester;
    private CourseSemester newSemester;

    public boolean isShowSemestersInfoPanel() {
        return this.semestersInfoPanel;
    }

    public int getCourseResponsibleId() {
        return courseResponsibleId;
    }

    public void setCourseResponsibleId(int courseResponsibleId) {
        this.courseResponsibleId = courseResponsibleId;
    }

    public CoursesBean() {
        this.newCourse = new Course();
        this.currentCourse = new Course();
        this.currentSemester = new CourseSemester();
        this.newSemester = new CourseSemester();
    }

    public List<Teacher> getAvailableCourseResponsibles() {
        return teacherService.allTeachers();
    }

    public ICourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(ICourseService courseService) {
        this.courseService = courseService;
    }

    public Course getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(Course newCourse) {
        this.newCourse = newCourse;
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(Course currentCourse) {
        this.courseResponsibleId = currentCourse.getCourseResponsible().getId();
        this.currentCourse = currentCourse;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = courseService.allCourses();
        return courses;
    }

    public String addNewCourse() {
        Teacher teacher = teacherService.getTeacherById(courseResponsibleId);
        newCourse.setCourseResponsible(teacher);
        Course course = courseService.saveCourse(newCourse);
        newCourse = new Course();
        return null;
    }

    public String editCourse() {
        Teacher teacher = teacherService.getTeacherById(courseResponsibleId);
        currentCourse.setCourseResponsible(teacher);
        Course course = courseService.saveCourse(currentCourse);
        currentCourse = new Course();
        return null;
    }

    public String removeCourse(int id) {
        courseService.removeCourse(id);
        return null;
    }

    public void showCourseSemestersInfoPanel() {
        this.semestersInfoPanel = true;
    }

    public void hideCourseSemestersInfoPanel() {
        this.semestersInfoPanel = false;
    }

    public CourseSemester getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(CourseSemester currentSemester) {
        this.currentSemester = currentSemester;
    }

    public CourseSemester getNewSemester() {
        return newSemester;
    }

    public void setNewSemester(CourseSemester newSemester) {
        this.newSemester = newSemester;
    }

    public String addSemester() {
        newSemester.setCourse(currentCourse);
        CourseSemester savedSemester = semesterService.saveCourseSemester(newSemester);
        currentCourse.getSemesters().add(savedSemester);
        newSemester = new CourseSemester();
        return null;
    }
    
    public List<CourseSemester> getCurrentSemesters(){
        List<CourseSemester> results = semesterService.getSemesters(currentCourse.getId());
        return results;
    }
    
    public String removeSemester(int semesterId){
        semesterService.removeSemester(semesterId);
        return null;
    }
    
    public String editCourseSemester(){
        CourseSemester editedSemester = semesterService.saveCourseSemester(currentSemester);
        return null;
    }
    
    public String viewCourseSemester(CourseSemester semester){
        //this.semesterBean.setSemester(semester);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSemester", semester);
        return "semester";
    }
    
}
