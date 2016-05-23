package ActionEntities;

import Entities.Mark;
import ServiceEntities.SchedulePupilLesson;

import java.sql.Time;

/**
 * Created by Артем on 23.05.2016.
 */

public class PupilDayLesson {

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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    private int lessonID;
    private int number;
    private String timeRange;
    private String teacher;
    private String subject;
    private int auditorium;
    private String homework;
    private String mark;

    public PupilDayLesson(SchedulePupilLesson spl, Mark _mark) {
        lessonID = spl.getLesson().getID();
        number = spl.getLesson().getScheduleNumber();
        teacher = spl.getTeacher().getSurname() + " " + spl.getTeacher().getName();
        subject = spl.getSubject().getName();
        auditorium = spl.getLesson().getRoom();
        homework = spl.getLesson().getHomework();
        timeRange = TimeOfLesson.NumberToTime(number);
        if (_mark.getMark() < 0)
            mark = "absent";
        else
            mark = String.valueOf(_mark.getMark());
    }
}
