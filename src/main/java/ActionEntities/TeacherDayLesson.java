package ActionEntities;

import Entities.Mark;
import ServiceEntities.SchedulePupilLesson;
import ServiceEntities.ScheduleTeacherLesson;

/**
 * Created by Артем on 23.05.2016.
 */
public class TeacherDayLesson {
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

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(int auditorium) {
        this.auditorium = auditorium;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    private int lessonID;
    private int number;
    private String timeRange;
    private String subject;
    private int auditorium;
    private String homework;

    public String getClassLetter() {
        return classLetter;
    }

    public void setClassLetter(String classLetter) {
        this.classLetter = classLetter;
    }

    public int getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(int classGrade) {
        this.classGrade = classGrade;
    }

    private int classGrade;
    private String classLetter;

    public TeacherDayLesson(ScheduleTeacherLesson stl) {
        lessonID = stl.getLesson().getID();
        number = stl.getLesson().getScheduleNumber();
        subject = stl.getSubject().getName();
        auditorium = stl.getLesson().getRoom();
        homework = stl.getLesson().getHomework();
        timeRange = TimeOfLesson.NumberToTime(number);
        classGrade = stl.getCls().getGrade();
        classLetter = stl.getCls().getLetter();
    }
}
