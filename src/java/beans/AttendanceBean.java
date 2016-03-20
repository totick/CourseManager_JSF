/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import classviews.AbsenceCheckboxView;
import ejb.ICourseSemesterService;
import ejb.IStudentService;
import entities.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;
import javax.faces.bean.*;
import java.util.*;
import javax.faces.component.UIInput;

/**
 *
 * @author John
 */
@ManagedBean(name = "attendanceBean")
@ViewScoped
public class AttendanceBean {

    @EJB
    private ICourseSemesterService semesterService;
    @EJB
    private IStudentService studentService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private CourseSemester semester;
    private String searchSemesterCode;
    private Map<Integer, AbsenceCheckboxView> absenceList;
    private Date searchSemesterDate;

    public Date getSearchSemesterDate() {
        return searchSemesterDate;
    }

    public void setSearchSemesterDate(Date searchSemesterDate) {
        this.searchSemesterDate = searchSemesterDate;
    }

    public Map<Integer, AbsenceCheckboxView> getAbsenceList() {
        return absenceList;
    }

    public void setAbsenceList(Map<Integer, AbsenceCheckboxView> absenceList) {
        this.absenceList = absenceList;
    }

    public String getSearchSemesterCode() {
        return searchSemesterCode;
    }

    public void setSearchSemesterCode(String searchSemesterCode) {
        this.searchSemesterCode = searchSemesterCode;
    }

    public CourseSemester getSemester() {
        return semester;
    }

    public void setSemester(CourseSemester semester) {
        this.semester = semester;
    }

    public List<Student> getSemesterStudents() {
        if (this.semester != null && this.semester.getStudents().size() > 0) {
            return this.semester.getStudents();
        }
        return new ArrayList<Student>();
    }

    private Absence getStudentAbsence(Student student) {
        for (Absence abs : student.getAbsenceList()) {
            if (sdf.format(searchSemesterDate).equals(abs.getAbsenceDate())) {
                return abs;
            }
        }
        return null;
    }

    public void changeAttendance(int studentId, UIInput input) {
        boolean isAbsence = (boolean) input.getValue();
        AbsenceCheckboxView view = this.absenceList.get(studentId);
        Student student = view.getStudent();

        if (isAbsence) {
            Absence newAbsence = new Absence();
            newAbsence.setAbsenceDate(searchSemesterDate);
            newAbsence.setStudent(student);
            newAbsence.setSemester(this.semester);
            Absence savedAbs = this.studentService.addAbsence(newAbsence);
            student.getAbsenceList().add(savedAbs);
            //studentService.addAbsence(newAbsence);
            //this.studentService.saveStudent(student);
        } else {
            Absence abs = this.getStudentAbsence(student);
            student.getAbsenceList().remove(abs);

            this.studentService.removeAbsence(student.getId(), this.semester.getId(), this.searchSemesterDate);
        }

        //this.semesterService.saveCourseSemester(this.semester);
    }

    public String searchSemester() throws ParseException {
        this.absenceList = new HashMap<>();

        this.semester = semesterService.getSemester(searchSemesterCode);
        

        //TODO: create error message
        if (this.semester == null) {
            return null;
        }

        for (Student student : this.semester.getStudents()) {
            this.absenceList.put(student.getId(), new AbsenceCheckboxView(studentService, this.semester, student, this.searchSemesterDate));
        }
        return null;
    }

}
