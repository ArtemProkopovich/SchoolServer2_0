package ActionEntities;

import Entities.Lesson;
import Entities.Subject;
import Entities.Teacher;

/**
 * Created by Артем on 25.05.2016.
 */
public class ScheduleClassLesson {
    private int lessonID;
    private int number;
    private int auditorium;
    private int subjectID;
    private String subject;
    private int teacherID;
    private String teacher;

    public ScheduleClassLesson(Lesson lesson, Subject subject, Teacher teacher) {
        lessonID = lesson.getID();
        number = lesson.getScheduleNumber();
        auditorium = lesson.getRoom();
        subjectID = subject.getID();
        this.subject = subject.getName();
        if (teacher != null) {
            teacherID = teacher.getID();
            this.teacher = teacher.getSurname() + " " + teacher.getName();
        }
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(int auditorium) {
        this.auditorium = auditorium;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
