/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classviews;

import ejb.IStudentService;
import entities.Absence;
import entities.CourseSemester;
import entities.Student;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public class AbsenceCheckboxView {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private boolean absence;
    private Student student;

    public AbsenceCheckboxView(Student s, Date absenceDate) {
        for (Absence abs : s.getAbsenceList()) {
            if (sdf.format(abs.getAbsenceDate()).equals(sdf.format(absenceDate))) {
                this.absence = true;
            }
        }
        this.student = s;
    }

    public AbsenceCheckboxView(IStudentService service, CourseSemester cs, Student s, Date absenceDate) {
        List<Absence> absenceList = service.getAbsenceList(s.getId(), cs.getId());
        for (Absence abs : absenceList) {
            if (sdf.format(abs.getAbsenceDate()).equals(sdf.format(absenceDate))) {
                this.absence = true;
            }
        }
        this.student = s;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isAbsence() {
        return absence;
    }

    public void setAbsence(boolean absence) {
        this.absence = absence;
    }

}
