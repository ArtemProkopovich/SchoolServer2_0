package Entities;

/**
 * Created by Артем on 01.05.2016.
 */
public class Subject implements IEntity {
    private int ID;
    private String name;
    private int lessonCount;
    private int teacherID;
    private int classID;

    public int getID() {
        return ID;
    }

    public void setID(int value) {
        ID = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }
}
