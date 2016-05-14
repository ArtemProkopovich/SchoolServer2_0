package Entities;

import java.util.Date;

/**
 * Created by Артем on 01.05.2016.
 */
public class Lesson implements IEntity {
    private int ID;
    private Date date;
    private int room;
    private int scheduleNumber;
    private String homework;
    private int subjectID;

    public int getID() {
        return ID;
    }

    public void setID(int value) {
        ID=value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScheduleNumber() {
        return scheduleNumber;
    }

    public void setScheduleNumber(int scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
